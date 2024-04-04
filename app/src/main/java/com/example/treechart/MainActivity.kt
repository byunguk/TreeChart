package com.example.treechart

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.util.Random


class MainActivity : AppCompatActivity() {
    private lateinit var rootLayout: LinearLayout
    // test case 1
    private val childViews1 = intArrayOf(3, 2, 1, 1, 1, 1, 1)
    // test case 2
    private val childViews2 = intArrayOf(4, 3, 2, 2, 1, 1, 1, 1, 1)
    // test case 3
    private val childViews3 = intArrayOf(1, 1, 1, 1, 1, 2, 2, 3, 4)
    // test case 4
    private val childViews4 = intArrayOf(1, 2, 3, 4, 3, 2, 1, 2, 3, 4, 2, 1, 1)

    private val array = arrayOf(childViews1, childViews2, childViews3, childViews4)
    var selectedChildView = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViews()
        createTreeChart(array[selectedChildView], rootLayout)
    }

    private fun setViews() {
        rootLayout = LinearLayout(this)
        rootLayout.orientation = LinearLayout.HORIZONTAL
        rootLayout.setOnClickListener {
            selectedChildView++
            if (selectedChildView >= array.size) {
                selectedChildView = 0
            }
            rootLayout.removeAllViews()
            createTreeChart(array[selectedChildView], rootLayout)
        }
        setContentView(rootLayout)
    }

    /**
     * create Tree Chart with child size and parent view
     * @param children: array of size of child views
     * @param parentView: parent view
     */
    private fun createTreeChart(children: IntArray, parentView: ViewGroup) {
        val midIndex = findMidIndex(children)
        val firstArray = children.sliceArray(0..midIndex)
        val sumOfFirstArray = firstArray.sum()
        val secondArray = children.sliceArray(midIndex+1..<children.size)
        val sumOfSecondArray = secondArray.sum()
        val orientation = if ((parentView as? LinearLayout)?.orientation == LinearLayout.VERTICAL) {
            LinearLayout.HORIZONTAL
        } else {
            LinearLayout.VERTICAL
        }

        val firstView = LinearLayout(parentView.context)
        firstView.setBackgroundColor(getRandomColor())
        firstView.orientation = orientation
        if (orientation == LinearLayout.VERTICAL) {
            firstView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                sumOfFirstArray.toFloat()
            )
        } else {
            firstView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                sumOfFirstArray.toFloat()
            )
        }
        parentView.addView(firstView)
        if (firstArray.size > 1) {
            createTreeChart(firstArray, firstView)
        }

        val secondView = LinearLayout(parentView.context)
        secondView.setBackgroundColor(getRandomColor())
        secondView.orientation = orientation
        if (orientation == LinearLayout.VERTICAL) {
            secondView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                sumOfSecondArray.toFloat()
            )
        } else {
            secondView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                sumOfSecondArray.toFloat()
            )
        }
        parentView.addView(secondView)
        if (secondArray.size > 1) {
            createTreeChart(secondArray, secondView)
        }
    }

    /**
     * get random color
     * @return Integer representation of color
     */
    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    /**
     * find index when sum reaches around the half of sum of entire array.
     * @param array input array of integers
     * @return index
     */
    private fun findMidIndex(array: IntArray): Int {
        if (array.size > 2) {
            val halfOfTotal = array.sum() / 2
            var sum = 0
            array.forEachIndexed { index, i ->
                sum += i
                if (sum > halfOfTotal) {
                    return index - 1
                }
            }
        }
        return 0
    }
}