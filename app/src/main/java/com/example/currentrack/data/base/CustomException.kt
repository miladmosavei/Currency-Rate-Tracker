package com.example.currentrack.data.base

import com.example.currentrack.data.enum.Error

/**
 *
 * deal with any kinds of exceptions
 * @property error Error
 * @constructor
 */
data class CustomException(
    val error: Error,
    val errorMessage:String
    ):Exception(errorMessage)