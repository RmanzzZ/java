package com.rman.controller;

import com.rman.config.result.Result;
import com.rman.config.result.ResultGenerator;
import com.rman.config.shiro.JWTUtil;
import com.rman.entity.UserBean;
import com.rman.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Description :
 * @Author : ziming
 * @Date: 2020-04-23 08:54
 */
@RestController
public class ShiroController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @GetMapping("/redis")
    public void tt(){
        redisTemplate.opsForValue().set("kk","tt");
    }
    @GetMapping("/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        UserBean user = userService.getUserBean(username);
        if(user.getPassword().equals(password)){
            return ResultGenerator.genSuccessResult(JWTUtil.sign(username,password));
        }else {
            return ResultGenerator.genFailResult("username or password is error");
        }
    }
    @GetMapping("/article")
    public Result article(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return ResultGenerator.genSuccessResult("you has logged in");
        }else {
            return ResultGenerator.genFailResult("you has not logged in");
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public Result requireAuth() {
        return ResultGenerator.genSuccessResult("You are authenticated");
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public Result requireRole() {
        return ResultGenerator.genSuccessResult("You are visiting require_role");
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public Result requirePermission() {
        return ResultGenerator.genSuccessResult("You are visiting permission require edit,view");
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result unauthorized() {
        return ResultGenerator.genFailResult("Unauthorized");
    }
}
