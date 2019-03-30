package com.rahul.hope.models

import com.google.firebase.auth.FirebaseAuth

class Appointment (
    val progress:Long = 0L,
    val name: String? = FirebaseAuth.getInstance().currentUser?.displayName,
    val time: Long = 0L,
    val uid: String? = FirebaseAuth.getInstance().uid
)