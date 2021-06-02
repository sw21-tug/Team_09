/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tugraz.asd.modernnewsgroupapp.db

import androidx.room.*
import androidx.room.FtsOptions.Order
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer


/**
 * Interface for database access for User related operations.
 */
@Dao
interface NewsgroupServerDao {
    @Query("SELECT COUNT(id) FROM NewsgroupServer")
    suspend fun getCount(): Int

    @Query("SELECT * FROM NewsgroupServer")
    suspend fun getAll(): List<NewsgroupServer>

    @Insert
    suspend fun insert(server: NewsgroupServer): Long

    @Insert
    suspend fun insertAll(vararg servers: NewsgroupServer)

    @Delete
    suspend fun delete(server: NewsgroupServer)

    @Query("DELETE FROM NewsgroupServer")
    suspend fun deleteAll()

    @Query("UPDATE NewsgroupServer SET alias=:serverAlias WHERE id=:serverId")
    suspend fun updateAlias(serverId: Int, serverAlias: String)

    @Query("SELECT * FROM NewsgroupServer WHERE current=1")
    suspend fun getCurrentServer(): NewsgroupServer

    @Query("UPDATE NewsgroupServer SET current=:current WHERE id=:serverId")
    suspend fun updateCurrentServer(serverId: Int, current: Boolean)
}
