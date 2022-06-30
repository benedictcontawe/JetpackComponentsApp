package com.example.jetpackcomponentsapp

import org.apache.commons.lang3.StringUtils
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SampleUnitTest {

    private var number : String? = null

    @Before
    fun before() {
        System.out.println("Initial Testing")
    }

    @Test
    fun null_test() {
        number = null
        System.out.println("null_test() $number")
        Assert.assertEquals(isWholeNumber(), false)
    }

    @Test
    fun blank_test() {
        number = ""
        System.out.println("blank_test() $number")
        Assert.assertEquals(isWholeNumber(), false)
    }

    @Test
    fun whitespace_test() {
        number = " "
        System.out.println("whitespace_test() $number")
        Assert.assertEquals(isWholeNumber(), false)
    }

    @Test
    fun number_whitespace_test() {
        number = "1 "
        System.out.println("number_whitespace_test() $number")
        Assert.assertEquals(isWholeNumber(), false)
    }

    @Test
    fun whitespace_number_test() {
        number = " 1"
        System.out.println("whitespace_number_test() $number")
        Assert.assertEquals(isWholeNumber(), false)
    }

    @Test
    fun number_whitespace_number_test() {
        number = "12 3"
        System.out.println("number_whitespace_number_test() $number")
        Assert.assertEquals(isWholeNumber(), false)
    }

    @Test
    fun alpha_special_characters_test() {
        number = "12-3"
        System.out.println("alpha_special_characters_test() $number")
        Assert.assertEquals(isWholeNumber(), false)
    }

    @Test
    fun number_test() {
        number = "1"
        System.out.println("number_test() $number")
        Assert.assertEquals(isWholeNumber(), true)
    }

    @Test
    fun numbers_test() {
        number = "123"
        System.out.println("numbers_test() $number")
        Assert.assertEquals(isWholeNumber(), true)
    }

    @Test
    fun decimal_numbers_test() {
        number = "12.3"
        System.out.println("decimal_numbers_test() $number")
        Assert.assertEquals(isWholeNumber(), false)
    }

    private fun isWholeNumber() : Boolean {
        return number?.isNotBlank() == true && StringUtils.isNumeric(number)
    }

    private fun isNotWholeNumber() : Boolean {
        return number?.isBlank() == true || !StringUtils.isNumeric(number)
    }

    @After
    fun after() {
        System.out.println("Done Testing")
    }
}