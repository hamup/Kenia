package hamu.hoge.kotlin.com.kenia

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.widget.AbsListView
import android.widget.Toast
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
        getHomeTimeLine(tweetList, adapter)

        listview_timeline.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                if (scrollState == 0 && view!!.lastVisiblePosition == tweetList.size - 1) {
                    getHomeTimeLine(tweetList, adapter, null, tweetList.last().id)
                }

            }
        })

        listview_timeline.setOnItemClickListener { parent, view, position, id ->
            val tweetActionDialog = TweetActionDialogFragment()
            tweetActionDialog.onSelectedItem = DialogInterface.OnClickListener { dialog, index ->
                resources?.let {
                    createFavorite(this, tweetList[position].id)
                }
            }
            tweetActionDialog.show(supportFragmentManager,"tweetActionDialog")
        }

        swipe_refresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                if (!tweetList.isEmpty()) {
                    getHomeTimeLine(tweetList, adapter, tweetList.first().id, null)
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