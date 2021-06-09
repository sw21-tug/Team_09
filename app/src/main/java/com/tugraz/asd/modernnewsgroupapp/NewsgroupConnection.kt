package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import java.io.BufferedReader
import org.apache.commons.net.nntp.*
import java.net.UnknownHostException
import kotlin.Exception
import kotlin.collections.ArrayList

class NewsgroupConnection (var server: NewsgroupServer){

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
                        throw NewsgroupConnectionException("IOException while connecting to newsgroup server " + server.host + ": " + e.toString() + ":" + e.message)
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

    fun updateNewsGroup(newsgroup: Newsgroup) {
        ensureConnection()
        val response = client.listNewsgroups()

        for (group in response) {
            if (newsgroup.name == group.newsgroup) {
                newsgroup.firstArticle = group.firstArticleLong
                newsgroup.lastArticle = group.lastArticleLong
            }
            //groups.add(Newsgroup(name = group.newsgroup, newsgroupServerId = server.id, firstArticle = group.firstArticleLong, lastArticle = group.lastArticleLong))
        }
    }

    fun getArticleHeaders(sg: Newsgroup?, retry: Int = 0): Article? {
        ensureConnection()
        if (sg != null) {
            print("name of ng to select: " + sg.name)
            if(client.selectNewsgroup(sg.name))
                print("Newsgroup selected")
            else
                print("Failed select newsgroup")
        }
        if (sg != null) {
            try {
                resp = client.iterateArticleInfo(sg.firstArticle, sg.lastArticle)
            } catch (e: Exception) {
                if (retry < 5) {
                    client.disconnect()
                    client = NNTPClient()
                    client.connect(server.host, server.port)
                    return getArticleHeaders(sg, retry + 1)
                } else  {
                    throw e
                }
            }

            val threader = Threader()
            val graph = threader.thread(resp)
            if (graph != null)
                article = (graph as Article?)!!
            else
                article = null
        }
        return article
    }

    fun getArticleBody(articleId: Long) : String {
        return client.retrieveArticleBody(articleId).use(BufferedReader::readText)
    }

    fun postArticle(newsgroup: Newsgroup, from: String, subject: String, message: String): Boolean {
        ensureConnection()

        if(!client.isAllowedToPost) return false

        client.selectNewsgroup(newsgroup.name)

        val writer = client.postArticle() ?: return false

        val header = SimpleNNTPHeader(from, subject)
        header.addNewsgroup(newsgroup.name)
        writer.write(header.toString())
        writer.write(message)
        writer.close()
        client.completePendingCommand()
        return true
    }

    /*
        Custom Exception class for newsgroup connection
     */
    class NewsgroupConnectionException(message:String): Exception(message)

}
