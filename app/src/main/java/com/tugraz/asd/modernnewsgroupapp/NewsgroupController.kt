package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.db.NewsgroupDb
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer

class NewsgroupController {
    var servers: HashMap<NewsgroupServer, NewsgroupConnection> = HashMap<NewsgroupServer, NewsgroupConnection>()
    lateinit var currentServer: NewsgroupServer
    lateinit var currentNewsgroups: List<Newsgroup>
    lateinit var db: NewsgroupDb
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

    fun isCurrentNewsgroupsInitialised() = ::currentNewsgroups.isInitialized

    fun removeServer(server: NewsgroupServer) {
        servers.remove(server)
    }

    suspend fun loadServersFromDB() {
        val query = db.newsgroupServerDao().getAll()
        for(s in query) {
            addServer(s)
        }
    }

    suspend fun loadNewsgroupsFromDB() {
        currentNewsgroups = db.newsgroupDao().getNewsgroupsForServerId(currentServer.id)
        println("Loaded NGs from DB: " + currentNewsgroups.size)
    }

    suspend fun saveNewsgroups() {
        if(this::currentServer.isInitialized) {
            println("Saving NGs to DB: " + currentNewsgroups.size)
            db.newsgroupDao().insertAll(currentNewsgroups)
        }
    }

    suspend fun removeCurrentServer() {
        if(this::currentServer.isInitialized) {
            db.newsgroupServerDao().delete(currentServer)
            db.newsgroupDao().deleteNewsgroupsForServerId(currentServer.id)
            servers.remove(currentServer)
        }
    }

    fun renameCurrentAlias(newAlias: String){
        if(this::currentServer.isInitialized)
            currentServer.alias = newAlias
    }

}