package com.tugraz.asd.modernnewsgroupapp

import org.apache.commons.net.nntp.NNTPClient
import org.apache.commons.net.nntp.NewsgroupInfo

object NewsgroupConnection {
    var server: NewsgroupServer? = null
    var client: NNTPClient = NNTPClient()

    fun ensureConnection() {
        if(!client.isConnected) {
            client.connect(server?.host)
        }
    }


    fun getNewsGroups(): ArrayList<Newsgroup> {
        ensureConnection()
        var response = client.listNewsgroups()
        var groups: ArrayList<Newsgroup> = ArrayList<Newsgroup>()

        for (group in response) {
            groups.add(Newsgroup(group.newsgroup))
        }

        return groups
    }

}