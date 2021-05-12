package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer

class NewsgroupController {
    var servers: HashMap<NewsgroupServer, NewsgroupConnection> = HashMap<NewsgroupServer, NewsgroupConnection>()
    lateinit var currentServer: NewsgroupServer

    fun addServer(server: NewsgroupServer) {
        servers[server] = NewsgroupConnection(server)
    }

    fun getConnection(server: NewsgroupServer): NewsgroupConnection? {
        return servers.get(server)
    }

    fun fetchNewsGroups() {
        for ((server, con) in servers) {
            server.newsGroups = con.getNewsGroups()
        }
    }

    fun fetchNewsGroups(server: NewsgroupServer) {
        server.newsGroups = servers.get(server)?.getNewsGroups()
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