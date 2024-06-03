package com.dicoding.storyviewapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.storyviewapp.data.remote.response.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStory(story: List<ListStoryItem>)

    @Query("SELECT * FROM story_list")
    fun getAllStory(): PagingSource<Int, ListStoryItem>

    @Query("DELETE FROM story_list")
    suspend fun deleteAll()

}