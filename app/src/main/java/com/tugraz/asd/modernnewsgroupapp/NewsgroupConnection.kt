package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.apache.commons.net.nntp.Article
import org.apache.commons.net.nntp.NNTPClient
import org.apache.commons.net.nntp.Threadable
import org.apache.commons.net.nntp.Threader
import java.net.UnknownHostException
import java.util.*
import kotlin.Exception
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class NewsgroupConnection (var server: NewsgroupServer){
    private lateinit var resp: Iterable<Article>
    private lateinit var article: Article
    private  var client: NNTPClient = NNTPClient()

    fun ensureConnection() {
        if(!client.isConnected) {
            try {
                client.connect(server.host, server.port)
            } catch (e: Exception) {
                when(e) {
                    is UnknownHostException -> {
                        throw NewsgroupConnectionException("Unknown host while connecting to newsgroup server")
                    }
                    else -> {
                        throw NewsgroupConnectionException("IOException while connecting to newsgroup server: " + e.message)
                    }
                }
            }
        }
    }

    fun getNewsGroups(): ArrayList<Newsgroup> {
        ensureConnection()
        var response = client.listNewsgroups()
        var groups: ArrayList<Newsgroup> = ArrayList()

        for (group in response) {
            var ng = Newsgroup(group.newsgroup)
            ng.firstArticle = group.firstArticleLong
            ng.lastArticle = group.lastArticleLong
            groups.add(ng)
        }
        return groups
    }

    fun getArticleHeaders(sg: Newsgroup?): Article{
        ensureConnection()
        if (sg != null) {
            print("name of ng to select: " + sg.name)
            if(client.selectNewsgroup(sg.name))
                print("Newsgroup selected")
            else
                print("Failed select newsgroup")
        }
        //var response = client.listNewsgroups()
        if (sg != null) {
            resp = client.iterateArticleInfo(sg.firstArticle, sg.lastArticle)
            var threader = Threader()
            var graph = threader.thread(resp)
            article = (graph as Article?)!!
        }
        return article
    }

    fun getArticleBody(sg: Newsgroup?, id: Long)
    {

    }

    /*
        Custom Exception class for newsgroup connection
     */
    class NewsgroupConnectionException(message:String): Exception(message) {}

}
