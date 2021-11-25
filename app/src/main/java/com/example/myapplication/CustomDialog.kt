package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.widget.Button

class CustomDialog(context: Context, activity: MainActivity) {
    private val dlg = Dialog(context)
    private val activity = activity

    fun dialogTwoButton() {
        dlg.setContentView(R.layout.two_button_dialog)
        dlg.setCancelable(false)

        dlg.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dlg.dismiss()
        }

        dlg.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            activity.finish()
        }
        dlg.show()
    }
}