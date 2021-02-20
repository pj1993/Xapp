package com.jsycn.pj_project.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jsycn.pj_project.entity.StockDetails

/**
 * Author: jsync
 * Date: 2021/2/20 14:32
 * Description:
 * History:
 */
@Dao
interface StockDetailsDao {

    @Query("SELECT * FROM stockdetails WHERE stockId IN (:stockIds)")
    fun loadAllByIds(stockIds: IntArray): List<StockDetails>

    @Query("SELECT * FROM stockdetails WHERE stockId = :stockId")
    fun getStockDetailsById(stockId: Int): List<StockDetails>

//    @Query("SELECT * FROM stock WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): StockBean

    @Insert
    suspend fun insertAll(vararg stocks: StockDetails)

    @Delete
    fun delete(stock: StockDetails)
}