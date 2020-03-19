package com.example.jetpackcomponentsapp

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.jetpackcomponentsapp.web.CountryResponseModel
import com.google.gson.Gson
import org.json.JSONException
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class WebInstrumentedTest {

    lateinit var instrumentationContext : Context

    @Before
    fun init() {
        //instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        instrumentationContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    private fun readJSONCountryTest()  : String {
        var json : String? = null
        try {
            val  inputStream : InputStream = instrumentationContext.getAssets().open("country.json")
            val size : Int = inputStream.available()
            val buffer = ByteArray(size)
            val charset : Charset = Charsets.UTF_8

            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
            assert(true)
        } catch (ex : IOException) {
            ex.printStackTrace()
            assert(false)
        } catch (ex : JSONException) {
            ex.printStackTrace()
            assert(false)
        }
        return json?:""
    }

    @Test
    fun convertToGSONCountryTest() {
        val gson : Gson = Gson()
        val json : String = readJSONCountryTest()

        val countryResponseArray : Array<CountryResponseModel>
        val countryResponseList : List<CountryResponseModel>

        countryResponseArray  = gson.fromJson(json, Array<CountryResponseModel>::class.java)
        countryResponseList  = gson.fromJson(json, Array<CountryResponseModel>::class.java).toList()
        assert(true)
    }
}