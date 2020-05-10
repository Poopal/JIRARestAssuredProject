package DefaultPackage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		
		//AddBook
		RestAssured.baseURI ="http://216.10.245.166";
			
		String response = given().header("Content-Type", "application/json").body(Payload.addBook(isbn, aisle))
		.when()
		.post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String Id = js.get("ID");
		
		//DeleteBook
		
	}
		//Parameterization
		@DataProvider(name = "BooksData")
		public static Object[][] getData() {
			
			Object data[][] = new Object[][] {
				
				{"9232", "djdh23rpfj"},
				{"4853", "cmf5678edd"},
				{"2922", "293ghfg495"}
				
			};
			return data;
		}
	}
	
