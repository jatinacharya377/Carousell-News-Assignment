package com.carousell.carousellnews.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carousell.carousellnews.MyApplication
import com.carousell.carousellnews.R
import com.carousell.carousellnews.data.error.ErrorCallback
import com.carousell.carousellnews.data.model.Article
import com.carousell.carousellnews.data.remote.repository.ArticlesRepository
import kotlinx.coroutines.delay
import org.joda.time.Period
import org.joda.time.PeriodType
import org.joda.time.format.PeriodFormatterBuilder

class ArticlesViewModel(application: Application): ViewModelBase(application)  {

    private val repo = ArticlesRepository()
    private val _articlesListLiveData = MutableLiveData<List<Article>>()
    val articlesListLiveData: LiveData<List<Article>>
        get() = _articlesListLiveData

    init {
        getArticles()
    }

    fun getArticles() {
        coroutineScope {
            delay(1000)
            error.postValue(ErrorCallback(false))
            val articlesList = repo.getArticles()
            if (articlesList.isNotEmpty()) {
                _articlesListLiveData.postValue(getTheModifiedArticlesList(articlesList))
            } else {
                error.postValue(ErrorCallback(true, MyApplication.INSTANCE.getString(R.string.bad_data_received_error)))
            }
        }
    }

    private fun getTheModifiedArticlesList(articlesList: List<Article>): List<Article> {
        articlesList.forEach {
            it.published_time = it.time_created?.let { timeCreated -> getThePublishedTime(timeCreated) }
        }
        return articlesList
    }

    private fun getThePublishedTime(timeCreated: Long): String {
        val period = Period(timeCreated * 1000, System.currentTimeMillis())
        val formatterBuilder = PeriodFormatterBuilder()
        val formatter = when {
            period.years > 0 -> formatterBuilder.appendYears().appendSuffix(" year ago", " years ago").toFormatter()
            period.months > 0 -> formatterBuilder.appendMonths().appendSuffix(" month ago", " months ago").toFormatter()
            period.weeks > 0 -> formatterBuilder.appendWeeks().appendSuffix(" week ago", " weeks ago").toFormatter()
            period.days > 0 -> formatterBuilder.appendDays().appendSuffix(" day ago", " days ago").toFormatter()
            period.hours > 0 -> formatterBuilder.appendHours().appendSuffix(" hour ago", " hours ago").toFormatter()
            period.minutes > 0 -> formatterBuilder.appendMinutes().appendSuffix(" minute ago", " minutes ago").toFormatter()
            period.seconds > 0 -> formatterBuilder.appendSeconds().appendSuffix(" second ago", "seconds ago").toFormatter()
            else -> formatterBuilder.toFormatter()
        }
        return formatter.print(period)
    }

    override fun onCreate() {}
}