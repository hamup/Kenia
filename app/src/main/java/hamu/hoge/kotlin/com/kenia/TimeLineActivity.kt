package hamu.hoge.kotlin.com.kenia

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.activity_timeline.*

class TimeLineActivity : AppCompatActivity() {
    companion object {
        val TAG = "TimeLineActivity"
    }

    var adapter: TweetAdapter? = null
    var tweetList: MutableList<Tweet> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)


        adapter = TweetAdapter(this, tweetList)
        listview_timeline.adapter = adapter
        getHomeTimeLine()
    }

    fun getHomeTimeLine() {
        val call = TwitterCore.getInstance().apiClient.statusesService.homeTimeline(20, null, null, false, false, false,false)
        call.enqueue(object : Callback<List<Tweet>>(){
            override fun success(result: Result<List<Tweet>>?) {
                if (result != null) {
                    tweetList.addAll(result.data)
                    adapter?.notifyDataSetChanged()
                    Log.d(TAG, "success to get home timeline.")
                }
            }

            override fun failure(exception: TwitterException?) {
                Log.d(TAG, "failed to get home timeline.")
            }
        })
    }
}