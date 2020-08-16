package com.example.jetpackcomponentsapp

import java.util.regex.Matcher
import java.util.regex.Pattern

object BooleanUtils {
    fun hasSpecialCharacter(value : String) : Boolean {
        val specialCharacterPattern : Pattern
        val specialCharacterMatcher : Matcher
        specialCharacterPattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        specialCharacterMatcher = specialCharacterPattern.matcher(value)

        return specialCharacterMatcher.matches()
    }
}