

# Retrofit

# Read Data using retrofit

- ##### Add dependency in build.gradle

```groovy
// Retrofit networking
implementation 'com.squareup.retrofit2:retrofit:2.4.0'
// Gson for parsing
implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
```



- ##### Create Interface for client

GithubClient.java

```java
public interface GitHubClient {
    @GET("/users/{user}/repos")
    Call<List<GitHubRepoModel>> reposForUser(@Path("user") String user);
}
```

here user should be dynamic so we pass username as parameter to **reposForUser** method in {} braces.

and annotated parameter variable as @Path and gave default value "user". 

- ##### Create Model class - to parse response object

```java
public class GitHubRepoModel {
    private String name;
    public String getName() {
        return name;
    }
}
```

This is simple pojo class which contains exacts same name which is going to present in response.



- ##### Create Retrofit-Builder

```java
Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());
```

#2 method : Is base URL 

#3 method : We can specify parsing technic, we just need to pass its object to 

​		       addConverterFactory method.

​`here we use Gson for parsing. (need to add dependency of retrofit-gson)`

- ##### create Retrofit object from retrofit-builder

```java
Retrofit retrofit = builder.build();
```

- ##### Instantiate  client interface object

```java
GitHubClient client = retrofit.create(GitHubClient.class);
```

- ##### Call web service using client method

```java
Call<List<GitHubRepo>> call = client.reposForUser("username");
```

- ##### Call service asynchrony

```java
call.enqueue(new Callback<List<GitHubRepo>>() {
	@Override
	public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
     	List<GitHubRepo> repos = response.body();
      }
    @Override
     public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
            Toast.makeText(MainActivity.this, "error :(", Toast.LENGTH_SHORT).show();
     }
});
```

here we call enqueue method on call object which will get from calling web-service method.

enqueue need callback object which has 2 compulsory method to override 

1. onResponse  -  get response by calling response.body() method
2. onFailure       -  it will call if service fails or no connection found





# Write data to server

Same as Reading, no change in creating retrofit object and call service.



- ##### Create model class which we want to send to server

```java
class User {
    
    String name;
    String email;
    int age;
    String[] topics;
    Integer id;

    public User(String name, String email, int age, String[] topics) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.topics = topics;
    }

    public Integer getId() {
        return id;
    }
}
```

**Note** : If Object property is null retrofit will just ignore. So in our user class ID is null and we are sending it to server since it is null retrofit  will not create json property in request. it will just ignore.

- ##### Create interface for user-client

  ```java
  public interface UserClient {
          @POST("user")
          Call<User> createAccount(@Body User user);
  
  }
  ```

here want to send user object as request body so we need to annotate user object as a @Body()



# Log Request and response



- ##### Add dependency

```groovy
implementation 'com.squareup.okhttp3:logging-interceptor:3.6.0'
```

- ##### Add custom okhttp client to retrofit with log-interceptor

Retrofit **use default instance of okhttp** as a network layer unless u pass a custom. Now we will create custom one.

```java

OkHttpClient.Builder OkHttpClientBuilder=new OkHttpClient.Builder();
HttpLoggingInterceptor logger=new HttpLoggingInterceptor();
logger.setLevel(HttpLoggingInterceptor.Level.BODY);
OkHttpClientBuilder.addInterceptor(logger);

```

here we want to add log-interceptor, so we create object of HttpLoggingInterceptor and add it to OkHttpClientBuilder.

we can set **log level** by using method **setLevel** values can be 

- Body 	-	Logs request and response lines and their respective headers and bodies (if present).

	 Basic	-	Logs request and response lines.

	 Header	-	Logs request and response lines and their respective headers.

	 None 	-	No logs.

  

- ##### Add custom OkHttpClient to retrofit-builder

> Retrofit.Builder retrofitBuilder=new Retrofit.Builder()
>         						.baseUrl("http://192.168.5.51/ProductService/Service1.svc/")
>       							.addConverterFactory(GsonConverterFactory.create())
> 						        **.client(OkHttpClientBuilder.build());**



