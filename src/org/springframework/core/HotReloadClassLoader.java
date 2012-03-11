package org.springframework.core;

import java.io.InputStream;
import java.net.URL;

import org.springframework.core.InputStreamUtil;
import org.springframework.util.StringUtils;

public class HotReloadClassLoader extends ClassLoader{
	public HotReloadClassLoader(ClassLoader cl) {
		super(cl);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
	 */
	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		Class clazz = findLoadedClass(name);
		if(clazz != null) return clazz;
		
		clazz = defineClass(name, resolve);
		if(clazz != null) return clazz;
		
		return super.loadClass(name, resolve);
	}
	
	/**
	 * define class by load class file.
	 * @param className
	 * @param resolve
	 * @return
	 */
	protected Class defineClass(String className, boolean resolve) {
		
		String path = StringUtils.replace(className, ".", "/") + ".class";
		
		URL url = getResource(path);
		if(url == null || url.toString().startsWith("jar")) {
			return null;
		}

		InputStream is = null;
		try {
			is = url.openStream();
		} catch (Exception e) {
			return null;
		}
		if(is == null) return null;
		
		Class clazz = null;
		try {
			clazz = defineClass(className, is);
		} catch (Exception e) {
			return null;
		}
		return clazz;
	}
	
	/**
	 * define class
	 * @param className
	 * @param classFile
	 * @return
	 */
	protected Class defineClass(String className, InputStream classFile) {
		return defineClass(className, InputStreamUtil.getBytes(classFile));
	}
	
	/**
	 * define class
	 * @param className
	 * @param bytes
	 * @return
	 */
	protected Class defineClass(String className, byte[] bytes) {
		return defineClass(className, bytes, 0, bytes.length);
	}
	
}
