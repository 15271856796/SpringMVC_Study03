package cn.itcast.controller;

import cn.itcast.exception.SysException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/testException")
    public String testException() throws SysException{
        System.out.println("testException执行了...");

        try {
            // 模拟异常
            int a = 10/0;
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
            // 抛出自定义异常信息,如果没有异常处理的话 那么就会直接报500的错误
            throw new SysException("查询所有用户出现错误了...");
        }



        return "success";
    }

}
