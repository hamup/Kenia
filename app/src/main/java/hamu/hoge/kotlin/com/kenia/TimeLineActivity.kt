package hamu.hoge.kotlin.com.kenia

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AbsListView
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

        verifyUserIsLoggedIn()

        adapter = TweetAdapter(this, tweetList)
        listview_timeline.adapter = adapter

        /* first load timeline */
        getHomeTimeLine()

        listview_timeline.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if (totalItemCount != 0 && totalItemCount == firstVisibleItem + visibleItemCount) {
                    getHomeTimeLine(null, tweetList.last().id)
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {

            }
        })

        swipe_refresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                if (!tweetList.isEmpty()) {
                    getHomeTimeLine(tweetList.first().id, null)
                }
                refreshEndNotify()
            }
        })

        fab.setOnClickListener {
            // TODO
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun getHomeTimeLine(sinceId: Long? = null, maxId: Long? = null) {
        val call = TwitterCore.getInstance().apiClient.statusesService.homeTimeline(20, sinceId, maxId, false, false, false, false)
        call.enqueue(object : Callback<List<Tweet>>() {
            override fun success(result: Result<List<Tweet>>?) {
                if (sinceId != null) {
                    val tmp_tweetList = tweetList.toMutableList()
                    tweetList.clear()
                    tweetList.addAll(result!!.data)
                    tweetList.addAll(tmp_tweetList)
                } else {
                    tweetList.addAll(result!!.data)
                }
                adapter?.notifyDataSetChanged()
                Log.d(TAG, "success to get home timeline.")
            }

            override fun failure(exception: TwitterException?) {
                //val toast = Toast.makeText(applicationContext, "タイムラインの取得回数が制限に達しました。", Toast.LENGTH_SHORT)
                //toast.show()
                Log.d(TAG, "failed to get home timeline.")
            }
        })
    }

    private fun verifyUserIsLoggedIn() {
        if (TwitterCore.getInstance().sessionManager.activeSession?.userId == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun refreshEndNotify() {
        if (swipe_refresh.isRefreshing()) {
            swipe_refresh.setRefreshing(false)
        }
    }
}