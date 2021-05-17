package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.apache.commons.net.nntp.NNTPClient
import java.net.UnknownHostException
import kotlin.Exception

class NewsgroupConnection (var server: NewsgroupServer){
    private var client: NNTPClient = NNTPClient()

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
                        throw NewsgroupConnectionException("IOException while connecting to newsgroup server " + server.host + ": " + e.message)
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
            groups.add(Newsgroup(name = group.newsgroup, newsgroupServerId = server.id))
        }

        return groups
    }

    /*
        Custom Exception class for newsgroup connection
     */
    class NewsgroupConnectionException(message:String): Exception(message) {}

}