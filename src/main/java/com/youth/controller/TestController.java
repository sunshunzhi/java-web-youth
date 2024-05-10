package com.youth.controller;

import com.youth.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/youth")
    public ResponseResult testUser() {
        return ResponseResult.success("youth");
    }

    @GetMapping("/admin")
    public ResponseResult testAdmin() {
        return ResponseResult.success("admin");
    }


}
