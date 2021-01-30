package com.tammamkhalaf.shared

class Greeting {
    fun greeting(): String {
        return "Hello, Tammam ${Platform().platform}!"
    }
}