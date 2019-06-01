import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.specification.RequestSpecification;
import static constants.Path.*;
import static constants.User.*;
import static io.restassured.RestAssured.*;

public class Base {

    static RequestSpecification requeetGet(){
        return given()
                .auth()
                .preemptive()
                .basic(LOGIN, PASSWORD)
                .filter(new RequestLoggingFilter())
                .filter(new RequestLoggingFilter())
                .baseUri(BASE_PATH);
    }
}
