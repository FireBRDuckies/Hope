package com.rahul.hope.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rahul.hope.R
import kotlinx.android.synthetic.main.fragment_question.*

class QuestionFragment : Fragment() {

    var questionKey: String = "question"
    private lateinit var responseSeekBar: SeekBar
    private lateinit var selectedValueText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        responseSeekBar = activity!!.findViewById(R.id.responseSeekBar)
        selectedValueText = activity!!.findViewById(R.id.selectedValueText)
        questionStatement.text = this.arguments?.get(questionKey)?.toString()

        responseSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedValueText.text = when (progress) {
                    0 -> "Never"
                    1 -> "Occasionally"
                    2 -> "Half of the time"
                    3 -> "Most of the time"
                    4 -> "All of the time"
                    else -> "Never"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }
}
