package hamu.hoge.kotlin.com.kenia

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

class MyApplication : Application() {

    /* TODO:Commitする前にIDは実値から環境変数に直しておく */
    val CONSUMER_KEY = ""
    val CONSUMER_SECRET = ""

    override fun onCreate() {
        super.onCreate()

        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(CONSUMER_KEY,CONSUMER_SECRET))
                .debug(true)
                .build()
        Twitter.initialize(config)

    }
}