package com.example.treechart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class HierarchyFragment: Fragment() {
    private lateinit var linearLayout: LinearLayout
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.v("BK", "onCreate")
        setLinearLayout()
        return linearLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.arrayLiveData.observe(requireActivity()) {
            linearLayout.removeAllViews()
            createTreeChart(it, linearLayout)
        }
    }

    private fun setLinearLayout() {
        linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        linearLayout.setOnClickListener {
            viewModel.setTapped(true)
        }
    }

    /**
     * create Tree Chart with child size and parent view
     * @param children: array of size of child views
     * @param parentView: parent view
     */
    private fun createTreeChart(children: IntArray, parentView: ViewGroup) {
        val midIndex = children.findMidIndex()
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
}