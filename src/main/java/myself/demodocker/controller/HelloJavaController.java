package myself.demodocker.controller;

import myself.demodocker.RateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: HelloJavaController
 * @description:
 * @author: YuZhiYuan
 * @date: 2020-05-22 15:37
 **/
@RestController

@RequestMapping("/api/v1/test")
public class HelloJavaController {


    @GetMapping(value = "home")
    @RateLimit
    public String home(){
        return "Hello Java....";
    }

}

