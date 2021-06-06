package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PostArticleTest {

    private val HOST = "news.tugraz.at"

    @Test
    fun postTestArticle() {
        val server = NewsgroupServer(0, HOST, username = "test@tugrazngtest.local")

        val controller = NewsgroupController()
        controller.addServer(server)
        controller.fetchNewsGroups()
        controller.currentServer = server


        val newsgroups = controller.currentNewsgroups.filter { it.name == "tu-graz.test" }

        assertTrue("Test newsgroup not found", newsgroups.size > 0)

        val newsgroup = newsgroups[0]
        controller.currentNewsgroup = newsgroup

        controller.postArticle("ASD Android App Test", "This is a Test message")
    }
}