package com.example.jetpackcomponentsapp

public data class ContactModel (
        val id : Long,
        var name : String,
        var photo : String,
        val numbers : MutableMap<String,String>,
        val emails : MutableMap<String,String>
) {
        companion object {
                private val TAG = ContactModel::class.java.getSimpleName()
                public const val HeaderView = 0 //cell_contact_header
                public const val CellView = 1 //cell_contact_default
        }
}