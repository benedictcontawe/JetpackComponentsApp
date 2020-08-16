package com.example.jetpackcomponentsapp

class ContactViewHolderModel {

    val id : Long
    val name : String
    val photo : String
    //val numbers : MutableMap<String, String>

    val isSelf : Boolean
    val viewType : Int

    constructor(header : String, id : Long) {
        this.id = id
        name = header
        photo = ""
        //numbers = mutableMapOf<String, String>("" to "")
        this.isSelf = false
        viewType = ContactAdapter.HeaderView
    }

    constructor(contact : ContactModel, isSelf : Boolean = false) {
        this.id = contact.id
        this.name = contact.name
        this.photo = contact.photo
        //numbers = contact.numbers
        this.isSelf = isSelf
        viewType = ContactAdapter.DefaultView
    }
}
