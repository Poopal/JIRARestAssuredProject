package Files;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraTest {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://localhost:9090";
		// Note:==> public class SessionFilter extends Object implements Filter.
		// A session filter can be used record the session id returned from the server
		// as well as automatically apply this session id in subsequent requests

		// CreateSession
		SessionFilter session = new SessionFilter();

		// RelaxedHTTPSValidation()---> To ignore HTTPS Validation
		given().header("Content-Type", "application/json")
				.body("{ \"username\": \"2905poonam\", \"password\": \"Rushmore@123\" }").log().all().filter(session)
				.when().post("/rest/auth/1/session").then().extract().response().asString();

		
		String expectedMessage = "Its fun adding comment from Test Automation Framework";
		// Add Comment
		String addComment = given().pathParam("id", "10103").log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"body\": \""+expectedMessage+"\",\r\n"
						+ "    \"visibility\": {\r\n" + "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n" + "    }\r\n" + "}")
				.filter(session).when().post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat()
				.statusCode(201).extract().asString();

		JsonPath js = new JsonPath(addComment);
		String commentid = js.getString("id");

		// MultiPart--> To Upload File over a Server
		// Add Attachment
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("id", "10103")
				.header("Content-Type", "multipart/form-data")
				.multiPart("file", new File("C:\\Users\\2905p\\eclipse-workspace\\JIRADemoProject\\Jira.txt")).when()
				.post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);

		// Get Issue

		String getIssue = given().filter(session).pathParam("id", "10103").queryParam("fields", "comment").when()
				.get("/rest/api/2/issue/{id}").then().log().all().extract().response().asString();

		System.out.println(getIssue);

		JsonPath js1 = new JsonPath(getIssue);
		int commentCount = js1.get("fields.comment.comments.size()");

		for (int i = 0; i < commentCount; i++) {
			String commentsList = js1.get("fields.comment.comments[" + i + "].id ").toString();
			System.out.println(commentsList);
			
			{
				
				if(commentsList.equalsIgnoreCase(commentid)) {
					
					String message = js1.get("fields.comment.comments[" + i + "].body").toString();
					System.out.println(message);
					
					Assert.assertEquals(message, expectedMessage);
					
					
					
				}
			}
		}

		
		
	}

}