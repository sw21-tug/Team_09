package com.tugraz.asd.modernnewsgroupapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ServerObservable : ViewModel()
{
    val controller = MutableLiveData<NewsgroupController>()

    fun data(item: NewsgroupController) {
        controller.value = item
    }
}