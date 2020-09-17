package com.belyakov.vezdecodemusic.presentation.redact

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.belyakov.vezdecodemusic.R
import kotlinx.android.synthetic.main.d_picker.*


class TimecodePickerDialog : DialogFragment() {

    companion object {
        const val TAG = "picker_dialog"
        fun getInstance() = TimecodePickerDialog()
    }

    var onConfirmListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.d_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
    }

    private fun handleView() {
        minutePicker.maxValue = 59
        minutePicker.minValue = 0
        secondsPicker.maxValue = 59
        secondsPicker.minValue = 0
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}