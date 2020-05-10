package DefaultPackage;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.Payload;

public class Basics {

	public static void main(String[] args) {

     //Verify AddPlace API is working as expected or not
		
		//given : All input Details
		//when : Submit the APIs , https Methods
		//Then : Validate the response

//		String response = 	given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body(Payload.AddPlace()).when().post("/maps/api/place/add/json")
//				.then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();

		RestAssured.baseURI = "https://rahulshettyacademy.com"; 
	String response = 	given().queryParam("key", "qaclick123").header("Content-Type","application/json").body(Payload.AddPlace()).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		           
	System.out.println(response);
	
	JsonPath js = new JsonPath(response); //For Parsing the JSON
	String placeId = js.get("place_id");
	System.out.println(placeId);
	
	
	//Update Place
	
	String newAddress= "Wolf of Wall Street";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n" + "\"place_id\":\""+placeId+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
		
		//Get Place
		
	//	given().log().all().queryParam("key", "qaclick123").queryParam("place_id", "placeId").get("/maps/api/place/get/json").then().assertThat().log().all().statusCode(200).body("address", equalTo("newAddress"));
	
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", "placeId").get("/maps/api/place/get/json").then().assertThat().log().all().statusCode(200).body("address", equalTo("newAddress"))
		.extract().response().asString();
		

      JsonPath js1 = new JsonPath(getPlaceResponse);
		String actualAddress = js1.getString("address");
		
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
	}

}
