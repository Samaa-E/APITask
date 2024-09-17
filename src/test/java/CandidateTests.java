import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CandidateTests {

    private static final String BASE_URL = "https://opensource-demo.orangehrmlive.com";

    public static void main(String[] args) {
        RestAssured.baseURI = BASE_URL;

        String token = login();
        addCandidate(token);
        int candidateId = 0;
        deleteCandidate(token,candidateId);
    }

    private static String login() {
        // Step 1: Login to get authentication token
        String token = given()
                .header("Content-Type", "application/json")
                .body("{ \"username\": \"admin\", \"password\": \"admin123\" }")
                .when()
                .post("/web/index.php/auth/login")
                .then()
                .statusCode(200)
                .extract().path("token"); // Extract authentication token
        return token;
    }

    private static void addCandidate(String token) {
        // Step 2: Add a candidate
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)  // Authorization
                .body("{"
                        + "\"firstName\": \"John\","
                        + "\"email\": \"johndoe@example.com\","
                        + "}")
                .when()
                .post("/web/index.php/recruitment/addCandidate")  // Assume this is the candidate creation endpoint
                .then()
                .statusCode(201)  // Assuming 201 Created
                .extract().response();

        System.out.println("Add Candidate Response: " + response.asString());

    }

    private static void deleteCandidate(String token, int candidateId ) {
        // Step 2: Delete a candidate using Candidate ID
        //String candidateId = "5";  // Replace with actual candidate ID from the previous addition or from the list
        Response response1 = given()
                .header("Authorization", "Bearer " + token)  // Authorization
                .when()
                .delete("/web/index.php/recruitment/viewCandidates" + candidateId)  // Assume this is the delete endpoint
                .then()
                .statusCode(200)  // Assuming 200 OK
                .extract().response();

        System.out.println("Delete Candidate Response: " + response1.asString());
    }
    }

