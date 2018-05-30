Internationalization Multiple language

step 1>
	- setup beans in context servlet

1.		<bean id="messageSource"
			class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
			<property name="basename" value="classpath:messages" />
			<property name="defaultEncoding" value="UTF-8" />
		</bean>

2.		<bean id="localeResolver"
			class="org.springframework.web.servlet.i18n.SessionLocaleResolver">
			<property name="defaultLocale" value="en" />
		</bean>

3.		<mvc:interceptors>
			<bean id="localeChangeInterceptor"
				class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor">
				<property name="paramName" value="language" />
			</bean>
		</mvc:interceptors>



	1. Where the language properties file are store
	2. Default value [ here English]
	3. To change language we need interceptors which will change language.


step 2>

	Create messages.properties files
	- Inside resource->messages_en.properties
				todo.caption = Your Todoes in English
			 resource->messages_fr.properties
			 	todo.caption = Vos Todos en français


step 3>
		Inside JSP page
		<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

		<spring:message code="todo.caption"></spring:message> 			 


step 4>
		To change language

		http://localhost:8080/todo-list?language=en




