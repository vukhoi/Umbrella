package com.example.umbrella;

public class Util {
    public static String ZIP = "ZIP";
    public static String UNIT = "UNIT";
    public static String API_KEY = "0e66462b093ba2bcf082707ea1910bc2";
    public static String INPUT_UNIT = "";
    public static String INPUT_ZIPCODE = "";
    public static double TEMP_WARM_START_F = 60;
    public static double TEMP_WARM_START_C = 15.56;
    public static int INTERNET_PERMISSION_REQUESTCODE;
    public static int NUM_ENTRY_PER_ROW = 4;


    public static boolean verifyZip(String zip){
        return zip.length() == 5;
    }
}
