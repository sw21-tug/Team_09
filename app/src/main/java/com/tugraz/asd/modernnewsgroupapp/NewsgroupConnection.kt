package com.tugraz.asd.modernnewsgroupapp

import org.apache.commons.net.nntp.NNTPClient
import org.apache.commons.net.nntp.NewsgroupInfo
import java.io.IOException
import java.lang.Exception
import java.net.SocketException
import java.net.UnknownHostException

object NewsgroupConnection {
    var server: NewsgroupServer? = null
    private var client: NNTPClient = NNTPClient()

    fun ensureConnection() {
        if(!client.isConnected) {
            try {
                client.connect(server?.host)
            } catch (e: Exception) {
                when(e) {
                    is UnknownHostException -> {
                        throw NewsgroupConnectionException("Unknown host while connecting to newsgroup server")
                    }
                    is IOException -> {
                        throw NewsgroupConnectionException("IOException while connecting to newsgroup server")
                    }
                    else -> throw e
                }
            }
        }
    }

    fun getNewsGroups(): ArrayList<Newsgroup> {
        ensureConnection()
        var response = client.listNewsgroups()
        var groups: ArrayList<Newsgroup> = ArrayList()

        for (group in response) {
            groups.add(Newsgroup(group.newsgroup))
        }

        return groups
    }

    /*
        Custom Exception class for newsgroup connection
     */
    class NewsgroupConnectionException(message:String): Exception(message) {}

}