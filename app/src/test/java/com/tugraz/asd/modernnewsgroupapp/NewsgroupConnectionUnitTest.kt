package com.tugraz.asd.modernnewsgroupapp

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NewsgroupConnectionUnitTest {

    private val host = "news.tugraz.at"

    @Test
    fun establishConnection() {
        var server = NewsgroupServer(host)
        NewsgroupConnection.server = server
        var groups = NewsgroupConnection.getNewsGroups()

        assertTrue("No newsgroups received", groups.size > 0)
    }

    @Test(expected = NewsgroupConnection.NewsgroupConnectionException::class)
    fun establishWithWrongHost() {
        var server = NewsgroupServer("wrong.tugraz.at")
        NewsgroupConnection.server = server
        NewsgroupConnection.getNewsGroups()
    }
}