package com.entigrity.webservice;

public class ApiUtilsNew {


    private ApiUtilsNew() {

    }

    // Base URL for Live API..
     public static final String BASE_URL = "https://my-cpe.com/api/";
//
    // Base URL for TEST API..
//    public static final String BASE_URL = "http://testing-website.in/api/";

    public static APIService getAPIService() {

        return RetrofitClientNew.getClient(BASE_URL)
                .create(APIService.class);
    }


}
