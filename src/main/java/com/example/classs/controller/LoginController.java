package com.example.classs.controller;

import com.example.classs.common.ResponseCode;
import com.example.classs.common.ResponseEntity;
import com.example.classs.entity.User;
import com.example.classs.service.UserService;
import com.example.classs.util.CookieUtil;
import com.github.benmanes.caffeine.cache.Cache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

@Api(value = "/auth", tags = "登录登出接口")
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private Cache<String, Object> caffeineCache;

    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public ResponseEntity<Boolean> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        boolean flag = userService.login(user);
        if (flag) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //存缓存里面一份
            caffeineCache.put(user.getName(), uuid);
            //cookie里面一份
            CookieUtil.setCookie(response, user.getName(), uuid, "/");
            CookieUtil.setCookie(response, "username", user.getName(), "/");
            return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), flag, "登录成功");
        }
        return ResponseEntity.fail(ResponseCode.SUCCESS.getCode(), flag, "登录失败");
    }

    @ApiOperation("登出")
    @GetMapping(value = "/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie usernameCookie = CookieUtil.getCookie(request, "username");
        if (Objects.isNull(usernameCookie)) {
            //未授权
            return ResponseEntity.ok(ResponseCode.UNAUTHORIZED.getCode(), null, "用户未登录");
        }
        String username = usernameCookie.getValue();
        Cookie auth = CookieUtil.getCookie(request, username);
        if (Objects.isNull(auth)) {
            //未授权
            return ResponseEntity.ok(ResponseCode.UNAUTHORIZED.getCode(), null, "用户未登录 ");
        }
        //从缓存中删除user
        caffeineCache.invalidate(username);
        //TODO 删除cookie
        CookieUtil.setCookie(response, username, null, "/");
        CookieUtil.setCookie(response, "username", null, "/");
        return ResponseEntity.ok(ResponseCode.SUCCESS.getCode(), null, "注销成功");
    }
}
