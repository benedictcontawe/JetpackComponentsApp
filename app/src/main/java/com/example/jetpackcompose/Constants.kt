package com.example.jetpackcompose

object Constants {
    public const val API_DOMAIN = "https://api.nasa.gov"
    public const val API_GET = "planetary/apod"
    public const val API_KEY = "lebNOALEyzgIAKKggQws8YGcWVLDK72R0l2Twryx"
    public const val API_AUTHORIZATION = "Basic YWZwaWRldnVzZXI6YWZwaWRldlBAc3N3MHJk"
    public const val APPEND_COUNT : Int = 5
    public const val CONNECT_TIMEOUT : Long = 15L // seconds to connect to the server
    public const val READ_TIMEOUT : Long = 30L    // seconds to wait for the response (APOD with count= can be slow)
    public const val WRITE_TIMEOUT : Long = 15L   // seconds to send the request
    public const val BLANK = " "
}