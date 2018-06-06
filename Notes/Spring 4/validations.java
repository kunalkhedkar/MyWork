

----------[ Validation using hibernate-validator] [5.4.1.Final]
			
			1. Added Dependency in pom.xml

			2. Inside Model class
				Add anotation for Validation like size,max,notNull,notEmpty

				'@Size(min = 10, message = "Enter atleast 10 Characters.")'
				'private String desc;'


				@NotNull 		-	Checks that annotated value is not null 
				@Min 			-	must be number >= value
				@Max 			-	must be number <= value
				@Size 			-	size must match the given size
				@Pattern 		-	must match a regular expression pattern
				@Future/Past	-	date must be in future or past of given date


			3. Inside controller
				Add anotation @Valid
					'@RequestMapping(value = "/update-todo", method = RequestMethod.POST)'
					'public String updateTodo(@Valid Todo todo, 'BindingResult result') { }'

			4. If there is any error it will store in BindingResult object
				we can check for error by calling method 
					bindingResult.hasErrors() - return boolean

	

-------------------------------------------------------------------------------------------------------------------

	sometimes some exception occurs spring handle it smartly and put it as error
	in this case it gives error with full Exception
	we can Override that error message using message.properties file and we need give value for that perticuler error code
	-	we can get error code by printing BindingResult result object.


	BindingResult result :org.springframework.validation.BeanPropertyBindingResult: 1 errors
	Field error in object 'todo' on field 'level': rejected value [rewrewrew]; 
	codes ['''typeMismatch.todo.level''',typeMismatch.level,typeMismatch.int,typeMismatch]; 
	arguments [org.springframework.context.support.DefaultMessageSourceResolvable: 
	codes [todo.level,level]; arguments []; default message [level]]; 
	default message [Failed to convert property value of type 'java.lang.String' to required type 'int' for property 'level'; nested exception is java.lang.NumberFormatException: For input string: "rewrewrew"]


	'typeMismatch.todo.level' specific code can be Override with custum message

		//message.properties
		typeMisMatch.todo.level=Invalid Number



-------------------------------------------------------------------------------------------------------------------------

						[ Custom Annotation]		



	[1].   Create anotation
		
		1.	Need use special type call @Interface
			Eg.
				public @Interface CourceCode{

				}	
				
		2.	 Define value,message,groups,payload

			@Constraint(validatedBy=CourceCodeConstraintValidator.class)			//	 where is business login define
			@Target({ElementType.METHOD,ElementType.FIELD})							//   where can you apply this annotation
			@Retention(RetentionPolicy.RUNTIME)										//   How long this anotation mark in memory
			public @interface CourceCode {

				public String value() default "LUV";
				
				public String message() default "must start with LUV";
				
				public Class<?>[] groups() default {};
				
				public Class<? extends Payload>[] payload() default {};
						
			}		


	[2]		Create CourceCodeConstraintValidator class which contains business logic 		


			public class CourceCodeConstraintValidator implements ConstraintValidator<CourceCode, String> {

				private String coursePrefix;

				@Override
				public void initialize(CourceCode theCourseCode) {
					coursePrefix = theCourseCode.value();
				}

				@Override
				public boolean isValid(String theCode, ConstraintValidatorContext constraintValidatorContext) {
					boolean result;
					result = theCode.startsWith(coursePrefix);		// comparing given data is startwith string specify by  
					return result;						
				}

			}	


	[3]		Use it

			@CourceCode()  //  with default values 


			@CourceCode(value="KUNAL",message="must start with KUNAL only") 
			private String desc;		