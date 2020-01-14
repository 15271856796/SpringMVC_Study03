# SpringMVC_Study03
springmvc中是一层层的调用,当service调dao层出错,那么异常就被抛到上一层,controller调用service出错,就把异常也往上一层抛出,也就是异常最终会到达前端控制器,如果不加处理的话,那么就会报500的错误,所以为了出现异常的时候能有一个美观的页面响应,我们需要引用异常处理
这个项目不包含ajax的配置,就只是基本的配置外加异常处理的配置

SpringMVC的异常处理机制处理异常过程如下:
  1.创建自定义异常类
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

