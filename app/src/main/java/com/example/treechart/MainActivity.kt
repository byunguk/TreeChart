package com.example.treechart

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val FRAME_LAYOUT_ID = 1000
class MainActivity : AppCompatActivity() {
    // test case 1
    private val childViews1 = intArrayOf(3, 2, 1, 1, 1, 1, 1)
    // test case 2
    private val childViews2 = intArrayOf(4, 3, 2, 2, 1, 1, 1, 1, 1)
    // test case 3
    private val childViews3 = intArrayOf(1, 1, 1, 1, 1, 2, 2, 3, 4)
    // test case 4
    private val childViews4 = intArrayOf(1, 2, 3, 4, 3, 2, 1, 2, 3, 4, 2, 1, 1)

    private val array = arrayOf(childViews1, childViews2, childViews3, childViews4)
    private var selectedChildViewIndex = 0

    private lateinit var frameLayout: FrameLayout
    private lateinit var viewModel: MainViewModel
    private val hierarchyFragment = HierarchyFragment()
    private val plainFragment = PlainFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setFragments()
        setViewModel()
    }

    private fun setFragments() {
        frameLayout = FrameLayout(this)
        frameLayout.id = FRAME_LAYOUT_ID
        setContentView(frameLayout)

        val fragment = supportFragmentManager.findFragmentByTag(MAIN_FRAGMENT_TAG)
        if (fragment == null) {
            val fragTransaction = supportFragmentManager.beginTransaction()
            val mainFragment = MainFragment()
            fragTransaction.add(FRAME_LAYOUT_ID, mainFragment, MAIN_FRAGMENT_TAG)
            fragTransaction.commit()
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.selectedFragmentLiveData.observe(this) {
            when (it) {
                HIERARCHY_FRAGMENT_TAG -> {
                    supportFragmentManager.beginTransaction()
                        .replace(FRAME_LAYOUT_ID, hierarchyFragment, HIERARCHY_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit()
                    viewModel.setArray(array[0])
                }
                PLAIN_FRAGMENT_TAG -> {
                    supportFragmentManager.beginTransaction()
                        .replace(FRAME_LAYOUT_ID, plainFragment, PLAIN_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit()
                    viewModel.setArray(array[0])
                }
                else -> Unit
            }
        }

        viewModel.tappedLiveData.observe(this) {
            selectedChildViewIndex++
            if (selectedChildViewIndex >= array.size) {
                selectedChildViewIndex = 0
            }
            viewModel.setArray(array[selectedChildViewIndex])
        }
    }
}