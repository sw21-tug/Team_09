package com.tugraz.asd.modernnewsgroupapp.vo

import kotlin.text.count as count

data class Newsgroup (var name: String){
    var parent: String? = null
    var hierarchyLevel: Int? = null
    var subscribed: Boolean = false

    fun hasSubgroup(): Boolean {
        return parent != null
    }

    fun subscribe() {
        subscribed = true
    }

    fun unsubscribe() {
        subscribed = false
    }

    // if newsgroup has at least a dot in its name -> indicates subgroup
    fun isSubgroup(): Boolean {
        return name.filter { it == '.' }.count() >= 1
    }

    fun setParentNewsgroup() {
        parent = name.substringBeforeLast(".")
    }

    fun setHierarchyLevel() {
        hierarchyLevel = name.filter { it == '.' }.count()
    }
}