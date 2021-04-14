package com.tugraz.asd.modernnewsgroupapp


class NewsgroupServer(var host: String = "", var port: Int = 119) {
    var alias: String = ""
    var newsGroups: List<Newsgroup>? = null
}