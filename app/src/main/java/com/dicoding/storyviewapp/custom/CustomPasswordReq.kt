package com.dicoding.storyviewapp.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.storyviewapp.R

class CustomPasswordReq: AppCompatEditText {

    val passwordErrorMessage = resources.getString(R.string.password_error)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                error = if (s.toString().length < 8) {
                            passwordErrorMessage
                        } else {
                            null
                        }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }
}