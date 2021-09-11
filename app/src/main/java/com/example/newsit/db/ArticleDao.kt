package com.example.newsit.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsit.models.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article) :Long

    @Query("SELECT *FROM articles")
    fun getAllArticles() : LiveData<List<Article>>

    @Delete
    suspend fun delete (article: Article)
}