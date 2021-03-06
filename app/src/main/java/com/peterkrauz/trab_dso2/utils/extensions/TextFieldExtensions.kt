package com.peterkrauz.trab_dso2.utils.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.textOrBlank() = this.editText?.text.toString()

var TextInputLayout.text: String
    get() = editText?.text.toString()
    set(value) {
        editText?.setText(value)
    }