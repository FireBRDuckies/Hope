package com.rahul.hope

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahul.hope.activities.ChatActivity
import com.rahul.hope.adapters.ChatRoomAdapter
import com.rahul.hope.listeners.LaunchBottomSheetListener
import com.rahul.hope.viewmodels.ChatRoomViewModel
import com.rahul.hope.viewmodels.RoomViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*


class HomeActivityFragment : Fragment() {
    private var launcherBottomSheetListener: LaunchBottomSheetListener? = null
    private lateinit var viewModelFactory: RoomViewModelFactory
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
        call911Button.setOnClickListener { launcherBottomSheetListener?.launchBottomSheet(1) }
        addChatRoom.setOnClickListener(View.OnClickListener { showDialog(activity!!) })

//        call911Button.setOnClickListener { launcherBottomSheetListener?.launchBottomSheet(1) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
            Toast.makeText(context, "Depression", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        val mDialogAnxiety = dialog.findViewById<Button>(R.id.socialAnxietyButton)
        mDialogAnxiety.setOnClickListener {
            Toast.makeText(context, "Social Anxiety", Toast.LENGTH_SHORT).show()
            dialog.cancel()
        }

        dialog.show()
    }
}
