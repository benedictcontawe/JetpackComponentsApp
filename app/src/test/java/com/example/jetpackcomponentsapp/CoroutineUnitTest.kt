package com.example.jetpackcomponentsapp

import org.junit.Assert
import org.junit.Test

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.coroutineScope
import kotlin.concurrent.thread

class CoroutineUnitTest {

    @Test
    public fun firstCoroutine() {
        System.out.println("First coroutine")
        GlobalScope.launch {
            delay(1000) //Non-blocking Function
            System.out.println("Current thread (${Thread.currentThread().name})")
            System.out.println("Task 1 ")
            System.out.println("---------------------------------------------")
        }

        System.out.println("Task 2")
        System.out.println("Current thread (${Thread.currentThread().name})")
        System.out.println("---------------------------------------------")
        Thread.sleep(2000) //Blocking Function

        //Assert.assertEquals(true, true)
        assert(true)
    }

    @Test
    public fun withThread() {
        System.out.println("Replace coroutine with thread")
        thread {
            Thread.sleep(1000) //Blocking Function
            System.out.println("Current thread (${Thread.currentThread().name})")
            System.out.println("Task 1")
            System.out.println("---------------------------------------------")
        }

        System.out.println("Task 2")
        System.out.println("Current thread (${Thread.currentThread().name})")
        System.out.println("---------------------------------------------")
        Thread.sleep(2000) //Blocking Function

        assert(true)
    }

    @Test
    public fun removeBlockingCode() {
        System.out.println("Remove blocking code")
        GlobalScope.launch {
            delay(1000) //Non-blocking Function
            System.out.println("Current thread (${Thread.currentThread().name})")
            System.out.println("Task 1 ")
            System.out.println("---------------------------------------------")
        }

        runBlocking {
            System.out.println("Task 2")
            System.out.println("Current thread (${Thread.currentThread().name})")
            System.out.println("---------------------------------------------")
            delay(2000) //Non-blocking Function
        }


        assert(true)
    }

    @Test
    public fun removeExplicitWaitJob() : Unit = runBlocking {
        System.out.println("Remove explicit wait using job")
        val fisrtJob : Job = launch {
            delay(1000) //Non-blocking Function
            System.out.println("Current thread (${Thread.currentThread().name})")
            System.out.println("Task 1 ")
            System.out.println("---------------------------------------------")
        }

        System.out.println("Task 2")
        System.out.println("Current thread (${Thread.currentThread().name})")
        System.out.println("---------------------------------------------")
        fisrtJob.join()

        assert(true)
    }

    @Test
    public fun removeExplicitWaitJobSimplified() : Unit = runBlocking {
        System.out.println("Remove explicit wait using job Simplified")
        launch {
            delay(1000) //Non-blocking Function
            System.out.println("Current thread (${Thread.currentThread().name})")
            System.out.println("Task 1 ")
            System.out.println("---------------------------------------------")
        }

        System.out.println("Task 2")
        System.out.println("Current thread (${Thread.currentThread().name})")
        System.out.println("---------------------------------------------")

        assert(true)
    }

    @Test
    public fun scopeBuilder() : Unit = runBlocking {
        System.out.println("Coroutine Scope Builder")

        //runBlocking - blocks the thread
        //coroutineScope - Non blocking thread

        launch {
            delay(100) //Non-blocking Function
            System.out.println("Current thread (${Thread.currentThread().name})")
            System.out.println("Inside Run Blocking Scope")
            System.out.println("---------------------------------------------")
        }

        coroutineScope {
            launch {
                delay(300)
                System.out.println("Current thread (${Thread.currentThread().name})")
                System.out.println("Nested launch")
                System.out.println("---------------------------------------------")
            }
            delay(200)
            System.out.println("Current thread (${Thread.currentThread().name})")
            System.out.println("Inside Coroutine Scope")
            System.out.println("---------------------------------------------")
        }

        System.out.println("Current thread (${Thread.currentThread().name})")
        System.out.println("Run Blocking scope over")
        System.out.println("---------------------------------------------")

        assert(true)
    }

    @Test
    public fun loopCoroutine() : Unit = runBlocking {
        repeat(100) {
            delay(1)
            println("Light weight thread")
        }

        assert(true)
    }

    @Test
    public fun loopThread() : Unit = runBlocking {
        repeat(100) {
            Thread.sleep(1)
            println("Light weight thread")
        }

        assert(true)
    }

    @Test
    public fun random_number_until_one() {
        println("random_number_until_one()")
        val randomInteger = (1..10000).shuffled().first()
        println("randomInteger - $randomInteger")
        when(randomInteger) {
            1 -> {
                println("randomInteger is now one")
                assert(true)
            }
            else -> {
                println("recall")
                random_number_until_one()
            }
        }
    }

    @Test
    public fun check_string() {
        println("check_string()")
        val x = "+00"
        when {
            x == "+00" -> {
                println("+00")
                assert(true)
            }
            else -> {
                println("else")
                assert(false)
            }
        }
    }
}