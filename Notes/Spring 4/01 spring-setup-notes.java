Guild to setup spring-mvc project in eclipse


1.	Create maven project with option 'war'

2.	Inside pom.xml
	2.1 - Add dependency javax (javaee-web-api)
	eg.

		<dependency>
			<groupId>javax</groupId>
			<artifactId>javaee-web-api</artifactId>
			<version>6.0</version>
			<scope>provided</scope>
		</dependency>

	2.2	 Add tomcat & apache plugin

			<build> 
				<pluginManagement>
					<plugins>
						<plugin>
							<groupId>org.apache.maven.plugins</groupId>
							<artifactId>maven-compiler-plugin</artifactId>
							<version>3.2</version>
							<configuration>
								<verbose>true</verbose>
								<source>1.7</source>
								<target>1.7</target>
								<showWarnings>true</showWarnings>
							</configuration>
						</plugin>
						<plugin>
							<groupId>org.apache.tomcat.maven</groupId>
							<artifactId>tomcat7-maven-plugin</artifactId>
							<version>2.2</version>
							<configuration>
								<path>/</path>
								<contextReloadable>true</contextReloadable>
							</configuration>
						</plugin>
					</plugins>
				</pluginManagement>
			</build>

2.x -------------- At this point your java EE project setuo is done with maven plugin

3. 		============= setup Spring ========	

	3.1		Add spring jar dependency in pom.xml

			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-webmvc</artifactId>
				<version>4.2.2.RELEASE</version>
			</dependency>



	3.2		Add dispatcher servlet entry  [ front controller]
			- Every request is come to dispatcher and dispatcher will deside where to forword it

			3.2.1	Create or find web.xml   "wenapp/WEB-INF/web.xml"

			3.2.2	Add servlet entry

						<servlet>
					        <servlet-name>dispatcher</servlet-name>
					        <servlet-class>
					            org.springframework.web.servlet.DispatcherServlet
					        </servlet-class>
					        <init-param>
					            <param-name>contextConfigLocation</param-name>
					            <param-value>/WEB-INF/todo-servlet.xml</param-value>		// todo-servlet.xml beans stuff
					        </init-param>
	        				<load-on-startup>1</load-on-startup>
	    				</servlet>
	
	    				<servlet-mapping>
					        <servlet-name>dispatcher</servlet-name>
					        <url-pattern>/spring-mvc/ *</url-pattern>   // forword all req start with /spring-mvc
	    				</servlet-mapping>


	    	3.2.3		DispatcherServlet needs an Spring Application Context to launch

	    				Create xml file and put following code

	    					<beans xmlns="http://www.springframework.org/schema/beans"
								xmlns:context="http://www.springframework.org/schema/context"
								xmlns:mvc="http://www.springframework.org/schema/mvc"
								xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
								xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
								    http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
								    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

	
								<context:component-scan base-package="com.kunal" />

								<mvc:annotation-driven />

							</beans>


		===========================    Done with basic Setup ===================================
