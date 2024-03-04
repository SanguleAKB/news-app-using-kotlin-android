package com.example.newnewsapp.repository

import com.example.newnewsapp.api.RetrofitInstance
import com.example.newnewsapp.db.ArticleDatabase
import com.example.newnewsapp.models.Article

class NewsRepository(val db:ArticleDatabase) {

    suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String,pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getFavouriteNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

    suspend fun getHealthNews(countryCode: String, pageNumber: Int,category: String) =
        RetrofitInstance.api.getHealthNews(countryCode,pageNumber,category)

    suspend fun getSportsNews(countryCode: String,pageNumber: Int,category: String) =
        RetrofitInstance.api.getSportsNews(countryCode, category, pageNumber)

    suspend fun getScienceNews(countryCode: String,pageNumber: Int,category: String) =
        RetrofitInstance.api.getScienceNews(countryCode, category, pageNumber)

    suspend fun getTechnologyNews(countryCode: String,pageNumber: Int,category: String) =
        RetrofitInstance.api.getTechnologyNews(countryCode, category, pageNumber)

    suspend fun getBusinessNews(countryCode: String,pageNumber: Int,category: String) =
        RetrofitInstance.api.getBusinessNews(countryCode, category, pageNumber)

    suspend fun getGeneralNews(countryCode: String,pageNumber: Int,category: String) =
        RetrofitInstance.api.getGeneralNews(countryCode, category, pageNumber)

    suspend fun getEntertainmentNews(countryCode: String,pageNumber: Int,category: String) =
        RetrofitInstance.api.getEntertainmentNews(countryCode, category, pageNumber)
}