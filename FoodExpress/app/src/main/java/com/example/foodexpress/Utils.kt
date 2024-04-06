package com.example.foodexpress

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.foodexpress.databinding.ProgressDialogBinding
import com.google.firebase.auth.FirebaseAuth

object Utils {

    private var dialog: AlertDialog?= null
    fun showDialog(context: Context, message : String){
        val progress = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        progress.tvmessage.text = message
        dialog = AlertDialog.Builder(context).setView(progress.root).setCancelable(false).create()
        dialog!!.show()

    }
    fun hideDialog() {
        dialog?.dismiss()
    }
    fun getCurrentUserid(): String? {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid
    }

    fun getRandomId(): String {
        val length = 10
        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { charPool.random() }
            .joinToString("")
    }

}