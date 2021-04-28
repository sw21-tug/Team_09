package com.tugraz.asd.modernnewsgroupapp

import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SubgroupUnitTest {

    private val HOST = "news.tugraz.at"
    private var newsgroupList: ArrayList<Newsgroup> = ArrayList<Newsgroup>()

    fun init()
    {
        val server = NewsgroupServer(HOST)
        val con = NewsgroupConnection(server)
        this.newsgroupList = con.getNewsGroups()
    }

    @Test
    fun getSubgroups() {
        init()
        assertTrue("No subgroups received", newsgroupList.any(Newsgroup::hasSubgroup))
    }
}