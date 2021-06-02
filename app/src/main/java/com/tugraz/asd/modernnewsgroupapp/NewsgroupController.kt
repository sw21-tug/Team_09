package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.db.NewsgroupDb
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.apache.commons.net.nntp.Article

class NewsgroupController {
    var servers: HashMap<NewsgroupServer, NewsgroupConnection> = HashMap<NewsgroupServer, NewsgroupConnection>()
    var currentServer: NewsgroupServer? = null
    lateinit var currentNewsgroups: List<Newsgroup>
    var currentNewsgroup: Newsgroup? = null
    lateinit var db: NewsgroupDb
    var skipSetup: Boolean = false

    fun addServer(server: NewsgroupServer) {
        servers[server] = NewsgroupConnection(server)
    }

    fun getConnById(id: Int): NewsgroupConnection? {
        for ((server, connection) in servers) {
            if(server.id == id)
                return connection
        }
        return null
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

    fun fetchArticles(server: NewsgroupServer): Article? {
        val conn = getConnById(server.id)

        if(::currentNewsgroups.isInitialized && conn != null)
        {
            var articles = conn?.getArticleHeaders(currentNewsgroup)
            return articles
        }else
            return null
    }

    fun removeServer(server: NewsgroupServer) {
        servers.remove(server)
    }

    suspend fun loadServersFromDB() {
        val query = db.newsgroupServerDao().getAll()
        for(s in query) {
            addServer(s)
        }
    }

    suspend fun getCurrentServerFromDB(): NewsgroupServer {
        return db.newsgroupServerDao().getCurrentServer()
    }

    suspend fun setCurrentServerDB(id: Int, current: Boolean) {
        db.newsgroupServerDao().updateCurrentServer(id, current)
    }

     suspend fun loadNewsgroupsFromDB() {
//        currentNewsgroups = db.newsgroupDao().getAll() // TODO: only get NGs for this server (also save the right id for the NGs)
        currentNewsgroups = db.newsgroupDao().getNewsgroupsForServerId(currentServer!!.id)
        println("Loaded NGs from DB: " + currentNewsgroups.size)
    }

    suspend fun saveNewsgroups() {
        if(currentServer != null && this::currentNewsgroups.isInitialized) {
            println("Saving NGs to DB: " + currentNewsgroups.size + ". Server id:" + currentServer!!.id)
            for(ng in currentNewsgroups) {
                ng.newsgroupServerId = currentServer!!.id
            }
            db.newsgroupDao().insertAll(currentNewsgroups)
        }
    }

    suspend fun removeCurrentServer() {
        if(currentServer != null) {
            db.newsgroupServerDao().delete(currentServer!!)
            db.newsgroupDao().deleteNewsgroupsForServerId(currentServer!!.id)
            servers.remove(currentServer!!)
            currentServer = null
            currentNewsgroups = emptyList()
        }
    }

    suspend fun renameCurrentAlias(newAlias: String){
        if(currentServer != null)
        {
            db.newsgroupServerDao().updateAlias(currentServer!!.id, newAlias)
            currentServer!!.alias = newAlias
        }
    }

}