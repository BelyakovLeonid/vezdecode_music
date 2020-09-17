package com.belyakov.vezdecodemusic.presentation.redact.views

import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.data.models.Timecode
import com.belyakov.vezdecodemusic.presentation.redact.TimecodePickerDialog
import com.belyakov.vezdecodemusic.utils.toTimeString
import kotlinx.android.synthetic.main.v_timecode.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class TimeCodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var fragmentManager: FragmentManager? = null
    var onCloseListener: ((View) -> Unit)? = null
    private var selectedTime: Long = 0

    init {
        View.inflate(context, R.layout.v_timecode, this)
        closeButton.setOnClickListener {
            onCloseListener?.invoke(this)
        }
        timeButton.setOnClickListener {
            openTimeDialog()

        }
    }

    private fun openTimeDialog() {
        fragmentManager?.let {
            TimecodePickerDialog.getInstance().show(it, TimecodePickerDialog.TAG)
        }
//
//        Calendar.getInstance().let {
//            val dateListener = TimePickerDialog.OnTimeSetListener { _, h, m ->
//                it.set(Calendar.HOUR_OF_DAY, h)
//                it.set(Calendar.MINUTE, m)
//                selectedTime =
//                    TimeUnit.MINUTES.toMillis(m.toLong()) + TimeUnit.SECONDS.toMillis(h.toLong()) //todo плохо
//                timeButton.text = it.toTimeString()
//            }
//
//            TimePickerDialog(
//                context,
//                dateListener,
//                it.get(Calendar.HOUR_OF_DAY),
//                it.get(Calendar.MINUTE),
//                true
//            ).apply {
//                window?.setFlags(
//                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                )
//                show()
//            }
//        }
    }

    fun setTimecodeName(name: String) {
        editName.setText(name)
    }

    fun getTimecode(): Timecode {
        return Timecode(editName.text.toString(), selectedTime)
    }
}
