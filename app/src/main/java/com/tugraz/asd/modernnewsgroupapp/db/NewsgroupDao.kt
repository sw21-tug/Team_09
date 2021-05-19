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
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup

/**
 * Interface for database access for User related operations.
 */
@Dao
interface NewsgroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsgroup: Newsgroup)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(servers: List<Newsgroup>)

    @Delete
    suspend fun delete(newsgroup: Newsgroup)

    @Query("DELETE FROM Newsgroup")
    suspend fun deleteAll()

    @Query("DELETE FROM Newsgroup WHERE newsgroup_server_id = :serverId")
    suspend fun deleteNewsgroupsForServerId(serverId: Int)

    @Query("SELECT * FROM Newsgroup WHERE newsgroup_server_id = :serverId ")
    suspend fun getNewsgroupsForServerId(serverId: Int) : List<Newsgroup>
}
