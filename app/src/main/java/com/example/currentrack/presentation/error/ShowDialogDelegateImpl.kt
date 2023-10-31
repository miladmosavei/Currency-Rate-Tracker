package com.example.currentrack.presentation.error

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ShowDialogDelegateImpl : ShowErrorDelegate {
    /**
     * To observe dialog state on the UI, we need a liveData
     */
    private val _showErrorDialogLiveData = MutableLiveData<Boolean>()
    private val showErrorDialogLiveData: LiveData<Boolean> = _showErrorDialogLiveData
    private var isErrorState: Boolean = false

    /**
     * A liveData can be observed by the UI if more details are needed about an exception.
     */
    private val _errorLiveData = MutableLiveData<Throwable>()
    override fun onFailure(throwable: Throwable) {
        setErrorState(true)
        showErrorDialog()
        _errorLiveData.postValue(throwable)
    }

    override fun showErrorDialog() {
        _showErrorDialogLiveData.postValue(true)
    }

    override fun hideErrorDialog() {
        _showErrorDialogLiveData.postValue(false)
    }

    override fun getErrorDialogState(): LiveData<Boolean> {
        return showErrorDialogLiveData
    }

    override fun getErrorMessage(): String {
        return _errorLiveData.value?.message ?: ""
    }

    override fun isErrorState(): Boolean {
        return isErrorState
    }

    override fun setErrorState(boolean: Boolean) {
        isErrorState = boolean
    }

}