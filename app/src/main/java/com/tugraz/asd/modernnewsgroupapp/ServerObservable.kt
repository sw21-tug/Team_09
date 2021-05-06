package com.tugraz.asd.modernnewsgroupapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer

open class ServerObservable : ViewModel()
{
    val data = MutableLiveData<NewsgroupController>()

    fun data(item: NewsgroupController) {
        data.value = item
    }
}