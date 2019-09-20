package com.leo.redis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Leo Liu
 * @create 19/09/2019
 */
@RestController
@RequestMapping("api")
public class TestController {

  @GetMapping("item")
  public String testCreateSession(
      HttpServletRequest request,
      @RequestParam(required = false, defaultValue = "key") String key,
      @RequestParam(required = false, defaultValue = "value") String value) {
    HttpSession session = request.getSession();
    session.setAttribute(key, value);
    return "sessionId: " + session.getId() + " key: " + key;
  }

  @GetMapping("item1")
  public String testGetSession(
      HttpServletRequest request,
      @RequestParam(required = false, defaultValue = "key") String key) {
    HttpSession session = request.getSession();
    Object value = session.getAttribute(key);
    return "sessionId: " + session.getId() + " value: " + value;
  }

  @GetMapping("item2")
  public void testTakeSessionFormCookie(HttpServletRequest request) {
    System.out.println("cookie方式");
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      System.out.println(cookie.getName() + ": " + cookie.getValue());
    }
  }

  @GetMapping("item3")
  public void testTakeSessionFromHeader(HttpServletRequest request) {
    System.out.println("header方式");
    String header = request.getHeader("x-Auth-Token");
    System.out.println("header: " + header);
  }
}
