package com.magicalcoder.youyamvc.web.interceptor;

import com.magicalcoder.youyamvc.core.common.utils.StringUtils;
import com.magicalcoder.youyamvc.core.common.utils.SysPropertiesUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * qq:799374340
 * 
 * @author hdy 2013-7-25上午10:11:56 上下文拦截器
 */
public class ContextInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String ctxConfig = SysPropertiesUtil.getProperty("CTX");
		if(StringUtils.isBlank(ctxConfig)){
			String path = request.getContextPath();
			String CTX = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			ctxConfig = CTX;
		}
		request.setAttribute("CTX", ctxConfig);
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
    }

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
        //释放资源 避免内存溢出
        /*HandlerMethod hm = (HandlerMethod)handler;
        Object hmBean = hm.getBean();
        if(	hmBean instanceof AdminLoginController) {
            ControllerContext.remove();
        }*/
	}
	
}
