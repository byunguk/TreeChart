package com.example.treechart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class PlainFragment: Fragment() {
    private lateinit var frameLayout: FrameLayout
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setFragmentLayout()
        return frameLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
    }

    private fun setFragmentLayout() {
        frameLayout = FrameLayout(requireContext())
        frameLayout.setOnClickListener {
            viewModel.setTapped(true)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.arrayLiveData.observe(requireActivity()) {
            frameLayout.post {
                createTreeChart(
                    it,
                    frameLayout.left,
                    frameLayout.top,
                    frameLayout.width,
                    frameLayout.height,
                    LinearLayout.HORIZONTAL
                )
            }
        }
    }

    private fun createTreeChart(children: IntArray, x: Int, y: Int, width: Int, height: Int, orientation: Int) {
        val total = children.sum()
        val midIndex = children.findMidIndex()
        val firstArray = children.sliceArray(0..midIndex)
        val sumOfFirstArray = firstArray.sum()
        val secondArray = children.sliceArray(midIndex+1..<children.size)

        val firstView: FrameLayout
        val secondView: FrameLayout
        val firstX: Int
        val secondX: Int
        val firstY: Int
        val secondY: Int
        val firstWidth: Int
        val secondWidth: Int
        val firstHeight: Int
        val secondHeight: Int
        if (orientation == LinearLayout.VERTICAL) {
            firstView = FrameLayout(requireContext())
            firstX = x
            firstY = y
            firstWidth = width
            firstHeight = Math.round(height.toFloat() / total * sumOfFirstArray)
            firstView.setBackgroundColor(getRandomColor())
            firstView.x = firstX.toFloat()
            firstView.y = firstY.toFloat()
            firstView.layoutParams = FrameLayout.LayoutParams(firstWidth, firstHeight)

            secondX = x
            secondY = y + firstHeight
            secondWidth = width
            secondHeight = height - firstHeight
            secondView = FrameLayout(requireContext())
            secondView.setBackgroundColor(getRandomColor())
            secondView.x = secondX.toFloat()
            secondView.y = secondY.toFloat()
            secondView.layoutParams = FrameLayout.LayoutParams(secondWidth, secondHeight)
        } else {
            firstView = FrameLayout(requireContext())
            firstX = x
            firstY = y
            firstWidth = Math.round(width.toFloat() / total * sumOfFirstArray)
            firstHeight = height
            firstView.setBackgroundColor(getRandomColor())
            firstView.x = firstX.toFloat()
            firstView.y = firstY.toFloat()
            firstView.layoutParams = FrameLayout.LayoutParams(firstWidth, firstHeight)

            secondX = x + firstWidth
            secondY = y
            secondWidth = width - firstWidth
            secondHeight = height
            secondView = FrameLayout(requireContext())
            secondView.setBackgroundColor(getRandomColor())
            secondView.x = secondX.toFloat()
            secondView.y = secondY.toFloat()
            secondView.layoutParams = FrameLayout.LayoutParams(secondWidth, secondHeight)
        }
        frameLayout.addView(firstView)
        frameLayout.addView(secondView)
        val o = if (orientation == LinearLayout.VERTICAL) {
            LinearLayout.HORIZONTAL
        } else {
            LinearLayout.VERTICAL
        }

        if (firstArray.size > 1) {
            firstView.post {
                createTreeChart(firstArray, firstX, firstY, firstWidth, firstHeight, o)
            }
        }
        if (secondArray.size > 1) {
            secondView.post {
                createTreeChart(secondArray, secondX, secondY, secondWidth, secondHeight, o)
            }
        }
    }
}