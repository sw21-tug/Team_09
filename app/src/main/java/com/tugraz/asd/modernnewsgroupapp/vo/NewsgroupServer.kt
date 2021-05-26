package com.tugraz.asd.modernnewsgroupapp.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsgroupServer(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        @ColumnInfo(name = "host") val host: String,
        @ColumnInfo(name = "port") val port: Int = 119,
        @ColumnInfo(name = "alias") var alias: String = "",
        @ColumnInfo(name = "username") var username: String = ""
)