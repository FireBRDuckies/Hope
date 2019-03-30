package com.rahul.hope

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahul.hope.activities.QuestionnaireDepressionActivity
import com.rahul.hope.listeners.LaunchBottomSheetListener
import kotlinx.android.synthetic.main.fragment_select_track.*

class SelectTrackFragment : Fragment() {

    private var launchBottomSheetListener: LaunchBottomSheetListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_track, container, false)
    }

    override fun onAttach(context: Context) {
        launchBottomSheetListener = context as LaunchBottomSheetListener
        super.onAttach(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        depressionButton.setOnClickListener { val intent = Intent(context,QuestionnaireDepressionActivity::class.java)
            startActivity(intent) }
    }
}
