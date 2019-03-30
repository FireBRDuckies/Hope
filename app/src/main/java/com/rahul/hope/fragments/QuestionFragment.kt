package com.rahul.hope.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.rahul.hope.R
import kotlinx.android.synthetic.main.fragment_question.*

class QuestionFragment : Fragment() {

    var questionKey: String = "question"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        questionStatement.text = this.arguments?.get(questionKey)?.toString()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                valueSelectedText.text = when(progress) {
                    0 -> "never"
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
