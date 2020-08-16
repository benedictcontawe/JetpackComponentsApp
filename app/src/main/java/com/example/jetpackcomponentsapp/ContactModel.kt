package com.example.jetpackcomponentsapp

data class ContactModel (
        val id : Long,
        var name : String,
        var photo : String,
        var numbers : MutableMap<String,String>,
        var emails : MutableMap<String,String>
)