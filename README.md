# FoodMandu2jfksjdfkajsbckasjbfkasgfiasbckcksnfkl
jawbdkjasbdkasbdkSLDX:z
REST Client in our case is the Retrofit library that is used on the client side (Android) to make HTTP request to REST API, in our case, The Movie DB API and also process the response.
A REST API defines a set of functions which developers can perform requests and receive responses via HTTP protocol such as GET and POST. in our case, The Movie DB (TMDB) API is the REST API.
We can also simply say that a RESTful API is an application program interface (API) that uses HTTP requests to GET, PUT, POST and DELETE data.
To use Retrofit in your Android Application, you’ll need 3 major classes.
An Interface which defines the HTTP operations (Functions or methods)
According to Square, creators of Retrofit documentation, Retrofit turns your HTTP API into a Java interface. Sample codes for the interface and the method declared in it are as below:
public interface GitHubService {
@GET("users/{user}/repos")
Call<List<Repo>> listRepos(@Path("user") String user);
@GET("group/{id}/users")
Call<List<User>> groupList(@Path("id") int groupId);
}
Every method inside an interface represents one possible API call. It must have a HTTP annotation (GET, POST, etc.) to specify the request type and the relative URL. The return value wraps the response in a Call object with the type of the expected result.
Query parameters can also be added to a method.
@GET(“group/{id}/users”)
Call<List<User>> groupList(@Path(“id”) int groupId, @Query(“sort”) String sort);
You can use replacement blocks and query parameters to adjust the URL. A replacement block is added to the relative URL with {}. With the help of the @Path annotation on the method parameter, the value of that parameter is bound to the specific replacement block.
2. A Retrofit class which generates an implementation of the GitHubService interface. The below sample code would be inside the Retrofit class and this is how it creates an instance of Retrofit and implements the listRepos() method that’s in the GitHubService Interface.
Retrofit retrofit = new Retrofit.Builder()
.baseUrl(“https://api.github.com/")
.build();
GitHubService servic
