# SpringMVC_Study03
springmvc中是一层层的调用,当service调dao层出错,那么异常就被抛到上一层,controller调用service出错,就把异常也往上一层抛出,
也就是异常最终会到达前端控制器,如果不加处理的话,那么就会报500的错误,所以为了出现异常的时候能有一个美观的页面响应,我们需要引用异常处理
SpringMVC的异常处理机制处理异常过程:
  1.创建自定义异常类
  package cn.maoritian.exception;

public class SysException extends Exception {

    // 存储提示信息的
    private String message;

    // 构造方法
    public SysException(String message) {this.message = message; }

    // get,set方法
    public String getMessage() {return message; }
    public void setMessage(String message) {this.message = message; }
}
2.创建异常处理器,异常处理器必须实现HandlerExceptionResolver接口,其resolveException()方法执行异常处理.
package cn.maoritian.exception;

// 自定义异常处理器
public class MyExceptionResolver implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        myException e = null;
        if (ex instanceof myException) {
            e = (myException) ex;
        } else {
            e = new myException("其他错误");
        }
        
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMsg", e.getMessage());   // 封装错误信息
        mv.setViewName("error");                    // 跳转页面
        return mv;
    }
}
3.向Spring容器中注入异常处理器
<!--配置异常处理器-->
<bean id="myExceptionResolver" class="cn.maoritian.exception.MyExceptionResolver"/>
4.controller处理方法的编写(需要抛出异常,才能在出错的时候,能返回一个美观的报错页面,而不是404 500)

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

