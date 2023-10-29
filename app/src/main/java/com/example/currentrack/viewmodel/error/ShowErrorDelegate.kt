package com.example.currentrack.viewmodel.error

import androidx.lifecycle.LiveData

interface ShowErrorDelegate {
    fun onFailure(throwable: Throwable)

    fun showErrorDialog()

    fun hideErrorDialog()

    fun getErrorDialogState(): LiveData<Boolean>

    fun getErrorMessage(): String

    fun isErrorState(): Boolean

    fun setErrorState(boolean: Boolean)
}