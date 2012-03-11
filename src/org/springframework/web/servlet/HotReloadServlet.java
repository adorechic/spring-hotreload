package org.springframework.web.servlet;

import java.beans.Introspector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.event.SourceFilteringListener;
import org.springframework.core.HotReloadClassLoader;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.HotReloadApplicatinContext;

public class HotReloadServlet extends DispatcherServlet{
	{
		setPublishContext(false);
	}
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.FrameworkServlet#getContextClass()
	 */
	@Override
	public Class getContextClass() {
		return HotReloadApplicatinContext.class;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.DispatcherServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			initServletBean();
			
			super.doService(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(getWebApplicationContext() instanceof HotReloadApplicatinContext) {
				ConfigurableWebApplicationContext context = (ConfigurableWebApplicationContext)getWebApplicationContext();
				context.close();
				Introspector.flushCaches();
				System.gc();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.FrameworkServlet#createWebApplicationContext(org.springframework.web.context.WebApplicationContext)
	 */
	@Override
	protected WebApplicationContext createWebApplicationContext(WebApplicationContext parent) throws BeansException {

		if (!ConfigurableWebApplicationContext.class.isAssignableFrom(getContextClass())) {
			throw new ApplicationContextException(
					"Fatal initialization error in servlet with name '" + getServletName() +
					"': custom WebApplicationContext class [" + getContextClass().getName() +
					"] is not of type ConfigurableWebApplicationContext");
		}
		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader hotReloadClassLoader = new HotReloadClassLoader(originalClassLoader);
		Thread.currentThread().setContextClassLoader(hotReloadClassLoader);
		
		HotReloadApplicatinContext wac =
				(HotReloadApplicatinContext) BeanUtils.instantiateClass(getContextClass());
		wac.setClassLoader(hotReloadClassLoader);
		wac.setParent(parent);
		wac.setServletContext(getServletContext());
		wac.setServletConfig(getServletConfig());
		wac.setNamespace(getNamespace());
		wac.setConfigLocation(getContextConfigLocation());
		wac.addApplicationListener(new SourceFilteringListener(wac, this));

		postProcessWebApplicationContext(wac);
		wac.refresh();

		return wac;
	}
	
}
