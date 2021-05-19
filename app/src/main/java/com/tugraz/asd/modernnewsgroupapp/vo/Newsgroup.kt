package com.tugraz.asd.modernnewsgroupapp.vo

import kotlin.text.count as count

data class Newsgroup (var name: String){

    var alias: String? = null
    var parent: String? = null
    var hierarchyLevel: Int? = null
    var subscribed: Boolean = false

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