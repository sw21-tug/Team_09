package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer

class NewsgroupController {
    var servers: HashMap<NewsgroupServer, NewsgroupConnection> = HashMap<NewsgroupServer, NewsgroupConnection>()
    lateinit var currentServer: NewsgroupServer
    lateinit var currentNewsgroups: List<Newsgroup>
    var skipSetup: Boolean = false

    fun addServer(server: NewsgroupServer) {
        servers[server] = NewsgroupConnection(server)
    }

    fun fetchNewsGroups() {
        for ((_, con) in servers) {
            currentNewsgroups = con.getNewsGroups()
        }
    }

    fun fetchNewsGroups(server: NewsgroupServer) {
        currentNewsgroups = servers.get(server)?.getNewsGroups()!!
    }

    fun removeServer(server: NewsgroupServer) {
        servers.remove(server)
    }

    fun removeCurrentServer() {
        if(this::currentServer.isInitialized)
            servers.remove(currentServer)
    }

    fun renameCurrentAlias(newAlias: String){
        if(this::currentServer.isInitialized)
            currentServer.alias = newAlias
    }

}