package com.example.projekakhirppapb.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "novel_table")
data class NovelEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int=0,
    @ColumnInfo(name = "judul")
    val judul: String,
    @ColumnInfo(name = "penulis")
    val penulis: String,
    @ColumnInfo(name = "tanggalTerbit")
    val tanggalTerbit: String,
    @ColumnInfo(name= "isiNovel")
    val isiNovel: String,
)
