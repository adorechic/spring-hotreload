package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanFactory;

public class HotReloadBeanFactory extends DefaultListableBeanFactory{
	
	public HotReloadBeanFactory(BeanFactory parent) {
		super(parent);
	}
	
	/**
	 * never cache bean meta data for hot reloading
	 * @see org.springframework.beans.factory.support.AbstractBeanFactory#isCacheBeanMetadata()
	 */
	@Override
	public boolean isCacheBeanMetadata() {
		return false;
	}

}
