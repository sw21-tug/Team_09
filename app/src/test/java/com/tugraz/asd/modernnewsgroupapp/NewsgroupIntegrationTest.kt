package com.tugraz.asd.modernnewsgroupapp

import org.junit.Test
import org.junit.Assert.*

class NewsgroupIntegrationTest {

    private val host = "news.tugraz.at"

    @Test
    fun fillNewsGroupList() {
        var server = NewsgroupServer(host)
        NewsgroupConnection.server = server
        var groups = NewsgroupConnection.getNewsGroups()
        assertFalse("Server does not contain any newsgroups ", server.newsGroups.isNullOrEmpty())
    }

}