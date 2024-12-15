package com.example.projekakhirppapb.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface NovelDao {
    @Insert
    suspend fun insert(novel: NovelEntity)

    @Update
    suspend fun update(novel: NovelEntity)

    @Delete
    suspend fun delete(novel: NovelEntity)

    @Query("SELECT * FROM novel_table")
    suspend fun getAllNovel(): List<NovelEntity>

    @Query("DELETE FROM novel_table WHERE id = :eventId")
    suspend fun deleteNovelById(eventId: String)
}