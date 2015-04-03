package cn.focus.search.admin.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.focus.search.admin.model.UserInfo;

/**
 * 此拦截器
 * 有时候我们可能只需要实现三个回调方法中的某一个，
 * 如果实现HandlerInterceptor接口的话，三个方法必须实现，不管你需不需要，
 * 此时spring提供了一个HandlerInterceptorAdapter适配器（一种适配器设计模式的实现），允许我们只实现需要的回调方法
 */
public class LoginInterceptor implements HandlerInterceptor {

    //还没发现可以直接配置不拦截的资源，所以在代码里面来排除
    public String[] allowUrls;

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    /**
     * 返回值：true表示继续流程（如调用下一个拦截器或处理器）；
     * false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	System.out.println("execute preHandle");
        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        if (null != allowUrls && allowUrls.length >= 1) {
            for (String url : allowUrls) {
                if (requestUrl.contains(url)) {
                    return true;
                }
            }
        }
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        if (user != null) {
            //返回true，则这个方面调用后会接着调用postHandle(),  afterCompletion()
        	System.out.println(user);
            return true;
        } else {
        	System.out.println("request.getContextPath(): "+request.getContextPath());
            // 未登录  跳转到登录页面
        	response.sendRedirect(request.getContextPath() + "/search/admin/tologin");
            return false;//返回到配置文件中定义的路径
        }
    }
    
    
    private Map<String,String> getCookies(HttpServletRequest request){
    	Map<String,String> map = new HashMap<String,String>();
    	
    	Cookie[] cookieArr = request.getCookies();
    	
    	for(Cookie c : cookieArr){
    		if(c.getName().equalsIgnoreCase("userName")){
    			map.put("userName", c.getValue());
    			continue;
    		}
    		if(c.getName().equalsIgnoreCase("token")){
    			map.put("token",c.getValue() );
    		}
    	}
    	
    	return map;
    }

    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），
     * 此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("=========postHandle=" + request.getServletPath());

    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，
     * 如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，
     * 类似于try-catch-finally中的finally，但仅调用处理器执行链中preHandle返回true的拦截器的afterCompletion
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("========afterCompletion=" + request.getServletPath());
    }
}
