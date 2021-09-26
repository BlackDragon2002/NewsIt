package com.example.newsit.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsit.models.Article
import com.example.newsit.models.NewsResponse
import com.example.newsit.repository.NewsRepository
import com.example.newsit.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsViewModel(
    val newsRepository: NewsRepository
) :ViewModel(){

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsResponse :NewsResponse?=null
    var breakingNewsPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNewsResponse :NewsResponse?=null
    var searchNewsPage = 1

    init {
        getBreakingNews("us")
    }
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery:String)=viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response=newsRepository.getSearchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if(breakingNewsResponse==null){
                    breakingNewsResponse==resultResponse
                }else{
                    val oldArticle=breakingNewsResponse?.articles
                    val newArticle=resultResponse.articles
                    oldArticle.addAll(newArticle)
                }
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if(searchNewsResponse==null){
                    searchNewsResponse==resultResponse
                }else{
                    val oldArticle=searchNewsResponse?.articles
                    val newArticle=resultResponse.articles
                    oldArticle.addAll(newArticle)
                }
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun savedArticle(article: Article)=viewModelScope.launch {
        newsRepository.upsert(article)
    }
    fun deleteArticle(article: Article)=viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
    fun getSavedNews()=newsRepository. getSavedNews()

}

