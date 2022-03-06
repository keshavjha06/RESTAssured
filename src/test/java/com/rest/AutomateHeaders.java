package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;


public class AutomateHeaders {

    @Test
    public void multiple_headers() {
        Header header = new Header("headerName", "value1");
        Header matchHeader = new Header("x-mock-match-request-headers", "headerName");
        given().baseUri("https://b4de06c3-2d59-44d3-94f2-3ac78156fd20.mock.pstmn.io").
                header(header).
                header(matchHeader).
        when().
                get("/get").
         then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @Test
    public void multiple_headers_using_Headers() {
        Header header = new Header("headerName", "value2");
        Header matchHeader = new Header("x-mock-match-request-headers", "headerName");

        Headers headers = new Headers(header , matchHeader);
        given().baseUri("https://b4de06c3-2d59-44d3-94f2-3ac78156fd20.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @Test
    public void multiple_headers_using_map() {
        HashMap<String , String > headers = new HashMap<>();
        headers.put("headerName" , "value2");
        headers.put("x-mock-match-request-headers", "headerName");

        given().baseUri("https://b4de06c3-2d59-44d3-94f2-3ac78156fd20.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @Test
    public void multi_value_header_in_the_request() {
        Header header1 = new Header("multiValueHeader", "value1");
        Header header2 = new Header("multiValueHeader", "value2");

        Headers headers = new Headers(header1 , header2);

        given().baseUri("https://b4de06c3-2d59-44d3-94f2-3ac78156fd20.mock.pstmn.io").
               // headers(headers).
                headers(headers).
                log().headers().
         when().
                get("/get").
         then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @Test
    public void assert_response_headers() {
        HashMap<String , String > headers = new HashMap<>();
        headers.put("headerName" , "value1");
        headers.put("x-mock-match-request-headers", "headerName");

        given().baseUri("https://b4de06c3-2d59-44d3-94f2-3ac78156fd20.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
         then().
                assertThat().
                statusCode(200).
                headers("responseHeader" , "resValue1" ,"X-RateLimit-Limit" , "120" );

    }
    @Test
    public void extract_response_headers() {
        HashMap<String , String > headers = new HashMap<>();
        headers.put("headerName" , "value1");
        headers.put("x-mock-match-request-headers", "headerName");

        Headers extractedHeaders = given().baseUri("https://b4de06c3-2d59-44d3-94f2-3ac78156fd20.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200).
                extract().
                headers();
        for (Header header : extractedHeaders) {
            System.out.print("header name = " + header.getName() + " ,");
            System.out.println("header value = " + header.getValue());
        }

        /*System.out.println("header name = " + extractedHeaders.get("responseHeader").getName());
        System.out.println("header value = " + extractedHeaders.get("responseHeader").getValue());
        System.out.println("header value = " + extractedHeaders.getValue("responseHeader"));*/
    }

    @Test
    public void extract_multivalue_response_header() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("headerName", "value1");
        headers.put("x-mock-match-request-headers", "headerName");

        Headers extractedHeaders = given().baseUri("https://b4de06c3-2d59-44d3-94f2-3ac78156fd20.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200).
                extract().
                headers();
        List<String> values = extractedHeaders.getValues("multiValueHeader");
        for (String value : values)
            System.out.println(value);
    }

}
