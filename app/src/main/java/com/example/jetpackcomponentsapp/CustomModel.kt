package com.example.jetpackcomponentsapp

public data class CustomModel (
        public val id : Int,
        public val name : String = "",
        public val icon : Int? = null,
) {

        companion object {
                private val TAG = CustomModel::class.java.getSimpleName()
        }

        override fun toString(): String {
                return "$TAG($id, $name, $icon)" ?: super.toString()
        }
}
