https://www.itsalif.info/content/android-volley-tutorial-http-get-post-put


Android Volley Tutorial – Making HTTP GET, POST, PUT
 July 2013	    mobile
Google released Android Volley Library around May/June 2013, which has been internally used by Google for some time. It is supposed to provide Fast Networking Operations and also takes care of Threads nicely. If you are unfamiliar with Volley, please see Google I/O 2013 Video

Unfortunately, there is almost no documentation on Volley. So, I put together code snippets on how to make Volley HTTP Requests (GET, POST, PUT, DELETE).

### Setting up Android Volley Library
Setting up is straight-forward. Clone the Volley project from here and then import the Volley into project. 
A comprehensive tutorial on setting up can be found here.

### Key Classes of Android Volley
The following are the Key classes of Volley:

– RequestQueue: A Queue containing the Network/HTTP Requests that needs to be made.
– Request: A Base Class which contains Network related information like HTTP Methods.
– StringRequest: HTTP Request where the response is parsed a String. View Source
– JsonObjectRequest: HTTP Request where the response is JSONObject. View Source
Getting Started with Android Volley
At first make a RequestQueue, which holds the HTTP Requests. View Source. Ideally, the RequestQueue should be made once and then referred to it across the Application. The Application is an ideal place to make it.


RequestQueue queue = Volley.newRequestQueue(this);  // this = context


### -------------------- Making GET Requests --------------

Making GET Requests is simple. The example below uses JsonObjectRequest. It prepares a JsonObjectRequest and
passes and then adds it to RequestQueue. The JsonObject accepts 4 parameters
(Http method, Url, Json values, Response Listener – Invoked on success, Error Listener – Invoked on failure).

------------------------ code ----------------------------
final String url = "http://httpbin.org/get?param1=hello";
 
// prepare the Request
JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
    new Response.Listener<JSONObject>() 
    {
        @Override
        public void onResponse(JSONObject response) {   
                        // display response     
            Log.d("Response", response.toString());
        }
    }, 
    new Response.ErrorListener() 
    {
         @Override
         public void onErrorResponse(VolleyError error) {            
            Log.d("Error.Response", response);
       }
    }
);
 
// add it to the RequestQueue   
queue.add(getRequest);


### -------------------- Making POST Requests ---------------------------
For a POST request, to add form parameters/values, the getParams() method needs to be overridden and a Map needs to be returned.
----------------------- code --------------------------------------------
url = "http://httpbin.org/post";
StringRequest postRequest = new StringRequest(Request.Method.POST, url, 
    new Response.Listener<String>() 
    {
        @Override
        public void onResponse(String response) {
            // response
            Log.d("Response", response);
        }
    }, 
    new Response.ErrorListener() 
    {
         @Override
         public void onErrorResponse(VolleyError error) {
             // error
             Log.d("Error.Response", response);
       }
    }
) {     
    @Override
    protected Map<String, String> getParams() 
    {  
            Map<String, String>  params = new HashMap<String, String>();  
            params.put("name", "Alif");  
            params.put("domain", "http://itsalif.info");
             
            return params;  
    }
};
queue.add(postRequest);

### ----------------------------- Making PUT Requests ------------------------------
Creating PUT Request is same as POST basically.
--------------------------------- code ---------------------------------------------
url = "http://httpbin.org/put";
StringRequest putRequest = new StringRequest(Request.Method.PUT, url, 
    new Response.Listener<String>() 
    {
        @Override
        public void onResponse(String response) {
            // response
            Log.d("Response", response);
        }
    }, 
    new Response.ErrorListener() 
    {
         @Override
         public void onErrorResponse(VolleyError error) {
                         // error
             Log.d("Error.Response", response);
       }
    }
) {
 
    @Override
    protected Map<String, String> getParams() 
    {  
            Map<String, String>  params = new HashMap<String, String> ();  
            params.put("name", "Alif");  
            params.put("domain", "http://itsalif.info");
             
            return params;  
    }
 
};
 
queue.add(putRequest);

### --------------------------- Making DELETE Requests ----------------------------------
------------------------------- Code ----------------------------------------------------
url = "http://httpbin.org/delete";
StringRequest dr = new StringRequest(Request.Method.DELETE, url, 
    new Response.Listener<String>() 
    {
        @Override
        public void onResponse(String response) {
            // response
            Toast.makeText($this, response, Toast.LENGTH_LONG).show();
        }
    }, 
    new Response.ErrorListener() 
    {
         @Override
         public void onErrorResponse(VolleyError error) {
             // error.
              
       }
    }
);
queue.add(dr);



