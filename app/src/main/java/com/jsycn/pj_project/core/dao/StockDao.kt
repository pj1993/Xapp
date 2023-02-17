package com.jsycn.pj_project.core.dao

import androidx.room.*
import com.jsycn.pj_project.core.entity.StockBean

/**
 * Author: jsync
 * Date: 2021/2/19 15:31
 * Description:
 * History:
 */
@Dao
interface StockDao {
    @Query("SELECT * from stockbean")
    fun getAll():MutableList<StockBean>

    @Query("SELECT * FROM stockbean WHERE stockId IN (:stockIds)")
    fun loadAllByIds(stockIds: IntArray): List<StockBean>

//    @Query("SELECT * FROM stock WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): StockBean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg stocks: StockBean)

    @Delete
    fun delete(stock: StockBean)
}