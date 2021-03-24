package com.tammamkhalaf.myuaeguide.helperClasses

object SplitText {
    fun splitEqually(text: String, size: Int): List<String> {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        val ret: MutableList<String> = java.util.ArrayList<String>((text.length + size - 1) / size)
        var start = 0
        while (start < text.length) {
            ret.add(text.substring(start, java.lang.Math.min(text.length, start + size)))
            start += size
        }
        return ret
    }
}