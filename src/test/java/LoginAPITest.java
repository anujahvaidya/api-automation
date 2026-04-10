import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginAPITest extends BaseTest {

    @BeforeClass
    public void setup() {
       // RestAssured.baseURI = "https://reqres.in/api";
        baseURI=ConfigReader.get("base.url");
    }

    // TC_01 - Valid Login
    @Test
    public void verifyValidLogin() {
        System.out.println("Valid login case execution started");
        given()
                .header("x-api-key", ConfigReader.get("api.key"))
                .contentType(ContentType.JSON)
                //.body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }")
                .body("{ \"email\": \"" + ConfigReader.get("valid.email") +
                        "\", \"password\": \"" + ConfigReader.get("valid.password") + "\" }")
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    // TC_02 - Invalid Password
    @Test
    public void verifyInvalidLogin() {
        System.out.println("Invalid login case execution started");
        given()
                .header("x-api-key", ConfigReader.get("api.key"))
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"wrongpassword\" }")
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    // TC_03 - Missing Password
    @Test
    public void verifyMissingPassword() {
        System.out.println("Missing password execution started");
        given()
                .header("x-api-key", ConfigReader.get("api.key"))
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\" }")
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
                //.statusCode(403)
                //.body("error", equalTo("invalid_api_key"));
    }
}