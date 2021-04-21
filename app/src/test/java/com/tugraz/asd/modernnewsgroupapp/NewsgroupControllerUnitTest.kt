package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NewsgroupControllerUnitTest {

    private val HOST = "news.tugraz.at"

    @Test
    fun fetchServers() {
        val controller = NewsgroupController()
        val server = NewsgroupServer(HOST)

        controller.addServer(server)
        controller.fetchNewsGroups()

        assertTrue("No groups fetched", server.newsGroups!!.isNotEmpty())
    }

    @Test
    fun fetchSingleServer() {
        val controller = NewsgroupController()
        val server = NewsgroupServer(HOST)

        controller.addServer(server)
        controller.fetchNewsGroups(server)

        assertTrue("No groups fetched", server.newsGroups!!.isNotEmpty())
    }


}