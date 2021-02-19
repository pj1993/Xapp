package com.jsycn.pj_project.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jsycn.pj_project.entity.StockBean

/**
 * Author: jsync
 * Date: 2021/2/19 15:41
 * Description:
 * History:
 */
@Database(entities = [StockBean::class],version = 4)
abstract class StockDataBase : RoomDatabase() {
    abstract fun stockDao():StockDao
}