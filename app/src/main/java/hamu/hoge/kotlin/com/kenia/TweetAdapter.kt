package hamu.hoge.kotlin.com.kenia

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.tweet_row.view.*

class TweetAdapter(context: Context, tweetList: MutableList<Tweet>): BaseAdapter() {

    private val inflater: LayoutInflater
    val tweetList = tweetList

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var holder: ViewHolder

        if (view == null) {
            view = inflater.inflate(R.layout.tweet_row, parent, false)
            holder = ViewHolder(view.textview_user_name, view.textview_screen_name, view.textview_messages)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val tweet = getItem(position)
        holder.userNameTextView.text = tweet.user.name
        holder.screenNameTextView.text = tweet.user.screenName
        holder.messagesTextView.text = tweet.text

        return view!!
    }

    override fun getItem(position: Int): Tweet {
        return tweetList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return tweetList.get(position).getId()
    }

    override fun getCount(): Int {
        return tweetList.size
    }

}

data class ViewHolder(val userNameTextView: TextView, val screenNameTextView: TextView, val messagesTextView: TextView)