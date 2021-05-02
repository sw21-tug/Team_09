package com.tugraz.asd.modernnewsgroupapp.vo

data class Newsgroup (var name: String){
    var parent: String? = null
    var subscribed: Boolean = false

    fun hasSubgroup(): Boolean {
        return this.parent != null
    }

    // if newsgroup has at least two dots in its name -> indicates subgroup
    fun isSubgroup(): Boolean {
        //println("Subgroup: $name")
        return name.count{ character -> character == '.' } >= 2
    }

    fun setParentNewsgroup() {
        var splitted: List<String> = name.split('.')
        parent =  splitted[0] + "." + splitted[1]
    }
}