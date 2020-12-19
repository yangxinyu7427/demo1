package com.example.demo.com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
class UserController {
    @RequestMapping("/hello")
    public Result<User> hello(@RequestParam("username") String name){
        User user = new User(name);
        return Result.Success(user);
    }
}

class User {
    public String name ;
    public User(String name) {
        this.name =name;
    }
}