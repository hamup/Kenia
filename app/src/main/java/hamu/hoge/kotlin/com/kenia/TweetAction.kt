package hamu.hoge.kotlin.com.kenia

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet

fun getHomeTimeLine(tweetList: MutableList<Tweet>, adapter: TweetAdapter?, sinceId: Long? = null, maxId: Long? = null) {
    val call = TwitterCore.getInstance().apiClient.statusesService.homeTimeline(20, sinceId, maxId, false, false, false, false)
    call.enqueue(object : Callback<List<Tweet>>() {
        override fun success(result: Result<List<Tweet>>?) {
            if (sinceId != null) {
                val tmp_tweetList = tweetList.toMutableList()
                tweetList.clear()
                tweetList.addAll(result!!.data)
                tweetList.addAll(tmp_tweetList)
            } else if (maxId != null){
                tweetList.addAll(result!!.data.drop(1))
            } else {
                tweetList.addAll(result!!.data)
            }

            adapter?.notifyDataSetChanged()
            Log.d(TimeLineActivity.TAG, "success to get home timeline.")
        }

        override fun failure(exception: TwitterException?) {
            //val toast = Toast.makeText(applicationContext, "タイムラインの取得回数が制限に達しました。", Toast.LENGTH_SHORT)
            //toast.show()
            Log.d(TimeLineActivity.TAG, "failed to get home timeline.")
        }
    })
}

fun createFavorite(context: Context, id: Long) {
    val call = TwitterCore.getInstance().apiClient.favoriteService.create(id,true)
    call.enqueue(object : Callback<Tweet>() {
        override fun success(result: Result<Tweet>?) {
            val toast = Toast.makeText(context, "いいねしました", Toast.LENGTH_SHORT)
            toast.show()
            Log.d(TimeLineActivity.TAG, "success to create favorite.")
        }

        override fun failure(exception: TwitterException?) {
            Log.d(TimeLineActivity.TAG, "failed to create favorite.")
        }

    })
}