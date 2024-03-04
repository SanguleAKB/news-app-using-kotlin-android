package com.example.newnewsapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.contentValuesOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newnewsapp.models.Article
import com.example.newnewsapp.models.NewsResponse
import com.example.newnewsapp.repository.NewsRepository
import com.example.newnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(app: Application, val newsRepository: NewsRepository): AndroidViewModel(app) {

    val headlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse:NewsResponse?=null
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse:NewsResponse?=null
    var newSearchQuery:String?=null
    var oldSearchQuery:String?=null

    //Science
    val scienceNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var sciencePage = 1
    var scienceResponse:NewsResponse?=null

    //Technology
    val technologyNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var technologyPage = 1
    var technologyResponse:NewsResponse?=null


    //Business
    val businessNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var businessPage = 1
    var businessResponse:NewsResponse?=null

    //Health
    val healthNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var healthPage = 1
    var healthResponse:NewsResponse?=null

    //Sport
    val sportNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var sportPage = 1
    var sportResponse:NewsResponse?=null

    //Entertainment
    val entertainmentNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var entertainmentPage = 1
    var entertainmentResponse:NewsResponse?=null

    init {
        getHeadlines("in")
        fetchScienceNews("in")
        fetchHealthNews("in")
        fetchTechnologyNews("in")
        fetchEntertainmentNews("in")
        fetchBusinessNews("in")
        fetchSportNews("in")
    }

    fun getHeadlines(countryCode: String) = viewModelScope.launch {
        headlinesInternet(countryCode)
    }
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNewsInternet(searchQuery)
    }

    fun fetchScienceNews(countryCode: String) = viewModelScope.launch {
        scienceNewsInternet(countryCode)
    }
    fun fetchHealthNews(countryCode: String) = viewModelScope.launch {
        healthNewsInternet(countryCode)
    }

    fun fetchTechnologyNews(countryCode: String) = viewModelScope.launch {
        technologyNewsInternet(countryCode)
    }

    fun fetchEntertainmentNews(countryCode: String) = viewModelScope.launch {
        entertainmentNewsInternet(countryCode)
    }
    fun fetchSportNews(countryCode: String) = viewModelScope.launch {
        sportNewsInternet(countryCode)
    }
    fun fetchBusinessNews(countryCode: String) = viewModelScope.launch {
        businessNewsInternet(countryCode)
    }
    fun addToFavorites(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }


    fun getFavouriteNews() = newsRepository.getFavouriteNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    private fun handleHeadlinesResponse(responde: Response<NewsResponse>): Resource<NewsResponse> {
        if (responde.isSuccessful){
            responde.body()?.let { resultResponse ->
                headlinesPage++
                if (headlinesResponse==null){
                    headlinesResponse=resultResponse
                }else{
                    val oldArticles = headlinesResponse?.articles
                    val newsArticles = resultResponse.articles
                    oldArticles?.addAll(newsArticles)
                }
                return Resource.Success(headlinesResponse?:resultResponse)
            }
        }
        return Resource.Error(responde.message())
    }

    //Science news response
    private fun handleScienceResponse(responde: Response<NewsResponse>):Resource<NewsResponse>{
        if (responde.isSuccessful){
            responde.body()?.let { resultResponse ->
                sciencePage++
                if (scienceResponse == null){
                    scienceResponse=resultResponse
                }else{
                    val oldArticles =  scienceResponse?.articles
                    val newsArticle = resultResponse.articles
                    oldArticles?.addAll(newsArticle)
                }
                return Resource.Success(scienceResponse?:resultResponse)
            }
        }
        return Resource.Error(responde.message())
    }

    //Health news response
    private fun handleHealthResponse(responde: Response<NewsResponse>):Resource<NewsResponse>{
        if (responde.isSuccessful){
            responde.body()?.let { resultResponse ->
                healthPage++
                if (healthResponse == null){
                    healthResponse=resultResponse
                }else{
                    val oldArticles =  healthResponse?.articles
                    val newsArticle = resultResponse.articles
                    oldArticles?.addAll(newsArticle)
                }
                return Resource.Success(healthResponse?:resultResponse)
            }
        }
        return Resource.Error(responde.message())
    }

    //Technology news response
    private fun handleTechnologyResponse(responde: Response<NewsResponse>):Resource<NewsResponse>{
        if (responde.isSuccessful){
            responde.body()?.let { resultResponse ->
                technologyPage++
                if (technologyResponse == null){
                    technologyResponse=resultResponse
                }else{
                    val oldArticles =  technologyResponse?.articles
                    val newsArticle = resultResponse.articles
                    oldArticles?.addAll(newsArticle)
                }
                return Resource.Success(technologyResponse?:resultResponse)
            }
        }
        return Resource.Error(responde.message())
    }

    //Entertainment news response
    private fun handleEntertainmentResponse(responde: Response<NewsResponse>):Resource<NewsResponse>{
        if (responde.isSuccessful){
            responde.body()?.let { resultResponse ->
                entertainmentPage++
                if ( entertainmentResponse== null){
                    entertainmentResponse=resultResponse
                }else{
                    val oldArticles =  entertainmentResponse?.articles
                    val newsArticle = resultResponse.articles
                    oldArticles?.addAll(newsArticle)
                }
                return Resource.Success(entertainmentResponse?:resultResponse)
            }
        }
        return Resource.Error(responde.message())
    }

    //Sport news response
    private fun handleSportResponse(responde: Response<NewsResponse>):Resource<NewsResponse>{
        if (responde.isSuccessful){
            responde.body()?.let { resultResponse ->
                sportPage++
                if ( sportResponse== null){
                    sportResponse=resultResponse
                }else{
                    val oldArticles =  sportResponse?.articles
                    val newsArticle = resultResponse.articles
                    oldArticles?.addAll(newsArticle)
                }
                return Resource.Success(sportResponse?:resultResponse)
            }
        }
        return Resource.Error(responde.message())
    }

    //Business news response
    private fun handleBusinessResponse(responde: Response<NewsResponse>):Resource<NewsResponse>{
        if (responde.isSuccessful){
            responde.body()?.let { resultResponse ->
                businessPage++
                if ( businessResponse== null){
                    businessResponse=resultResponse
                }else{
                    val oldArticles =  businessResponse?.articles
                    val newsArticle = resultResponse.articles
                    oldArticles?.addAll(newsArticle)
                }
                return Resource.Success(businessResponse?:resultResponse)
            }
        }
        return Resource.Error(responde.message())
    }

    //Search news response
    private fun handleSearchNewsResponse(responde: Response<NewsResponse>): Resource<NewsResponse> {
        if (responde.isSuccessful){
            responde.body()?.let { resultResponse ->
                if (headlinesResponse==null || newSearchQuery!=oldSearchQuery){
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                }else{
                    searchNewsPage++
                    val oldArticles = headlinesResponse?.articles
                    val newsArticles = resultResponse.articles
                    oldArticles?.addAll(newsArticles)
                }
                return Resource.Success(headlinesResponse?:resultResponse)
            }
        }
        return Resource.Error(responde.message())
    }

    fun internetConnection(context: Context):Boolean{
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when{
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }?:false
        }
    }

    private  suspend fun headlinesInternet(countryCode: String){
        headlines.postValue(Resource.Loading())

        try {
            if (internetConnection(this.getApplication())){
                val response = newsRepository.getHeadlines(countryCode,headlinesPage)
                headlines.postValue(handleHeadlinesResponse(response))
            }else{
                headlines.postValue(Resource.Error("No internet connectivity"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> headlines.postValue(Resource.Error("Unable to connect"))
                else -> headlines.postValue(Resource.Error("No signal"))
            }
        }
    }

    //Science Internet
    private suspend fun scienceNewsInternet(countryCode: String){
        scienceNews.postValue(Resource.Loading())

        try {
            if (internetConnection(this.getApplication())){
                val response = newsRepository.getScienceNews(countryCode,sciencePage,"science")
                scienceNews.postValue(handleScienceResponse(response))
            }else{
                scienceNews.postValue(Resource.Error("No internet connectivity"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> scienceNews.postValue(Resource.Error("Unable to connect"))
                else -> scienceNews.postValue(Resource.Error("No signal"))
            }
        }
    }

    // Health Internet
    private suspend fun healthNewsInternet(countryCode: String){
        healthNews.postValue(Resource.Loading())

        try {
            if (internetConnection(this.getApplication())){
                val response = newsRepository.getHealthNews(countryCode,sciencePage,"health")
                healthNews.postValue(handleHealthResponse(response))
            }else{
                healthNews.postValue(Resource.Error("No internet connectivity"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> healthNews.postValue(Resource.Error("Unable to connect"))
                else -> healthNews.postValue(Resource.Error("No signal"))
            }
        }
    }

    //Technology Internet
    private suspend fun technologyNewsInternet(countryCode: String){
        technologyNews.postValue(Resource.Loading())

        try {
            if (internetConnection(this.getApplication())){
                val response = newsRepository.getTechnologyNews(countryCode,sciencePage,"technology")
                technologyNews.postValue(handleTechnologyResponse(response))
            }else{
                technologyNews.postValue(Resource.Error("No internet connectivity"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> technologyNews.postValue(Resource.Error("Unable to connect"))
                else -> technologyNews.postValue(Resource.Error("No signal"))
            }
        }
    }

    //Entertainment Internet
    private suspend fun entertainmentNewsInternet(countryCode: String){
        entertainmentNews.postValue(Resource.Loading())

        try {
            if (internetConnection(this.getApplication())){
                val response = newsRepository.getEntertainmentNews(countryCode,sciencePage,"entertainment")
                entertainmentNews.postValue(handleEntertainmentResponse(response))
            }else{
                entertainmentNews.postValue(Resource.Error("No internet connectivity"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> entertainmentNews.postValue(Resource.Error("Unable to connect"))
                else -> entertainmentNews.postValue(Resource.Error("No signal"))
            }
        }
    }



    //Sport Internet
    private suspend fun sportNewsInternet(countryCode: String){
        sportNews.postValue(Resource.Loading())

        try {
            if (internetConnection(this.getApplication())){
                val response = newsRepository.getSportsNews(countryCode,sportPage,"sport")
                sportNews.postValue(handleSportResponse(response))
            }else{
                sportNews.postValue(Resource.Error("No internet connectivity"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> sportNews.postValue(Resource.Error("Unable to connect"))
                else -> sportNews.postValue(Resource.Error("No signal"))
            }
        }
    }

    //Business Internet
    private suspend fun businessNewsInternet(countryCode: String){
        businessNews.postValue(Resource.Loading())

        try {
            if (internetConnection(this.getApplication())){
                val response = newsRepository.getBusinessNews(countryCode,businessPage,"business")
                businessNews.postValue(handleBusinessResponse(response))
            }else{
                businessNews.postValue(Resource.Error("No internet connectivity"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> businessNews.postValue(Resource.Error("Unable to connect"))
                else -> businessNews.postValue(Resource.Error("No signal"))
            }
        }
    }


    //Search Internet
    private  suspend fun searchNewsInternet(searchQuery: String){
        newSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading())
        try {
            if (internetConnection(this.getApplication())){
                val response = newsRepository.searchNews(searchQuery,searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            }else{
                searchNews.postValue(Resource.Error("No Internet connectivity"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> headlines.postValue(Resource.Error("Unable to connect"))
                else -> headlines.postValue(Resource.Error("No signal"))
            }
        }
    }



}




