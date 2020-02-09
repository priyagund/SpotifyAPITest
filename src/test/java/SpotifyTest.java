import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    String userId;
    String playListId;
    private String tokenvalue = "Bearer BQC7_EagbX8RAngsjTVPBdSiXAX0r-UfKM5zZhbvKC7Da0s_a-3-GTKxmR3uLJmKyjLyllQxb-yJNdszXx09RF28ctR9V4dtA4UJ1C2RGoGQJ07avY5jSB60Rvf0AqohvRf0eWlfkL3e41pX6S2w2VN8RATtbDarh-LKEg-Ci8k67UgFFHHvSiPTQkpNINxyci2Kte5xnXwZmwuUFq5X4RHIn2h-XW8UbVRdWSNFgP5MKpDwVa9HSGkzbQPw90TTbIAfb20UYyZjqPAXWMag3x_3GYHwsw";
    Response response;

    //Test for get user Id
    @Test
    public void givenMethodForGetId() throws ParseException {
        Response response = RestAssured.given()
                .accept("application/json")
                .contentType("application/json")
                .header("Authorization", tokenvalue)
                .when()
                .get("https://api.spotify.com/v1/me");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        userId = (String) object.get("id");
        System.out.println(userId);
        response.then().assertThat().statusCode(200);

        //for verifing user
        System.out.println(userId);
        Response responseForUser = RestAssured.given()
                .accept("application/json")
                .contentType("application/json")
                .header("Authorization", tokenvalue)
                .when()
                .get("https://api.spotify.com/v1/users/" + userId);
        responseForUser.prettyPrint();
        responseForUser.then().assertThat().statusCode(200);

        //create playlist
        Response responseForCreatePL = RestAssured.given()
                .accept("application/json")
                .contentType("application/json")
                .header("Authorization", tokenvalue)
                .body("{\"name\": \"New add Playlist 1\",\"description\": \"New playlist description\",\"public\": false" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/users/" + userId + "/playlists");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    //Test for finding count of of playlist
    @Test
    public void givenMethodForGettingPlaylistCountAndUpdatePlayList() throws ParseException {
        Response responseForPlayListCount = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", tokenvalue)
                .when()
                .get("https://api.spotify.com/v1/me/playlists")
                .then()
                .extract().response();
        int playlistCount = responseForPlayListCount.path("total");
        String playListId = responseForPlayListCount.path("items[1].id");
        responseForPlayListCount.prettyPrint();
        System.out.println("Playlist count: " + playlistCount);
        System.out.println("palylist id: " + playListId);

        // Updating playlist
        Response responseOfPlaylist = RestAssured.given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", tokenvalue)
                .body("{\"name\": \"kumar sanu Songs\",\"description\": \"new song\",\"public\": false}")
                .when()
                .put("https://api.spotify.com/v1/playlists/" + playListId);
        ResponseBody responseBody = responseOfPlaylist.getBody();
        Object jsonElement = new JsonParser().parse(responseBody.prettyPrint());
        responseOfPlaylist.then().assertThat().statusCode(200);

    }
}
