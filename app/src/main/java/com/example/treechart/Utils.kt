package com.example.treechart

import android.graphics.Color
import java.util.Random

/**
 * get random color
 * @return Integer representation of color
 */
fun getRandomColor(): Int {
    val rnd = Random()
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}

/**
 * find index when sum reaches around the half of sum of entire array.
 * @param array input array of integers
 * @return index
 */
fun IntArray.findMidIndex(): Int {
    if (size > 2) {
        val halfOfTotal = sum() / 2
        var sum = 0
        forEachIndexed { index, i ->
            sum += i
            if (sum > halfOfTotal) {
                return index - 1
            }
        }
    }
    return 0
}