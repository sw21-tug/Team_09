package com.tugraz.asd.modernnewsgroupapp.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.text.count as count

@Entity
data class Newsgroup (
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "newsgroup_server_id") val newsgroupServerId: Int,
        @ColumnInfo(name = "parent") var parent: String? = null,
        @ColumnInfo(name = "hierarchy_level") var hierarchyLevel: Int? = null,
        @ColumnInfo(name = "subscribed") var subscribed: Boolean = false
) {
    // if newsgroup has at least a dot in its name -> indicates subgroup
    fun isSubgroup(): Boolean {
        return name.filter { it == '.' }.count() >= 1
    }

    fun setParentNewsgroup() {
        parent = name.substringBeforeLast(".")
    }

    fun setHierarchyLevel() {
        hierarchyLevel = name.filter { it == '.' }.count()
    }
}