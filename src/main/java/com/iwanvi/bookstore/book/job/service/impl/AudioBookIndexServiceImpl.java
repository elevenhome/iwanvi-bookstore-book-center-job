package com.iwanvi.bookstore.book.job.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.iwanvi.bookstore.book.job.dao.zwsccenter.AudioBookDao;
import com.iwanvi.bookstore.book.job.dao.zwsccenter.AudioChannelLibBookDao;
import com.iwanvi.bookstore.book.job.service.AudioBookIndexService;
import com.iwanvi.bookstore.book.job.utils.Utils;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Cat;
import io.searchbox.core.CatResult;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.aliases.AddAliasMapping;
import io.searchbox.indices.aliases.ModifyAliases;
import io.searchbox.indices.aliases.RemoveAliasMapping;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 音频图书
 * @author zzw
 * @since 2019年3月28日14:09:04
 */
@Service
public class AudioBookIndexServiceImpl implements AudioBookIndexService {
    /**日志**/
    private static final Logger LOGGER = LoggerFactory.getLogger(BookIndexServiceImpl.class);

    @Autowired
    private AudioBookDao bookDao;//书籍
    @Autowired
    private AudioChannelLibBookDao channelLibBookDao;//渠道书籍


    @Autowired
    private JestClient jestClient;//Jest客户端

    /**图书分面域名**/
    @Value("${book_facet_domain}")
    private String bookFacetDomain;
    @Value("${es.audio.indexNames}")
    private String indexNames;//索引名称
    @Value("${es.audio.indexType}")
    private String indexType;//索引类型
    @Value("${es.audio.aliasName}")
    private String aliasName;//索引别名

    //公共变量
    private static Gson gosn = new Gson();//gson对象
    //任务开始结束时间
    private long beg_time = 0;
    private long end_time = 0;
    private static final int BATCH_SIZE = 1000;// 批量
    //    private static final int BATCH_SIZE = 500;// 批量
    private boolean endFlag = false; //结束标识
    private String buildIndexName; //构建索引的名称
    private String workingIndexName; //当前工作索引名称
    private Map<String, String[]> bookChannelMap; //书籍渠道Map
    private AtomicLong fail_count; //添加索引失败次数
    /**处理的图书数量**/
    private int bookTotal = 0;
    /**处理的渠道图书数量**/
    private int channelBookTotal = 0;


    //待创建索引队列
//    private static BlockingQueue<List<ESBook>> Q = new LinkedBlockingDeque<>(500);
    private static BlockingQueue<List<Map>> Q = new LinkedBlockingDeque<>(500);

    /**
     * 查询book,每次查询@BATCH_SIZE
     * 丢入Q里面查询
     * 如果查询结果条数少于BATCH_SIZE,可以确定索引已经查询完成
     */

    /**
     * 创建索引
     * @author zzw
     * @since 2018年12月20日17:34:17
     */
    @Override
    public void createIndex() {
        LOGGER.info("******************************音频-整个任务-开始了***************************");
        beg_time = System.currentTimeMillis();
        fail_count = new AtomicLong(0);
        bookChannelMap = new HashMap();
        bookTotal = 0;
        channelBookTotal = 0;
        // 初始化渠道对应map
        initChannelMap();
        // 初始化索引
        initBuildIndex();
        //set 数据到队列中，供消费者set到ES服务器中
        setDateToQ();
    }


