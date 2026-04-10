import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DataDrivenLoginTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigReader.get("base.url");
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
                // email, password, expectedStatusCode
                {"eve.holt@reqres.in", "cityslicka", 200},
                {"eve.holt@reqres.in", "wrongpassword", 200},
                {"invalid@email.com", "cityslicka", 400},
                {"", "cityslicka", 400},
                {"eve.holt@reqres.in", "", 400}
        };
    }

    @Test(dataProvider = "loginData")
    public void verifyLoginWithMultipleData(
            String email, String password, int expectedStatus) {

        System.out.println("Testing with email: " + email +
                " | password: " + password +
                " | expected status: " + expectedStatus);

        given()
                .header("x-api-key", ConfigReader.get("api.key"))
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"" + email +
                        "\", \"password\": \"" + password + "\" }")
                .when()
                .post("/login")
                .then()
                .statusCode(expectedStatus);
    }
}