package com.entigrity.webservice;

public class ApiUtilsNew {


    private ApiUtilsNew() {

    }

    public static final String BASE_URL = "https://my-cpe.com/api/";

    public static APIService getAPIService() {

        return RetrofitClientNew.getClient(BASE_URL)
                .create(APIService.class);
    }


}
