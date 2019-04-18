package com.iwanvi.bookstore.book.job.common;

import java.util.List;

/**
 * SQL实体类
 * @author zzw
 * @since 2018年12月14日15:00:51
 */
public class SqlEntity {

    private String sql; //sql语句
    private List<Object> args; //? 匹配的参数
    private List<Integer> argsType; //参数类型

    public SqlEntity(String sql, List<Object> args, List<Integer> argsType) {
        this.sql = sql;
        this.args = args;
        this.argsType = argsType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }

    public List<Integer> getArgsType() {
        return argsType;
    }

    public void setArgsType(List<Integer> argsType) {
        this.argsType = argsType;
    }
}
