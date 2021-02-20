package com.jsycn.pj_project.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: jsync
 * Date: 2021/2/19 14:32
 * Description:
 * History:
 */
@Entity
data class StockDetails(
        @PrimaryKey(autoGenerate = true) var id: Int?,
        @ColumnInfo(name = "stockId") var stockId: Int,
        @ColumnInfo(name = "quantity") val quantity: Int,    //买入卖出数量
        @ColumnInfo(name = "price") var price: Double,    //买入卖出价格
)