Note: Make sure you do not log any request-response in production level for that

```java
if(BuildConfig.DEBUG){
    OkHttpClientBuilder.addInterceptor(logger);
}
```



# Upload file to server

- ##### Create client interface


```java
public interface FileClient {
    
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadphoto(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part photofile
    );

}
```

here as usual we need to specify web-service end point by annotating @POST

For file we need add one more annotation which is @Multipart. Multipart need request type to POST or PUT

Multipart requests sperate request into different parts so we need to annotate every parameter with the   `@part` annotation.

- Parameters with there types

#1 **Description** - it is just a simple string . we can replace its type with string but its has some side effects

so we keep it `ResponseBody`.

#2 **Actual file** - need to add type as `MultipartBody.Part`



- Call web-service method

  As we mention in method parameter we need to create RequestBody object.

  

  In client method we have two parameter , lets create them

- > description 

  we need create requestBody object, we use static method from RequestBody class.

  ```java
  RequestBody descriptionPart=RequestBody.create(MultipartBody.FORM,"fileName");
  ```

  **first parameter** is MediaType , this need to be **MultipartBody.FORM** when sending addition data along with file 

  Second parameter is description String its self.

  

- > photoFile

     Its a two step process

  - **First we create requestBody for the file**

```java
RequestBody photoFilePart=RequestBody.create(
        						MediaType.parse(getContentResolver().getType(fileuri)),
        						FileUtils.getFile(this,fileuri) );
```

As above it take two parameter `MediaType` and `file`

- MediaType

  If we know file type we can directly set it in first parameter, 

  But for Dynamic purpose we take mediaType by parsing it using `MediaType.parse` and inside it we pass `fileUri` to contentResolver.getType.

- File

  ​	just File object. we need do this stuff because of file chooser of android.
  - **Second step**

okHttp needs to knows about part and file name we can describe those by creating new  MultipartBody.Part object using createForm object 

```java
MultipartBody.Partfile Partfile = MultipartBody.Part.createFormData("photo",
													photofile.getName(),
													photoFilePart);
```

#1 parameter is part name which we have specified in part annotation for the description 

#2 File name

#3 photoFilePart object



- **call web-service method**

  ```java
  Call<ResponseBody> call = fileClient.uploadphoto(descriptionPart, Partfile);
  call.enqueue(new Callback<ResponseBody>() {...}
  ```

- **Final call be** 

  ```java
  RequestBody descriptionPart=ResponseBody.create(MultipartBody.FORM,"fileName");
  
  RequestBody photoFilePart=RequestBody.create(
          						MediaType.parse(getContentResolver().getType(fileuri)),
          						FileUtils.getFile(this,fileuri) );
  
  MultipartBody.Partfile=MultipartBody.Part.createFormData("photo",
                                                           photofile.getName(),
                                                           photoFilePart);
  
  FileClient fileClient = retrofit.create(FileClient.class);
  
  Call<ResponseBody> call = fileClient.uploadphoto(descriptionPart, file);
  call.enqueue(new Callback<ResponseBody>() { ... });
  ```







# **Passing Multiple Parts Along a File with**

What if we want to send more part along with file, extra describing Strings

Lets create util method to cerate StringPart and filePart  

```java
public  static RequestBody createPartFromString(String string){
    return  RequestBody.create(MultipartBody.FORM,string);
}
```

```java
public static MultipartBody.Part createFilePart(String partName, Uri fileUri){

    File file= FileUtils.getFile(context,fileUri);

    //Create RequestBody instance from file
    RequestBody requestFilePart=RequestBody.create(
            MediaType.parse(context.getContentResolver().getType(fileUri)),file);

    return MultipartBody.Part.createFormData(partName,file.getName(),requestFilePart);
}
```



web-service call will be

