package com.rahul.hope

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahul.hope.activities.ChatActivity
import com.rahul.hope.adapters.ChatRoomAdapter
import com.rahul.hope.data.DataRepository
import com.rahul.hope.data.network.ApiService
import com.rahul.hope.listeners.LaunchBottomSheetListener
import com.rahul.hope.viewmodels.ChatRoomViewModel
import com.rahul.hope.viewmodels.RoomViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

const val SPEECH_REQUEST_CODE = 0
class HomeActivityFragment : Fragment() {

    private var launcherBottomSheetListener: LaunchBottomSheetListener? = null
    private lateinit var viewModelFactory: RoomViewModelFactory
    private lateinit var roomViewModelFactory: DataRepository
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatRoomViewModel::class.java)
        val adapterChat = ChatRoomAdapter(activity!!) {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra("name", it.chatRoomName)
            startActivity(intent)
        }
        chatRoomsRv.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = adapterChat
        }
        viewModel.allChatRooms.observe(this, Observer {
            it?.let { allChatRooms ->
                adapterChat.swapList(allChatRooms)
            }
        })
        addChatRoom.setOnClickListener(View.OnClickListener { showDialog(activity!!) })
        dayStatus.setOnClickListener(View.OnClickListener { showDayStatusDialog(activity!!) })

//        call911Button.setOnClickListener { launcherBottomSheetListener?.launchBottomSheet(1) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        roomViewModelFactory = (activity?.application as HopeApplication).applicationComponent.getRepository()
        apiService = (activity?.application as HopeApplication).applicationComponent.getApiService()
        launcherBottomSheetListener = context as LaunchBottomSheetListener
        viewModelFactory = (context.applicationContext as HopeApplication).applicationComponent.getViewModelFactory()
    }

    private fun showDialog(activity: Activity) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_track)
        dialog.getWindow().setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val mDialogDepression = dialog.findViewById<Button>(R.id.depressionButton)
        mDialogDepression.setOnClickListener {
            dialog.dismiss()
            launcherBottomSheetListener?.launchBottomSheet(6)
        }

        val mDialogAnxiety = dialog.findViewById<Button>(R.id.socialAnxietyButton)
        mDialogAnxiety.setOnClickListener {
            dialog.cancel()
            launcherBottomSheetListener?.launchBottomSheet(7)
        }
        dialog.show()
    }

    private fun showDayStatusDialog(activity: Activity) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_day_status)
        dialog.getWindow().setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        val submitButton = dialog.findViewById<ImageButton>(R.id.submitButton)
        submitButton.setOnClickListener {
            //val text = queryTv.text.toString()

        }
        dialog.show()
    }


    // Create an intent that can start the Speech Recognizer activity

}
