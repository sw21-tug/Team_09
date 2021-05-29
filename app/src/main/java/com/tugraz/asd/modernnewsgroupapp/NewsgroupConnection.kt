package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.apache.commons.net.nntp.Article
import org.apache.commons.net.nntp.NNTPClient
import org.apache.commons.net.nntp.Threader
import java.io.BufferedReader
import java.net.UnknownHostException
import java.nio.charset.Charset
import kotlin.Exception
import kotlin.collections.ArrayList

class NewsgroupConnection (private var server: NewsgroupServer){

    private var article: Article? = null
    private lateinit var resp: Iterable<Article>

    private var client: NNTPClient = NNTPClient()

    private fun ensureConnection() {
        if(!client.isConnected) {
            try {
                client.connect(server.host, server.port)
            } catch (e: Exception) {
                when(e) {
                    is UnknownHostException -> {
                        throw NewsgroupConnectionException("Unknown host while connecting to newsgroup server")
                    }
                    else -> {
                        throw NewsgroupConnectionException("IOException while connecting to newsgroup server " + server.host + ": " + e.message)
                    }
                }
            }
        }
    }

    fun getNewsGroups(): ArrayList<Newsgroup> {
        ensureConnection()
        val response = client.listNewsgroups()
        val groups: ArrayList<Newsgroup> = ArrayList()

        for (group in response) {
            groups.add(Newsgroup(name = group.newsgroup, newsgroupServerId = server.id, firstArticle = group.firstArticleLong, lastArticle = group.lastArticleLong))
        }
        return groups
    }

    fun getArticleHeaders(sg: Newsgroup?): Article? {
        ensureConnection()
        if (sg != null) {
            print("name of ng to select: " + sg.name)
            if(client.selectNewsgroup(sg.name))
                print("Newsgroup selected")
            else
                print("Failed select newsgroup")
        }
        if (sg != null) {
            resp = client.iterateArticleInfo(sg.firstArticle, sg.lastArticle)
            val threader = Threader()
            val graph = threader.thread(resp)
            if (graph != null) {
                article = graph as Article?
            } else {
                article = null
            }
        }
        return article
    }

    fun getArticleBody(articleId: Long) : String {
        return client.retrieveArticleBody(articleId).use(BufferedReader::readText)
    }

    /*
        Custom Exception class for newsgroup connection
     */
    class NewsgroupConnectionException(message:String): Exception(message)

}
