import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class SpotifyTest {

    String id;
    private String tokenvalue="Bearer BQAL6DSjXkK_0eNGEXKswXLF3ubbgSamIB3t8bHdSkcxbU0IdgAf5ArM2dw0zeKbjj_sHpvBfDW-AbluwCohh2ZKbqiFOhdy4GTOG5lAHC1b_xZUujSWaisZyD_vRNB1YoSx6v-52TBhBgB91QlP52brJR4tnfENO59yW8EVXBGnmA6VWIm-4fvszIujyJCCEsiWEBRhB3qwE6xd0IuogwAyV5J7JmqDqyN8k2iLw9Xih4StPXSU2dFcJT8U_nVvUXKF-d7a_f9q8qtV-AsNmdxSnE77SQ";
    Response response;

    @Test
    public void givenMethodForGetId() throws ParseException {
            Response response = RestAssured.given()
                    .accept( "application/json")
                    .contentType("application/json")
                    .header("Authorization", tokenvalue)
                    .when()
                    .get("https://api.spotify.com/v1/me");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
         id = (String) object.get("id");
        System.out.println(id);
       // System.out.println("id is"+id);
        response.then().assertThat().statusCode(200);

        System.out.println(id);
        Response response2 = RestAssured.given()
                .accept( "application/json")
                .contentType("application/json")
                .header("Authorization", tokenvalue)
                .when()
                .get("https://api.spotify.com/v1/users/"+id);
        response2.prettyPrint();
        response2.then().assertThat().statusCode(200);

        }

    @Test
    public void givenMethodForGettingPlaylistCount() throws ParseException {
        Response response2 =RestAssured. given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", tokenvalue)
                .when()
                .get("https://api.spotify.com/v1/me/playlists")
                .then()
                .extract().response();
        int playlistCount = response2.path("total");
       // int playListId=response2.path()
        response2.prettyPrint();
        System.out.println("Playlist count: "+playlistCount);
    }

  /*  @Test
    public void givenMethodCreatePlayList() {
        Response response = RestAssured.given()
                .accept( "application/json")
                .contentType("application/json")
                .header("Authorization", tokenvalue)
                .when()
                .get("https://api.spotify.com/v1/users/my55whm8jotn3kr4c54svsjq4/playlists");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

    }*/

/*    @Test
    public void givenMethodForUpdatePlayList() {
        Response response2 =RestAssured. given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", tokenvalue)
                .when()
                .put("https://api.spotify.com/v1/playlists/6qGrd3sae3c3XSLJ0oLYxX");

    }*/
/*    @Test
    public void givenMethodForUpdatePlayList() {
        Response response2 =RestAssured. given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", tokenvalue)
                .when()
                .put("https://api.spotify.com/v1/playlists/6qGrd3sae3c3XSLJ0oLYxX");

    }*/

}
