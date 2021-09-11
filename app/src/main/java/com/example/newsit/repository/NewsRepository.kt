package com.example.newsit.repository

import com.example.newsit.api.RetrofitInstance
import com.example.newsit.db.ArticleDatabase
import com.example.newsit.models.Article

class NewsRepository(
    val db:ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode:String,pageNumber: Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun getSearchNews(searchQuery :String,pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)

    suspend fun upsert(article:Article)=db.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article) =db.getArticleDao().delete(article)

    fun getSavedNews()=db.getArticleDao().getAllArticles()
}