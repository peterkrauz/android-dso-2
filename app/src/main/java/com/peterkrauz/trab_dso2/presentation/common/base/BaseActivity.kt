package com.peterkrauz.trab_dso2.presentation.common.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}