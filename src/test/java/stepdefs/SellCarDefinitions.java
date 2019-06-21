package stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class SellCarDefinitions {

    private RequestSpecification request;
    private Response response;

    private String URL = "write_here_url";
    private String WAKEY = "write_here_wakey";

    private RequestSpecification getAuthenticatedRequest() {
        return given().param("wa_key", WAKEY);
    }

    @Given("that the user has access to a list of car brands in (.*)")
    public void car_brands_in(String locale) {

        request = getAuthenticatedRequest().param("locale", locale);
        response = request.given().get(URL + "/manufacturer");

        String body = response.getBody().asString();
        System.out.println("response of manufacturer types in: " + response.prettyPrint());

        assertEquals(200, response.getStatusCode());
        assertEquals("2147483647", response.jsonPath().getString("pageSize"));
        assertEquals("0", response.jsonPath().getString("page"));
        assertEquals("1", response.jsonPath().getString("totalPageCount"));
        body.contains("\"wkda\":");
    }

    @When("User selects the Aston Martin option")
    public void car_models() {
        String body = response.getBody().asString();
        body.contains("\"057\": \"Aston Martin\"");
    }

    @And("User selects the (.*) car in (.*)")
    public void a_list_of_cars(String locale, String manufacturer) {
        request = request.param("manufacturer", manufacturer).param("locale", locale);
        response = request.given().get(URL + "/main-types");

        System.out.println("response of main types in: " + response.prettyPrint());
        assertEquals(200, response.getStatusCode());
    }

    @And("in (.*) with the (.*) - (.*) selected, the user selects the year")
    public void car_model(String locale, String manufacturer, String mainType) {
        request = request.param("locale", locale).param("manufacturer", manufacturer).param("main-type", mainType);
        response = request.given().get(URL + "/built-dates");

        System.out.println("response of built dates in: " + response.prettyPrint());
        assertEquals(200, response.getStatusCode());
    }

    @Then("the user can receive offers")
    public void car_offers() {

    }
}
