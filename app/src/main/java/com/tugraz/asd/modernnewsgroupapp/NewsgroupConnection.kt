package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.apache.commons.net.nntp.Article
import org.apache.commons.net.nntp.NNTPClient
import java.net.UnknownHostException
import kotlin.Exception

class NewsgroupConnection (var server: NewsgroupServer){
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

    fun getArticleHeaders(sg: Newsgroup?): ArrayList<Article>{
        ensureConnection()
        var articles: ArrayList<Article> = ArrayList()
        if (sg != null) {
            print("name of ng to select: " + sg.name)
            if(client.selectNewsgroup(sg.name))
                print("Newsgroup selected")
            else
                print("Failed select newsgroup")
        }
        //var response = client.listNewsgroups()
        if (sg != null) {
            print("st")
            var response = client.iterateArticleInfo(sg.firstArticle, sg.lastArticle)
            for(article in response)
            {
                articles.add(article)
            }
        }
        return articles
    }

    fun getArticleBody(sg: Newsgroup?, id: Long)
    {

    }

    /*
        Custom Exception class for newsgroup connection
     */
    class NewsgroupConnectionException(message:String): Exception(message) {}

}