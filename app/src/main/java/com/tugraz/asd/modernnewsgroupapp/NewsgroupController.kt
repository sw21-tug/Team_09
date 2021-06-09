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
    var currentArticles: Article? = null
    var currentArticle: Article? = null
    var currentReplyArticle: Article? = null
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

    /*fun fetchArticles(server: NewsgroupServer): Article? {
        if(::currentNewsgroups.isInitialized) {
            return servers[server]?.getArticleHeaders(currentNewsgroup)
        }
        return null
    }*/

    fun fetchCurrentArticleBody(server: NewsgroupServer): String? {
        if(::currentNewsgroups.isInitialized && currentArticle != null) {
            return servers[server]?.getArticleBody(currentArticle!!.articleNumberLong)
        }
        return null
    }

    fun fetchArticleBodyById(server: NewsgroupServer, articleId: Long): String? {
        if (::currentNewsgroups.isInitialized && currentArticle != null) {
            return servers[server]?.getArticleBody(articleId)
        }
        return null
    }

    fun fetchArticles() {
        if(currentServer != null)
        {
            val con = getConnById(currentServer!!.id)
            currentArticles = con?.getArticleHeaders(currentNewsgroup)
        }
    }

    fun postArticle(subject: String, message: String, article: Article? = null): Boolean {
        if(currentServer == null || currentNewsgroup == null) return false

        val con = getConnById(currentServer!!.id) ?: return false

        return con.postArticle(currentNewsgroup!!, currentServer!!.email, subject, message, article)
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