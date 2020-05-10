package DefaultPackage;
import Files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	int i = 10;
	int j = 5;

	public static void main(String[] args) {

		JsonPath js = new JsonPath(Payload.coursePrice());

		// 1. Print no. of Courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);

		// 2. Print Purchase Amount
		int purchaseAmt = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmt);

		// 3. Print Title of First course
		String FirstCourseTitle = js.get("courses[0].title");
		System.out.println(FirstCourseTitle);

		// To iterate over all courses Title and their respective Prices

		for (int i = 0; i < count; i++) {
			String listOfCourses = js.get("courses[" + i + "].title");
			int amount = js.get("courses[" + i + "].price");
			System.out.println(listOfCourses + " : " + amount);
		}

		System.out.println("********************************");

//		for (int i = 0; i < count; i++) {
//
//			String courseTitle = js.get("courses[" + i + "].title");
//			if (courseTitle.equalsIgnoreCase("RPA")) {
//
//				Object obj = js.get("courses[ " + i + "].copies");
//				System.out.println(obj);
//				break;
//			}
//		}
		
		
		for (int i = 0; i < count; i++) {

			String courseTitle = js.get("courses[" + i + "].title");
			
			
		}

	}
}
