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


import androidx.room.Database
import androidx.room.RoomDatabase
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer

/**
 * Main database description.
 */
@Database(
    entities = [
        Newsgroup::class,
        NewsgroupServer::class],
    version = 9,
    exportSchema = false
)
abstract class NewsgroupDb : RoomDatabase() {

    abstract fun newsgroupDao(): NewsgroupDao

    abstract fun newsgroupServerDao(): NewsgroupServerDao
}
