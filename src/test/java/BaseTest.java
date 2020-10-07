import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class BaseTest {
    Response response;

    private static final String URL = "https://reqres.in";

    public Response doGetRequest(String request, int statusCode) {
        RestAssured.defaultParser = Parser.JSON;

        response = given()
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(String.format("%s%s", URL, request))
                .then().statusCode(statusCode).contentType(ContentType.JSON)
                .extract().response();
        return response;
    }

    public Response doPostRequest(String body, String request, int statusCode) {
        RestAssured.defaultParser = Parser.JSON;

        response = given()
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .body(body)
                .when().post(String.format("%s%s", URL, request))
                .then().statusCode(statusCode)
                .extract().response();
        return response;
    }

    public Response doPutRequest(String body, String request, int statusCode) {
        RestAssured.defaultParser = Parser.JSON;

        response = given()
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .body(body)
                .when().put(String.format("%s%s", URL, request))
                .then().statusCode(statusCode)
                .extract().response();
        return response;
    }

    public Response doPatchRequest(String body, String request, int statusCode) {
        RestAssured.defaultParser = Parser.JSON;

        response = given()
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .body(body)
                .when().patch(String.format("%s%s", URL, request))
                .then().statusCode(statusCode)
                .extract().response();
        return response;
    }

    public Response doDeleteRequest(String request, int statusCode) {
        RestAssured.defaultParser = Parser.JSON;

        response = given()
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().delete(String.format("%s%s", URL, request))
                .then().statusCode(statusCode)
                .extract().response();
        return response;
    }

    public void validateResponseViaJsonPath(String jsonPath, String expected) {
        String getJsonPath = response.jsonPath().getString(jsonPath);
        assertEquals(getJsonPath, expected);
    }

    public void validateResponse(String expected) {
        assertEquals(response.asString(), expected);
    }

}
