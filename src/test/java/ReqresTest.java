import org.testng.annotations.Test;

public class ReqresTest extends BaseTest {

    @Test
    public void listUsers() {
        String expected = "Michael";
        doGetRequest("/api/users?page=2", 200);
        validateResponseViaJsonPath("data.first_name[0]", expected);
    }

    @Test
    public void singleUser() {
        String expected = "{\"data\":{\"id\":2,\"email\":\"janet.weaver@reqres.in\",\"first_name\":\"Janet\",\"last_name\":\"Weaver\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg\"},\"ad\":{\"company\":\"StatusCode Weekly\",\"url\":\"http://statuscode.org/\",\"text\":\"A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.\"}}";
        doGetRequest("/api/users/2", 200);
        validateResponse(expected);
    }

    @Test
    public void singleUserNotFound() {
        doGetRequest("/api/users/23", 404);
        validateResponse("{}");
    }

    @Test
    public void listResource() {
        String expected = "{\"page\":1,\"per_page\":6,\"total\":12,\"total_pages\":2,\"data\":[{\"id\":1,\"name\":\"cerulean\",\"year\":2000,\"color\":\"#98B2D1\",\"pantone_value\":\"15-4020\"},{\"id\":2,\"name\":\"fuchsia rose\",\"year\":2001,\"color\":\"#C74375\",\"pantone_value\":\"17-2031\"},{\"id\":3,\"name\":\"true red\",\"year\":2002,\"color\":\"#BF1932\",\"pantone_value\":\"19-1664\"},{\"id\":4,\"name\":\"aqua sky\",\"year\":2003,\"color\":\"#7BC4C4\",\"pantone_value\":\"14-4811\"},{\"id\":5,\"name\":\"tigerlily\",\"year\":2004,\"color\":\"#E2583E\",\"pantone_value\":\"17-1456\"},{\"id\":6,\"name\":\"blue turquoise\",\"year\":2005,\"color\":\"#53B0AE\",\"pantone_value\":\"15-5217\"}],\"ad\":{\"company\":\"StatusCode Weekly\",\"url\":\"http://statuscode.org/\",\"text\":\"A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.\"}}";
        doGetRequest("/api/unknown", 200);
        validateResponse(expected);
    }

    @Test
    public void singleResource() {
        String expected = "{\"data\":{\"id\":2,\"name\":\"fuchsia rose\",\"year\":2001,\"color\":\"#C74375\",\"pantone_value\":\"17-2031\"},\"ad\":{\"company\":\"StatusCode Weekly\",\"url\":\"http://statuscode.org/\",\"text\":\"A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.\"}}";
        doGetRequest("/api/unknown/2", 200);
        validateResponse(expected);
    }

    @Test
    public void singleResourceNotFound() {
        doGetRequest("/api/unknown/23", 404);
        validateResponse("{}");
    }

    @Test
    public void create() {
        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        doPostRequest(body, "/api/users", 201);
        validateResponseViaJsonPath("name", "morpheus");
        validateResponseViaJsonPath("job", "leader");
    }

    @Test
    public void putUpdate() {
        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        doPutRequest(body, "/api/users/2", 200);
        validateResponseViaJsonPath("name", "morpheus");
        validateResponseViaJsonPath("job", "zion resident");
    }

    @Test
    public void patchUpdate() {
        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        doPatchRequest(body, "/api/users/2", 200);
        validateResponseViaJsonPath("name", "morpheus");
        validateResponseViaJsonPath("job", "zion resident");
    }

    @Test
    public void delete() {
        doDeleteRequest("/api/users/2", 204);
        validateResponse("");
    }

    @Test
    public void registerSuccessful() {
        String body = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";
        doPostRequest(body, "/api/register", 200);
        validateResponseViaJsonPath("id", "4");
        validateResponseViaJsonPath("token", "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void registerUnsuccessful() {
        String body = "{\n" +
                "    \"email\": \"sydney@fife\"\n" +
                "}";
        doPostRequest(body, "/api/register", 400);
        validateResponseViaJsonPath("error", "Missing password");
    }

    @Test
    public void loginSuccessful() {
        String body = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";
        doPostRequest(body, "/api/login", 200);
        validateResponseViaJsonPath("token", "QpwL5tke4Pnpja7X4");
    }

    @Test
    public void loginUnsuccessful() {
        String body = "{\n" +
                "    \"email\": \"peter@klaven\"\n" +
                "}";
        doPostRequest(body, "/api/login", 400);
        validateResponseViaJsonPath("error", "Missing password");
    }

    @Test
    public void delayedResponse() {
        String expected = "{\"page\":1,\"per_page\":6,\"total\":12,\"total_pages\":2,\"data\":[{\"id\":1,\"email\":\"george.bluth@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Bluth\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg\"},{\"id\":2,\"email\":\"janet.weaver@reqres.in\",\"first_name\":\"Janet\",\"last_name\":\"Weaver\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg\"},{\"id\":3,\"email\":\"emma.wong@reqres.in\",\"first_name\":\"Emma\",\"last_name\":\"Wong\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/olegpogodaev/128.jpg\"},{\"id\":4,\"email\":\"eve.holt@reqres.in\",\"first_name\":\"Eve\",\"last_name\":\"Holt\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/marcoramires/128.jpg\"},{\"id\":5,\"email\":\"charles.morris@reqres.in\",\"first_name\":\"Charles\",\"last_name\":\"Morris\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/stephenmoon/128.jpg\"},{\"id\":6,\"email\":\"tracey.ramos@reqres.in\",\"first_name\":\"Tracey\",\"last_name\":\"Ramos\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/bigmancho/128.jpg\"}],\"ad\":{\"company\":\"StatusCode Weekly\",\"url\":\"http://statuscode.org/\",\"text\":\"A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.\"}}";
        doGetRequest("/api/users?delay=3", 200);
        validateResponse(expected);
    }

}
