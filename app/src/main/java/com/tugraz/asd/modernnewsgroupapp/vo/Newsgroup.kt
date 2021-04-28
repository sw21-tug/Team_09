package com.tugraz.asd.modernnewsgroupapp.vo

data class Newsgroup (var name: String){
    var parent: Newsgroup? = null
    var subscribed: Boolean = false
    var name_ : String = name

    fun hasSubgroup(): Boolean {
        return this.parent != null
    }
}