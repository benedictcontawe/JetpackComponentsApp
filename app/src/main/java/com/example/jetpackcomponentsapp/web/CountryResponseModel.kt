package com.example.jetpackcomponentsapp.web

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CountryResponseModel {

    @SerializedName("alpha2Code")
    @Expose
    var Alpha2Code : String? = null

    @SerializedName("alpha3Code")
    @Expose
    var Alpha3Code : String? = null

    @SerializedName("altSpellings")
    @Expose
    var AltSpellings : Any? = null //TODO: Create Respose Model for this

    @SerializedName("area")
    @Expose
    var Area : Double? = null

    @SerializedName("borders")
    @Expose
    var Borders : Any? = null //TODO: Create Respose Model for this

    @SerializedName("callingCodes")
    @Expose
    var CallingCodes : Any? = null //TODO: Create Respose Model for this

    @SerializedName("capital")
    @Expose
    var Capital : String? = null

    @SerializedName("cioc")
    @Expose
    var Cioc : String? = null

    @SerializedName("currencies")
    @Expose
    var Currencies : Any? = null //TODO: Create Respose Model for this

    @SerializedName("demonym")
    @Expose
    var Demonym : String? = null

    @SerializedName("flag")
    @Expose
    var FlagURL : String? = null

    @SerializedName("gini")
    @Expose
    var Gini : Double? = null

    @SerializedName("languages")
    @Expose
    var Languages : Any? = null //TODO: Create Respose Model for this

    @SerializedName("latlng")
    @Expose
    var Latlng : Any? = null //TODO: Create Respose Model for this

    @SerializedName("name")
    @Expose
    var Name : String? = null

    @SerializedName("nativeName")
    @Expose
    var NativeName : String? = null

    @SerializedName("numericCode")
    @Expose
    var NumericCode : String? = null

    @SerializedName("population")
    @Expose
    var Population : Int? = null

    @SerializedName("region")
    @Expose
    var Region : String? = null

    @SerializedName("regionalBlocs")
    @Expose
    var RegionalBlocs : Any? = null //TODO: Create Respose Model for this

    @SerializedName("subregion")
    @Expose
    var SubRegion : String? = null

    @SerializedName("timezones")
    @Expose
    var Timezones : Any? = null //TODO: Create Respose Model for this

    @SerializedName("topLevelDomain")
    @Expose
    var TopLevelDomain : Any? = null //TODO: Create Respose Model for this

    @SerializedName("translations")
    @Expose
    var Translations : Any? = null //TODO: Create Respose Model for this
}