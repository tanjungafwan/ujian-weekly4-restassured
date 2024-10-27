package com.juaracoding.movie.PostRating;

import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.AssertionSupport;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class TestMoviePostRating {

    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZjY0YzU3MTc0YjMzNjg5MTJkZDkwYzkzNTQ0MTAzZSIsIm5iZiI6MTcyOTc3MjE3NS45NzgwOTEsInN1YiI6IjY3MTkwODdiYTRhYzhhNDMyYzViZTEwNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ko20hgspSIj5bt_zdxcIhepMqOwrxR4E4Wey7wbhdJ8";
    String sessionId = "868cdf391d789f88de9c65e5a12fc898b9e8caa5";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test
    public void testPostRating(){
        RequestSpecification request = RestAssured.given()
                .header("Authorization",myToken)
                .contentType("application/json;charset=utf-8");
        String jsonBody = "{\"value\": 10}";
        Response response = request
                .body(jsonBody)
                .post("/movie/278/rating?guest_session_id=" +sessionId);
        int statusCode = response.statusCode();
        String message = response.getBody().jsonPath().getString("status_message");
        System.out.println(statusCode);
        System.out.println(message);
        Assert.assertEquals(201, statusCode);
        Assert.assertEquals("Success", message);
    }

    @Test
    public void testPostRatingWithNoPermission(){
        RequestSpecification request = RestAssured.given()
                .header("Authorization",myToken)
                .contentType("application/json;charset=utf-8");
        String jsonBody = "{\"value\": 10}";
        Response response = request
                .body(jsonBody)
                .post("https://api.themoviedb.org/3/movie/278/rating?guest_session_id=" +sessionId);
        int statusCode = response.statusCode();
        String message = response.getBody().jsonPath().getString("status_message");
        System.out.println(statusCode);
        System.out.println(message);
        Assert.assertEquals(401, statusCode);
        response.prettyPrint();

    }

}
