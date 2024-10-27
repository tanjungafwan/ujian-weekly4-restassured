package com.juaracoding.movie.NowPlaying;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.List;


public class TestMovieNowPlaying {

    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZjY0YzU3MTc0YjMzNjg5MTJkZDkwYzkzNTQ0MTAzZSIsIm5iZiI6MTcyOTc3MjE3NS45NzgwOTEsInN1YiI6IjY3MTkwODdiYTRhYzhhNDMyYzViZTEwNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ko20hgspSIj5bt_zdxcIhepMqOwrxR4E4Wey7wbhdJ8";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test (priority = 2)
    public void testStatusWithToken(){
        RequestSpecification request = RestAssured.given();
        request.header("Authorization",myToken);
        Response response = request.get("/movie/now_playing");
        int statusCode = response.statusCode();
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(statusCode, 200);
    }

    @Test (priority = 1)
    public void testStatusWithoutToken(){
        RequestSpecification request = RestAssured.given();
        Response response = request.get("/movie/now_playing");
        int statusCode = response.statusCode();
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(statusCode, 401);
        response.prettyPrint();
    }

    @Test (priority = 3)
    public void testNowPlaying(){
        RequestSpecification request = RestAssured.given();
        request.queryParam("language", "en-US");
        request.queryParam("page", "1");
        request.header("Authorization", myToken);
        Response response = request.get("/movie/now_playing");
        int statusCode = response.statusCode();
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(response.getBody().jsonPath().getInt("page"), 1);
        List<String> movieTitles = response.jsonPath().getList("results.title");
        for (String title : movieTitles) {
            System.out.println("Movie Title: " +title);
        }
    }



}
