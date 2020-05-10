package DefaultPackage;
import org.testng.Assert;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	
	@Test
	public static void sumOfCourses() {
		
		int sum = 0;
		JsonPath js = new JsonPath(Payload.coursePrice());
		int count = js.getInt("courses.size");
		
		for (int i=0;i<count;i++) {
			
			int price = js.get("courses["+i+"].price");
			int copy= js.get("courses["+i+"].copies");
			int total = price*copy;
			System.out.println(total);
			sum = sum + total;
		}
		System.out.println(sum);
		
		int expectedAmount = js.getInt("dashboard.purchaseAmount");
		
		Assert.assertEquals(sum, expectedAmount);
	}
}
