package DefaultPackage;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJson {

	@Test
	public void addBook() throws IOException {

		RestAssured.baseURI = "http://216.10.245.166";

		String response = given().header("Content-Type", "application/json")
				.body(GenerateStringFromResourse("C:\\Users\\2905p\\Documents\\RestAPI\\AddBookDetails.json")).when()
				.post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath js = new JsonPath(response);
		String Id = js.get("ID");

	}

	public static String GenerateStringFromResourse(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

}