    /**
     * set数据到队列中，供消费者set到ES服务器中
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private void setDateToQ() {
        LOGGER.info("setDateToQ start ......");
        //启动一个消费者线程，里边再启动线程池
        new Thread(new ConsumerRunner()).start();
        // 获取书城自有图书资源
        int id = 0;//book_v3表中的ID
        while (true) {
            List<Map> resultList = bookDao.queryBookForSearchForMap(id, BATCH_SIZE);
            try{
                //放入队列里边
                if(Utils.isNotEmpty(resultList)) {
                    int bookSize = resultList.size();
                    bookTotal = bookTotal + bookSize; //计算处理图书总数
                    Q.put(resultList);
                }
            } catch (InterruptedException e) {
                LOGGER.error("索引数据put队列错误!!!", e);
            }
            //判断是否为空，如果小于批处理的数量，那么一定为空
            if (resultList == null || resultList.size() < BATCH_SIZE) {
                break;
            }
            //获取最后一个的id
            id = (Integer) resultList.get(resultList.size() - 1).get("id");
        }
        endFlag = true;
        LOGGER.info("setDateToQ End ......");
    }




    /**
     * 初始化需要创新索引的index
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private void initBuildIndex() {
        LOGGER.info("initBuildIndex start ......");
        //获取当前的工作索引
        workingIndexName = getWorkingIndexName();
        LOGGER.info("当前工作索引 workingIndexName={}",workingIndexName);
        //获取索引名称列表
        List<String> indexList = getIndexNameArr();
        LOGGER.info("索引列表名称 indexList={}",gosn.toJson(indexList));
        //获取索引的下标
        int idx = indexList.indexOf(workingIndexName);
        LOGGER.info("索引下标 idx={}",idx);
        //然后轮换
        int nextIdx = (idx + 1) % indexList.size();
        LOGGER.info("轮换索引下表 nextIdx={}",nextIdx);
        //赋值
        buildIndexName = indexList.get(nextIdx);
        LOGGER.info("构建索引 buildIndexName={}",buildIndexName);
        //获取下一个将要使用的索引名称
        try {
            //删除构建的索引
            DeleteIndex.Builder delBuilder = new DeleteIndex.Builder(buildIndexName);
            //创建新的索引
            CreateIndex.Builder createBuilder = new CreateIndex.Builder(buildIndexName);
            //执行删除索引
            jestClient.execute(delBuilder.build());
            LOGGER.info("删除索引 buildIndexName={}",buildIndexName);
            //创建索引
            jestClient.execute(createBuilder.build());
            LOGGER.info("创建索引 buildIndexName={}",buildIndexName);
        } catch (IOException e) {
            LOGGER.error("初始化index出错,程序应该终止", e);
            throw new RuntimeException("初始化index出错,程序终止");
        }
        LOGGER.info("initBuildIndex end ......");
    }

    /**
     * 初始化书籍和对应渠道的map
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private void initChannelMap() {
        LOGGER.info("initChannelMap  start ...");
        String bookId = "";
        int batchSize = 10000;
        while (true) {
            List<Map> resultList = channelLibBookDao.getBookCnIdsMap(bookId, batchSize);
            LOGGER.info("get channel map,bookId={} batchSize={}",bookId,batchSize);
            if(Utils.isEmpty(resultList)){
                break;
            }
            for (Map map : resultList) {
                String tmpid = String.valueOf(map.get("bookId"));//书ID
                String val = String.valueOf(map.get("cnids"));//渠道ID逗号分隔
                if (StringUtils.isNotEmpty(val)) {
                    bookChannelMap.put(tmpid, val.split(","));
                }
            }
            if (resultList.size() < batchSize) {
                break;
            }
            //获取最后一本书的id
            bookId = String.valueOf(resultList.get(resultList.size() - 1).get("bookId"));
        }
        channelBookTotal = bookChannelMap.size();
        LOGGER.info("initChannelMap end ... bookChannelMap.size:" + channelBookTotal);
    }


    /**
     * 利用别名切换索引
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private void switchAliasMapping() {
        //获取工作索引
        try {
            LOGGER.info("switch index start ......");
//            String workingIndexName = getWorkingIndexName();
            //增加别名映射，把别名给予新建的索引
            AddAliasMapping.Builder addAliasMapping = new AddAliasMapping.Builder(buildIndexName, aliasName);
            LOGGER.info("把别名给予新建的索引 aliasName={} buildIndexName={}",aliasName,buildIndexName);
            //移除别名映射，把原来的别名映射移除
            RemoveAliasMapping.Builder removeAliasMapping = new RemoveAliasMapping.Builder(workingIndexName, aliasName);
            LOGGER.info("把原来的别名映射移除 aliasName={} workingIndexName={}",aliasName,workingIndexName);
            //别名修改
            ModifyAliases.Builder modify = new ModifyAliases.Builder(addAliasMapping.build());
            modify.addAlias(removeAliasMapping.build());
            //执行切换索引
            jestClient.execute(modify.build());
            end_time = System.currentTimeMillis();
            LOGGER.info("switch index End ......");
            workingIndexName = getWorkingIndexName();//查看一下当前的工作索引
            LOGGER.info("used time = {} minutes",(end_time - beg_time)/(float)(1000*60));
            LOGGER.info("渠道图书数量={} 图书数量={}",channelBookTotal,bookTotal);
            LOGGER.info("******************************音频-整个任务-结束了***************************");
        } catch (IOException e) {
            LOGGER.error("切换索引异常 {} {}",e.getMessage(), e);
        }
    }

    /**
     * 获取当前正在提供查询的indexName
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private String getWorkingIndexName() {
        try {
            // 获取当前正在提供搜索的index
            Cat.AliasesBuilder aliasesBuilder = new Cat.AliasesBuilder();
            CatResult result = jestClient.execute(aliasesBuilder.build());
            LOGGER.info("获取当前索引返回={}",gosn.toJson(result));
            //获取索引的别名映射
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map> aliasMaps = objectMapper.readValue(result.getJsonString(), List.class);
            LOGGER.info("aliasMaps={}",gosn.toJson(aliasMaps));
            //[{"alias":"esbookcenter","index":"book_center_index_1","filter":"-","routing.index":"-","routing.search":"-"}]
            for (Map aliasMap : aliasMaps) {
                // 如果当前需要创建的alias等于查找出来的alias的话,
                // 则获取到当前正在工作的index,然后取下一个index做为当前需要创建的索引index
                if (aliasName.equals(String.valueOf(aliasMap.get("alias")))) {
                    //获取当前工作的别名
                    String workingIndexName = String.valueOf(aliasMap.get("index"));
                    LOGGER.info("当前工作索引 workingIndexName={}",workingIndexName);
                    return workingIndexName;
                }
            }
        } catch (IOException e) {
            LOGGER.error("获取alias出错,程序应该终止 {} {}",e.getMessage(), e);
            throw new RuntimeException("获取alias出错,程序终止");
        }
        return "";
    }

    /**
     * 获取索引名称
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private List<String> getIndexNameArr() {
        return Arrays.asList(indexNames.split(","));
    }


    /**
     * 取队列数据线程
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    private static final int consumer_pool_size = 10;//消费线程池大小
    class ConsumerRunner implements Runnable {
        @Override
        public void run() {
            LOGGER.info("ConsumerRunner is running...");
            boolean run = true;//线程运行标识
            ThreadPoolExecutor poolExecutor =
                    new ThreadPoolExecutor(consumer_pool_size, consumer_pool_size, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                            new RejectedExecutionHandler() {
                                @Override
                                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                                    //线程池处理不过来,直接调用run方法,占用主线程资源
                                    r.run();
                                }
                            });
            //这个是计的什么数
            AtomicLong indexSeq = new AtomicLong();
            while (run) {
                //向池里边添加数据
                try {
                    //获取队列的数据
                    List<Map> indexList = Q.take();
                    //创建索引
                    poolExecutor.execute(new BulkRunner(indexList, indexSeq.incrementAndGet()));
                    //结束的善后工作
                    if ((indexList == null || indexList.size() < BATCH_SIZE) && endFlag) {
                        run = false;
                        Thread.sleep(100);
                        poolExecutor.shutdown();
                    }
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    LOGGER.warn(e.getMessage());
                }
            }
            //启动切换索引的线程
            new Thread(new SwichIndexRunner(poolExecutor)).start();
            LOGGER.info("ConsumerRunner is shutdown...");
        }
    }


    /**
     * 为索引添加数据
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    class BulkRunner implements Runnable {
        List<Map> data;//数据
        long seq;//序号
        public BulkRunner(List<Map> data, long seq) {
            this.data = data;
            this.seq = seq;
        }
        @Override
        public void run() {
            //判断数组是否为空
            if (data == null || data.isEmpty()) {
                LOGGER.warn("add index data is empty...");
                return;
            }
            //构造一个批量操作
            Bulk.Builder bulk = new Bulk.Builder().refresh(true).defaultIndex(buildIndexName).defaultType(indexType);
            //遍历数据(向ES服务添加数据)
            for (Map map : data) {
                // 索引docId
                String indexId = String.valueOf(map.get("bookId"));
                if (!bookChannelMap.containsKey(indexId)) {//渠道图书表中是否包含图书信息
                    continue;
                }
                //修改MAP中的参数
                //图片地址
                Object imgUrlObj = map.get("imgUrl");
                Object longImgUrlObj = map.get("longImgUrl");
                if (Utils.isNotEmpty(longImgUrlObj)) {//图片长地址
                    String longImgUrl = String.valueOf(longImgUrlObj);
                    String longImgUrlDomian = getAudioBookCover(longImgUrl);
                    map.put("imgUrl", longImgUrlDomian);
                    map.put("longImgUrl", longImgUrlDomian);
                } else if (Utils.isNotEmpty(imgUrlObj)) {
                    String imgUrl = String.valueOf(imgUrlObj);
                    map.put("imgUrl", getAudioBookCover(imgUrl));
                }
                //移除id，这个又是为了es自己生成主键？
                map.remove("id");
                //获取书籍渠道id=
                map.put("cnids", bookChannelMap.get(indexId));
                //增加一个索引记录
                bulk.addAction(new Index.Builder(map).id(indexId).build());
            }
            try {
                //批量执行
                jestClient.execute(bulk.build());
                LOGGER.info("add index success!! seq:{},buildIndexName:{},indexType:{},add size:{}",seq, buildIndexName, indexType, data.size());
            } catch (IOException e) {
                LOGGER.error("add index error!! seq:{},buildIndexName:{},indexType:{},add size:{}",seq, buildIndexName, indexType, data.size());
                LOGGER.error("插入索引数据错误!!! {} {}",e.getMessage(), e);
                retry(bulk.build());
            }
        }

        /**
         * 失败后重试一次(批量执行)
         * @author zzw
         * @since 2018年12月14日16:57:47
         */
        private static final int READD_NUM = 4; //重新添加次数
        private void retry(Bulk bulk) {
            int i = 0;
            while (i++ < READD_NUM) {
                try {
                    jestClient.execute(bulk);
                    LOGGER.info("readd index success!! seq:{},buildIndexName:{},indexType:{},add size:{}",seq,buildIndexName,indexType,data.size());
                    break;//添加索引成功,退出循环
                } catch (IOException e) {
                    LOGGER.error("readd index error!! seq:{},buildIndexName:{},indexType:{},add size:{}",seq,buildIndexName,indexType,data.size());
                    LOGGER.error("重新插入索引数据错误!!! i={} {} {}",i,e.getMessage(), e);
                }
            }
            if (i >= READD_NUM) {
                fail_count.getAndIncrement();
            }
        }
    }

    /**
     * 切换索引的线程
     * @author zzw
     * @since 2018年12月14日16:57:47
     */
    class SwichIndexRunner implements Runnable {
        //线程池执行器
        ThreadPoolExecutor executor;
        //切换线程
        SwichIndexRunner(ThreadPoolExecutor _executor) {
            this.executor = _executor;
        }
        @Override
        public void run() {
            LOGGER.info("启动切换索引线程... fail_count={}",fail_count.get());
            while (true) {
                //等待线程池中所有任务执行完成后 切换索引
                if (executor.isTerminated()) {
                    // 错误次数少于10次
                    if (fail_count.get() < 10) {
                        //切换索引
                        switchAliasMapping();
                    }
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOGGER.error("异常啦 {} {}",e.getMessage(),e);
                }
            }
        }
    }


    /**
     * @Description 获取音频图书封面
     * @Author zzw
     * @Date 2019年4月1日18:59:28
     */
    public String getAudioBookCover(String imgUrl) {
        if(Utils.isEmpty(imgUrl)){
            return "";
        }
        StringBuffer bookCover = new StringBuffer();
        String lowerImgUrl = imgUrl.toLowerCase();
        if (!lowerImgUrl.startsWith("http")) {
            bookCover.append(getBookCover());
        }
        bookCover.append(imgUrl);
        if (!lowerImgUrl.endsWith(".jpg")) {
            bookCover.append(".jpg");
        }
        return bookCover.toString();
    }

    /**
     * @Description 图书封面域名
     * @Author zzw
     * @Date 2019年4月1日18:59:28
     */
    public String getBookCover() {
        return bookFacetDomain;
        //return "http://cdn.ikanshu.cn/211/images/";
    }

}

