package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.junit.Test
import org.junit.Assert.*

class NewsgroupIntegrationTest {

    private val HOST = "news.tugraz.at"

    @Test
    fun fillNewsGroupList() {
        var server = NewsgroupServer(0, HOST)
        val con = NewsgroupConnection(server)

        var groups = con.getNewsGroups()
        assertFalse("Server does not contain any newsgroups ", groups.isNullOrEmpty())
    }

}