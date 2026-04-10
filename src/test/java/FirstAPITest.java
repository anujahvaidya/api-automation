import io.restassured.RestAssured;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FirstAPITest {

    @Test
    public void verifyGetUsers() {
        RestAssured.baseURI = "https://reqres.in/api";

        given()
                .header("x-api-key", "pro_6b434c16db81dba5301559c5ee9b641f865e7172e7c1e94e794ec49d4766f3f6")
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2));
    }
}
