package com.juaracoding.movie.MovieDetail;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class testMovieDetails {

    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZjY0YzU3MTc0YjMzNjg5MTJkZDkwYzkzNTQ0MTAzZSIsIm5iZiI6MTcyOTc3MjE3NS45NzgwOTEsInN1YiI6IjY3MTkwODdiYTRhYzhhNDMyYzViZTEwNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ko20hgspSIj5bt_zdxcIhepMqOwrxR4E4Wey7wbhdJ8";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test
    public void testGetValidMovieDetails() {
        RequestSpecification request = RestAssured.given();
        request.queryParam("language", "en-US");
        request.header("Authorization", myToken);
        Response response = request.get("/movie/278");
        int statusCode = response.statusCode();
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(statusCode, 200);
        String movieTitles = response.jsonPath().getString("title");
        System.out.println("Movie Title: "+movieTitles);
        response.prettyPrint();
    }

    @Test
    public void testGetInvalidMovieDetails() {
        RequestSpecification request = RestAssured.given();
        request.queryParam("language", "en-US");
        request.header("Authorization", myToken);
        Response response = request.get("/movie/001");
        int statusCode = response.statusCode();
        System.out.println(response.statusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(statusCode, 404);
        response.prettyPrint();
    }


}
