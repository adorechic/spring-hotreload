Spring-HotReload
======================
SpringMVCにHotReloading機能を追加するプラグイン。
クラスファイルが上書きされただけでアプリケーションに反映されます。

使い方
------
jarファイルをクラスパスに通した上で、web.xmlを以下のように編集
ただしurl-patternは任意。

	<servlet>
		<servlet-name>dispatch</servlet-name>
		<servlet-class>org.springframework.web.servlet.HotReloadServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:config/applicationContext.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>dispatch</servlet-name>
		<url-pattern>/api/*</url-pattern>
	</servlet-mapping>
