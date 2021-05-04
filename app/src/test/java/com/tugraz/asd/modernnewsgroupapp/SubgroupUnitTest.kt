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


    // Hierarchy level 2 +
    @Test
    fun getParents() {
        init()
        val listSize = newsgroupList.size - 1
        for (i in 0..listSize) {
            newsgroupList[i].setHierarchyLevel()
            val betweenTwoDots = newsgroupList[i].name.substringBeforeLast(".")
            if (newsgroupList[i].hierarchyLevel!! > 1 && newsgroupList[i - 1].name.substringBeforeLast(".") != betweenTwoDots) {
                newsgroupList[i - 1].setParentNewsgroup()
                // System.out.println(newsgroupList[i])

            }
        }

        assertTrue("Subgroups with hierarchy 2 or higher exist", newsgroupList.any(Newsgroup::hasSubgroup))
    }


    // Hierarchy level 1 +
    @Test
    fun getSubgroups() {
        init()
        val a = newsgroupList.size - 1
        for( i in 0..a) {
            newsgroupList[i].setHierarchyLevel()
        }

        assertTrue("Subgroups exist", newsgroupList.any(Newsgroup::isSubgroup))
    }

    // You must set hierarchy level
    @Test
    fun noSubgroups() {
        init()

        assertFalse(newsgroupList.any(Newsgroup::hasSubgroup))
    }

}