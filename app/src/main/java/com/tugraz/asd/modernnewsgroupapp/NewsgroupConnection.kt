package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.MessageThread
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.apache.commons.net.nntp.NNTPClient
import java.net.UnknownHostException
import kotlin.Exception

class NewsgroupConnection (var server: NewsgroupServer){
    private var client: NNTPClient = NNTPClient()
    private var server_: NewsgroupServer = server

    fun ensureConnection() {
        if(!client.isConnected) {
            try {
                client.connect(server_.host, server_.port)
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
            //var messages = getMessages(ng)
            print("test")
        }
        return groups
    }

    fun getMessages(sg: Newsgroup?): ArrayList<MessageThread>{
        ensureConnection()
        var messages: ArrayList<MessageThread> = ArrayList()

        if (sg != null) {
            print("st")
            for(i in sg.firstArticle!!..sg.lastArticle!!) {
                var response = client.retrieveArticle(i)
            }
        }

        return messages
    }

    /*
        Custom Exception class for newsgroup connection
     */
    class NewsgroupConnectionException(message:String): Exception(message) {}

}