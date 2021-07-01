package com.jsycn.pj_project.core.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jsycn.pj_project.core.entity.StockBean
import com.jsycn.pj_project.core.entity.StockDetails

/**
 * Author: jsync
 * Date: 2021/2/19 15:41
 * Description:
 * History:
 */
const val LAST_DATABASE_VERSION = 6
const val CURRENT_DATABASE_VERSION = LAST_DATABASE_VERSION+1

@Database(entities = [StockBean::class, StockDetails::class], version = CURRENT_DATABASE_VERSION)
abstract class StockDataBase : RoomDatabase() {
    abstract fun stockDao(): StockDao
    abstract fun stockDetailsDao(): StockDetailsDao
}