package com.iwanvi.bookstore.book.job.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 * @author zzw
 * @since 2019年3月1日11:21:47
 */

@Api(description = "首页")
@RestController
public class HomeController {

    @ApiOperation(value = "服务正常检查", httpMethod = "GET")
    @ApiResponses({@ApiResponse(code = 600, message = "")})
    @GetMapping("/home/checkHealth")
    public String checkHealth() {
        return "succ";
    }

}
