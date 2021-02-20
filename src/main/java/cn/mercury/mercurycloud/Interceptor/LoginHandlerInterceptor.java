package cn.mercury.mercurycloud.Interceptor;

import cn.mercury.mercurycloud.pojo.User;
import cn.mercury.mercurycloud.service.UserService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser = request.getSession().getAttribute("loginUser");
        if (loginUser==null){
            //未登录,返回登录页面
            response.sendRedirect("/mercury-cloud/");
            return false;
        }else {
            //已登录,放行
            return true;
        }
    }
}
