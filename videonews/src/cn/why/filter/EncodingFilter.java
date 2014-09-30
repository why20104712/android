package cn.why.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter {

	public void destroy() {}
	/*
	 * 每次请求到来都会执行该方法
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if("GET".equals(req.getMethod())){
			EncodingRequestWrapper wrapper = new EncodingRequestWrapper(req);
			chain.doFilter(wrapper, response);
		}else{
			req.setCharacterEncoding("UTF-8");
			chain.doFilter(request, response);
		}
	}
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
