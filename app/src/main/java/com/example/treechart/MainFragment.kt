package com.example.treechart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout.LayoutParams
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

private const val CONSTRAINT_LAYOUT_ID = 1000
private const val HIERARCHY_BUTTON_ID = 1001
private const val PLAIN_BUTTON_ID = 1002
private const val VERTICAL_CENTER_GUIDELINE_ID = 1003
class MainFragment: Fragment() {
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var hierarchyButton: Button
    private lateinit var plainButton: Button
    private lateinit var guideline: Guideline
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        constraintLayout = ConstraintLayout(requireContext())
        constraintLayout.id = CONSTRAINT_LAYOUT_ID
        return constraintLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setConstraints()
        setViewModel()
    }

    private fun setViews() {
        hierarchyButton = Button(requireContext())
        hierarchyButton.layoutParams = ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        hierarchyButton.id = HIERARCHY_BUTTON_ID
        hierarchyButton.text = "Hierarchy"
        hierarchyButton.setOnClickListener {
            viewModel.setFragment(HIERARCHY_FRAGMENT_TAG)
        }
        plainButton = Button(requireContext())
        plainButton.layoutParams = ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        plainButton.id = PLAIN_BUTTON_ID
        plainButton.text = "Plain"
        plainButton.setOnClickListener {
            viewModel.setFragment(PLAIN_FRAGMENT_TAG)
        }
        guideline = Guideline(requireContext())
        guideline.id = VERTICAL_CENTER_GUIDELINE_ID
        val layoutParams = ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.orientation = ConstraintLayout.LayoutParams.HORIZONTAL
        layoutParams.guidePercent = 0.5f
        guideline.layoutParams = layoutParams

        constraintLayout.addView(hierarchyButton)
        constraintLayout.addView(plainButton)
        constraintLayout.addView(guideline)
    }

    private fun setConstraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        constraintSet.connect(
            HIERARCHY_BUTTON_ID,
            ConstraintSet.LEFT,
            CONSTRAINT_LAYOUT_ID,
            ConstraintSet.LEFT,
            0
        )
        constraintSet.connect(
            HIERARCHY_BUTTON_ID,
            ConstraintSet.RIGHT,
            CONSTRAINT_LAYOUT_ID,
            ConstraintSet.RIGHT,
            0
        )
        constraintSet.connect(
            HIERARCHY_BUTTON_ID,
            ConstraintSet.BOTTOM,
            VERTICAL_CENTER_GUIDELINE_ID,
            ConstraintSet.TOP,
            40
        )

        constraintSet.connect(
            PLAIN_BUTTON_ID,
            ConstraintSet.LEFT,
            CONSTRAINT_LAYOUT_ID,
            ConstraintSet.LEFT,
            0
        )
        constraintSet.connect(
            PLAIN_BUTTON_ID,
            ConstraintSet.RIGHT,
            CONSTRAINT_LAYOUT_ID,
            ConstraintSet.RIGHT,
            0
        )
        constraintSet.connect(
            PLAIN_BUTTON_ID,
            ConstraintSet.TOP,
            VERTICAL_CENTER_GUIDELINE_ID,
            ConstraintSet.BOTTOM,
            40
        )

        constraintSet.applyTo(constraintLayout)
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
}