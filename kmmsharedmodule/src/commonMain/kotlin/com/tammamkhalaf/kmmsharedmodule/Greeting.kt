package com.tammamkhalaf.kmmsharedmodule

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}