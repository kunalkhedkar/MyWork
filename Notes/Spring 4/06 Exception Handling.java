Exception Handling

	Exception handling in spring mvc can be applied at two diff  level
		- @ControllerAdvice
		- Controller Specific Exception Handling

controlleradvice-  can be use for init binder
@ControllerAdvice 
	-	Its advice to all the controller in that perticuler application.
	-	Lot of thing can impliment here
		 eg. You can implement initBinder and binder would applied through out the application.


		 [1] -- For all Controller {'Global level'}

				@ExceptionHandler(value=Exception.class)
				public String handleException(HttpServletRequest request,Exception e) {
					logger.error("Request : "+request.getRequestURL()+" threw exception ",e);
					return "error";
				}


		[2] -- Specific Controller  same

					@ExceptionHandler(value=Exception.class)
					public String handleException(HttpServletRequest request,Exception e) {
						logger.error("Request : "+request.getRequestURL()+" threw exception ",e);
						return "error-specific";
					}

			'value accept parameter, which exception should handle'


		[3]	some jsp exception may be left from controllerAdvicer 
			so we put exceptionHandle in 'web.xml' that all unhandle exception by controllerAdvice will forword to here

			<error-page>
	    		<location>/WEB-INF/views/jsp/error.jsp</location>
			</error-page>