```java
Call<ResponseBody> call1 = fileClient.uploadphoto(
                                MultiPartUtil.createPartFromString("description"),
                                MultiPartUtil.createPartFromString("location"),
                                MultiPartUtil.createPartFromString("year"),
                                MultiPartUtil.createPartFromString("photographer"),
                                MultiPartUtil.createFilePart("photo",fileuri));

call.enqueue(new Callback<ResponseBody>() { ... });
```



> ### Multiple part Using Map
>

we can send as much part as we need but as number of parts grows its become difficult to maintain so we can replace multiple same type part with map.

```
@Multipart
@POST("upload")
Call<ResponseBody> uploadphoto(
        @PartMap Map<String,RequestBody> dataPart,
        @Part MultipartBody.Part photofile
);
```

we need so annotate it with @PartMap and map type.

And web-service call will be

```java
Map<String, RequestBody> partMap = new HashMap<>();
```

```java
partMap.put("description", MultiPartUtil.createPartFromString("description"));
partMap.put("location", MultiPartUtil.createPartFromString("location"));
partMap.put("year", MultiPartUtil.createPartFromString("year"));
partMap.put("photographer", MultiPartUtil.createPartFromString("photographer"));

Call<ResponseBody> call2 = fileClient.uploadphoto(partMap,
        MultiPartUtil.createFilePart("photo", fileuri));
```





# Upload Multiple Files

simple send list of MultipartBody.Part

```java
@Multipart
@POST("upload")
Call<ResponseBody> uploadphoto(
        @Part("description") RequestBody description,
        @Part List<MultipartBody.Part> files
);
```

web-service call will be

```java
List<MultipartBody.Part> partList=new ArrayList<>();

for (int i = 0; i < fileuris.size(); i++) {
    partList.add(MultiPartUtil.createFilePart(""+i,fileuris.get(i)));
}

Call<ResponseBody> call3 = fileClient.uploadphoto(
    								MultiPartUtil.createPartFromString("description"),
        							partList);
```



# Custom Request Headers

Often its necessary fill app meta info to request in request header. 

> **Static Headers** 

```java
@Headers("Cache-Control: max-age=3600")
@POST("user")
Call<User> createAccount(@Body User user);
```

 We just need to add annotation @Headers and give value. Value will string which include key and value.

We can also send multiple header using {}

```java
@Headers({"Cache-Control: max-age=3600",
          "User-Agent: Android",
          "User-Type: test"
        })
@POST("user")
Call<User> createAccount(@Body User user);
```

> **Dynamic Header**

Often we don't have values for header in advance. we can use thus there second way to set header

we can set dynamic header inside method body

```java
@POST("user")
Call<User> createAccount(
        @Header("Header-user-key") String userName,
        @Body User user);
```

method call will be

```java
userClient.createAccount("header-Username-Value",user);
```


> **What if we put Both Header**

```java
@Headers("Cache-Control: max-age=3600")
@POST("user")
Call<User> createAccount(
        @Header("Cache-Control") String userName,
        @Body User user);
```

If we put both header static as well as dynamic 

Dynamic header will not replace static. Both headers will be added.

```javascript
Cache-Control="Cache-Control:   max-age=3600 , Dynamic-header-Username-Value "
```



# Synchronous and Asynchronous Requests

> ##### Asynchronous -   enqueue 
>

Thread will **not block** until network operation complete. Thread will free to perform next operation.

```java
        Call<ResponseBody> call = fileClient.uploadphoto(descriptionPart, file);
        
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
```



> ##### synchronous  -   execute
>

Thread will **block** until network operation complete. When net operation complete then only thread move forward to perform next operation.

```java
try {
    
    	Response<ResponseBody> result = call.execute();
  
} catch (IOException e) {
    e.printStackTrace();
} 
```

In higher version of android app will directly **crash** if you try to perform network operation on main thread.

we can use Intent-service or background thread. To perform **synchronous**  request.



# Request Headers in OkHttp Interceptor

Retrofit support Static as well as Dynamic header. But if have large app and too many end points, you will need to add this header to every single endpoint declaration. (web-service call) This is neither efficient or developer friendly. Now here OkHttp Interceptor comes into picture because you can manage herder in one central place for all request. So we will go down a level, so instead of solving the problem of adding header at retrofit level, we go into utilized network level of okhttp. OkHttp offers the interceptor, which is an easy way to customized every request of the app in single place before it actually goes out to the network.

