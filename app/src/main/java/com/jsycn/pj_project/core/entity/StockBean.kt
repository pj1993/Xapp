package com.jsycn.pj_project.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: jsync
 * Date: 2021/2/19 14:29
 * Description: 股票实体
 * History:
 */
@Entity
data class StockBean (
    @PrimaryKey(autoGenerate = true) val stockId :Int?,
    @ColumnInfo(name = "stock_code") val stockCode :String?,
    @ColumnInfo(name = "stock_name") val stockName :String?
)