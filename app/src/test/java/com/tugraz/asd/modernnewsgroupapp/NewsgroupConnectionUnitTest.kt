package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NewsgroupConnectionUnitTest {

    private val HOST = "news.tugraz.at"

    @Test
    fun establishConnection() {
        val server = NewsgroupServer(0, HOST)
        val con = NewsgroupConnection(server)
        val groups = con.getNewsGroups()

        assertTrue("No newsgroups received", groups.size > 0)
    }

    @Test(expected = NewsgroupConnection.NewsgroupConnectionException::class)
    fun establishWithWrongHost() {
        val server = NewsgroupServer(0, "wrong.tugraz.at")
        val con = NewsgroupConnection(server)
        val groups = con.getNewsGroups()
    }

    @Test(expected = NewsgroupConnection.NewsgroupConnectionException::class)
    fun establishWithWrongPort() {
        val server = NewsgroupServer(0, HOST, 0)
        val con = NewsgroupConnection(server)
        con.getNewsGroups()
    }
}