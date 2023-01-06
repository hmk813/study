package com.example.demo.home.controller;

import com.example.demo.article.vo.Article;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UsrHomeController {

  @RequestMapping("/usr/home/main")
  @ResponseBody
  public String main(){
    return "안녕";
  }
}


