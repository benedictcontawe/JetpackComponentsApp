package com.example.jetpackcomponentsapp

data class ContactModel (
        val id : Long,
        var name : String,
        var photo : String,
        val numbers : MutableMap<String,String>,
        val emails : MutableMap<String,String>
)