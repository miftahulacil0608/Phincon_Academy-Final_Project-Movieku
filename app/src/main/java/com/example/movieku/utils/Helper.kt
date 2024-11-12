package com.example.movieku.utils

import android.content.Context
import android.util.Patterns
import androidx.core.content.ContextCompat
import androidx.core.util.PatternsCompat
import com.google.android.material.textfield.TextInputLayout

object Helper {
    fun emailValidator(email: String): Boolean {
        return email.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun passwordValidator(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 6
    }

    fun fullNameValidator(fullName: String): Boolean {
        return fullName.isNotEmpty() && fullName.length >= 3
    }

    fun updateInputLayout(textInputLayout:TextInputLayout, isNotValid:Boolean,errorMessage:String, context: Context){
        textInputLayout.isErrorEnabled = isNotValid
        textInputLayout.error = if (isNotValid) errorMessage else null
    }

}