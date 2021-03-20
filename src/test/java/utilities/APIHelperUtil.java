package utilities;

import io.restassured.RestAssured;

public class APIHelperUtil {

    public static void setBaseURI(String baseUri){
        RestAssured.baseURI=baseUri;
    }

    public static void setBasePath(String basePath){
        RestAssured.basePath=basePath;
    }

    public static String getBasePath(){
        return RestAssured.basePath;
    }

}
