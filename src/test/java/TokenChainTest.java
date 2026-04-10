import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TokenChainTest {

    private String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    // Step 1 - Login and extract token
    @Test
    public void extractTokenFromLogin() {
        token = given()
                .header("x-api-key", "pro_6b434c16db81dba5301559c5ee9b641f865e7172e7c1e94e794ec49d4766f3f6")
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }")
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract().path("token");

        System.out.println("Extracted Token: " + token);
    }

    // Step 2 - Use token to get user details
    @Test(dependsOnMethods = "extractTokenFromLogin")
    public void getUserWithToken() {
       Response response= given()
                .header("x-api-key", "pro_6b434c16db81dba5301559c5ee9b641f865e7172e7c1e94e794ec49d4766f3f6")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", notNullValue())
                .extract().response();
       int id= response.path("data.id");
       String fName=response.path("data.first_name");
       String email=response.path("data.email");

       System.out.println("User detail here is :-"+ id + " | "+email+" | "+fName);
    }
}