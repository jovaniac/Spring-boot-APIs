package com.demo.springbootapis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ssl")
public class SSLController {

  @GetMapping()
  public String greeting(){
    return "Self Signed SSL is Working!!";
  }
}