Retrofit use default instance of okHttp. So we need to create customized okhttp object and add interceptor.

```java
OkHttpClient.Builder okHttpBuilder=new OkHttpClient.Builder(); 
```

we need add interceptor

```java
okHttpBuilder.addInterceptor(new Interceptor() {
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Request.Builder newRequestBuilder=request.newBuilder().addHeader("Auth","auth-token");

        return chain.proceed(newRequestBuilder.build());
    }
});
```

here we got chain object using which we can get request object. we can not change request so we need to create new-request by using request object got from chain.

we use **newBuilder()** method on old-request object to create new-request-builder object and here we can add herder by using builder method addHeader(). And simple return request object created from new-request-builder object.

Note : we also can use 

```java
// will append header if already exist
request.newBuilder().addHeader("Auth","auth-token");
					or
// replace header if already exist					
request.newBuilder().Header("Auth","auth-token");
```



# Use Dynamic Urls for Requests

In Retrofit structure we gave base url, But what if we need to use completely different domain or url. For this retrofit allow to use dynamic urls.

```java
@GET
Call<ResponseBody> getUserProfilePhoto( 
    									@Url String url);
```

we simply annotate parameter as @Url and now we can simply pass the entire URL and Retrofit will access that exactly URL.   

```java
String myURL="https://s3.amazon.com/profile-picture/path";
userClient.getUserProfilePhoto(myURL);
```



# Download Files from Server

Retrofit is library for for doing rest request. It does support download file but it's not main feature.

```java
public interface FileDownloadClient {
    @GET("images/futurestudio-university-logo.png")
   Call<ResponseBody> downloadFile();

}
```

No change in calling end point. we can remove addConverterFactory as we are not converting it as java object.

we need write binary data as file to disk in response.

```java
Retrofit.Builder builder = new Retrofit.Builder()
        .baseUrl("https://futurestud.io/");

Retrofit retrofit = builder.build();

FileDownloadClient fileDownloadClient = retrofit.create(FileDownloadClient.class);
Call<ResponseBody> responseBodyCall = fileDownloadClient.downloadFile();
responseBodyCall.enqueue(new Callback<ResponseBody>() {
@Override
public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
	inputStream = response.body().byteStream();
}
...
});
```

> ##### But there is one problem

Retrofit, by default , keeps the response in memory. So basically the server response with some data, Retrofit will keep connection open wait until everything is there and pass it over to Gson for conversion.

When server response binary data file can pretty large.If you download a 50mb file, Retrofit try to keep those 50mb in memory.If file gets pretty large device will start get crash because they don't have that much memory. 

​	Retrofit solution here is to stream the response to app. Whenever the server response with the first bytes it will hand over the result right away. It won't wait until the entire response is there. The app then has to deal with the incoming stream of bytes and write it to the disk. we need listen to that open stream of bytes and write it down.  

we need add new annotation @Streaming to downloadClient interface. 

```java
@Streaming
@GET()
Call<ResponseBody> downloadFile(@Url String url);
```



After doing this changes app will crash with error `networkonmainthreadexception`

Retrofit goes onto a background thread to execute the request and when the request comes back it call the UI thread in the onResponse() method. So what happen is we have network stream of data coming in and we are trying to write it to disk on UI thread. this way we get `networkonmainthreadexception` . So solution is write response body on background thread. 



# Simple Error Handling

OnResponse callback means that server returns something but it does't mean its a successful response.

**it also can be an error response.**

So to differentiate between successful response and error response retrofit provide method **isSuccessful()**

```java
call.enqueue(new Callback<ResponseBody>() {
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            Log.d("TAG", "Successful Response");
        }else{
            Log.d("TAG", "error Response");
        }
    }
...
});
```

We can get http response code by using method **code**

```
response.code();
```



