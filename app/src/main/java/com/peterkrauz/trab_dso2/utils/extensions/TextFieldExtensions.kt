package com.peterkrauz.trab_dso2.utils.extensions

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.textOrBlank() = this.editText?.text.toString()

fun TextInputLayout.attachTextWatcher(textWatcher: TextWatcher) {
    this.editText?.addTextChangedListener(textWatcher)
}

class DateTextWatcher(
    private val textInput: TextInputLayout
) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        s?.let {
            var newDateText = ""
            if (it.length == 10) {
                with(it.toString()) {
                    newDateText = "${day()}/${month()}/${year()}"
                }
            }
            textInput.editText?.setText(newDateText)
        }
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}