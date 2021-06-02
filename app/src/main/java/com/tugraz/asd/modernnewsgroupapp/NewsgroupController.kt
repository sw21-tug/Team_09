package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.db.NewsgroupDb
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.apache.commons.net.nntp.Article
import org.apache.commons.net.nntp.Threadable

class NewsgroupController {
    var servers: HashMap<NewsgroupServer, NewsgroupConnection> = HashMap<NewsgroupServer, NewsgroupConnection>()
    var currentServer: NewsgroupServer? = null
    lateinit var currentNewsgroups: List<Newsgroup>
    var currentNewsgroup: Newsgroup? = null
    var currentArticles: Article? = null
    lateinit var db: NewsgroupDb
    var skipSetup: Boolean = false

    fun addServer(server: NewsgroupServer) {
        if(!servers.containsKey(server)) {
            servers[server] = NewsgroupConnection(server)
        }
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

    fun fetchArticles() {
        if(currentServer != null)
        {
            val con = getConnById(currentServer!!.id)
            currentArticles = con?.getArticleHeaders(currentNewsgroup)
        }
    }

    fun postArticle(subject: String, message: String): Boolean {
        if(currentServer == null || currentNewsgroup == null) return false

        val con = getConnById(currentServer!!.id) ?: return false

        return con.postArticle(currentNewsgroup!!, currentServer!!.email, subject, message)
    }

    suspend fun loadServersFromDB() {
        val query = db.newsgroupServerDao().getAll()
        for(s in query) {
            addServer(s)
        }
    }

    suspend fun loadNewsgroupsFromDB() {
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

    fun renameCurrentAlias(newAlias: String){
        if(currentServer != null)
            currentServer!!.alias = newAlias
    }

}