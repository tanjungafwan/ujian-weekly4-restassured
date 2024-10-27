package com.juaracoding.movie.Popular;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.List;



public class TestMoviePopular {
    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZjY0YzU3MTc0YjMzNjg5MTJkZDkwYzkzNTQ0MTAzZSIsIm5iZiI6MTcyOTc3MjE3NS45NzgwOTEsInN1YiI6IjY3MTkwODdiYTRhYzhhNDMyYzViZTEwNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ko20hgspSIj5bt_zdxcIhepMqOwrxR4E4Wey7wbhdJ8";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test (priority = 2)
    public void testStatusWithValidUrl(){
        RequestSpecification request = RestAssured.given();
        request.header("Authorization",myToken);
        Response response = request.get("/movie/popular");
        int statusCode = response.statusCode();
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(statusCode, 200);
    }

    @Test (priority = 1)
    public void testStatusWithInvalidUrl(){
        RequestSpecification request = RestAssured.given();
        request.header("Authorization",myToken);
        Response response = request.get("/movie/popula");
        int statusCode = response.statusCode();
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(statusCode, 404);
        response.prettyPrint();
    }

    @Test (priority = 3)
    public void testMoviePopular(){
        RequestSpecification request = RestAssured.given();
        request.queryParam("language", "en-US");
        request.queryParam("page", "1");
        request.header("Authorization", myToken);
        Response response = request.get("/movie/popular");
        int statusCode = response.statusCode();
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(response.getBody().jsonPath().getInt("page"), 1);
        List<String> popularMovieTitle = response.jsonPath().getList("results.title");
        for (String title : popularMovieTitle) {
            System.out.println("Popular Movie Title :" +title);
        }
    }
}
