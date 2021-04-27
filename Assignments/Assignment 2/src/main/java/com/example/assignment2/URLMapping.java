package com.example.assignment2;

public class URLMapping {

    public static final String API_PATH = "/api";
    public static final String BOOKS =  API_PATH + "/books";
    public static final String EXPORT_TYPE = "/export/{type}";
    public static final String ENTITY = "/{id}";
    public static final String OUT_OF_STOCK = "/out-of-stock";
    public static final String SEARCH_BOOKS = "/search-books/{search}";
    public static final String SELL_BOOK = "/sell-book/{id}/{quantity}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/users";

}
