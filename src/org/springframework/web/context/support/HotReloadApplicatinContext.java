package org.springframework.web.context.support;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.HotReloadBeanFactory;

public class HotReloadApplicatinContext extends XmlWebApplicationContext{
	
	/**
	 * returns HotReloading BeanFactory
	 * @see org.springframework.context.support.AbstractRefreshableApplicationContext#createBeanFactory()
	 */
	@Override
	protected DefaultListableBeanFactory createBeanFactory() {
		return new HotReloadBeanFactory(getInternalParentBeanFactory());
	}
	
}
