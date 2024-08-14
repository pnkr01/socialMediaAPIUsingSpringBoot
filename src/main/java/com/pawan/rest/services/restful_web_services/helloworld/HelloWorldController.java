package com.pawan.rest.services.restful_web_services.helloworld;

import com.pawan.rest.services.restful_web_services.helloworld.model.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    @GetMapping("/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable  String name){
        return new HelloWorldBean(String.format("Hello World %s", name));
    }

    @GetMapping("/hello-world-inter")
    public String helloWorldInter(){
        Locale locale = LocaleContextHolder.getLocale();
      return   messageSource.getMessage("good.morning.message",null,"Default Message", locale);
    }
}
