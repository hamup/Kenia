package hamu.hoge.kotlin.com.kenia

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
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
            holder = ViewHolder(view.textview_user_name, view.textview_screen_name,
                                view.textview_messages, view.textview_via_client,
                                view.textview_reteet_by_user, view.imageview_usericon)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val tweet = getItem(position)
        var textColor: String? = null

        /* tweet or retweet
         * TODO: 類似コードをキレイにまとめる
         */
        if (tweet.retweetedStatus == null) {
            holder.userNameTextView.text = tweet.user.name
            holder.screenNameTextView.text = "@" + tweet.user.screenName
            holder.messagesTextView.text = tweet.text
            holder.viaTextView.text = "via " + tweet.source.split(Regex("[>,<]"))[2]
            holder.retweetByUserTextView.text = ""
            Picasso.get().load(tweet.user.profileImageUrlHttps).into(holder.userIconImageView)

            textColor = "#696969"
        } else {
            holder.userNameTextView.text = tweet.retweetedStatus.user.name
            holder.screenNameTextView.text = "@" + tweet.retweetedStatus.user.screenName
            holder.messagesTextView.text = tweet.retweetedStatus.text
            holder.viaTextView.text = "via " + tweet.retweetedStatus.source.split(Regex("[>,<]"))[2]
            holder.retweetByUserTextView.text = "Retweet by @" + tweet.user.screenName
            Picasso.get().load(tweet.retweetedStatus.user.profileImageUrlHttps).into(holder.userIconImageView)

            textColor = "#008000"
        }

        holder.userNameTextView.setTextColor(Color.parseColor(textColor))
        holder.screenNameTextView.setTextColor(Color.parseColor(textColor))
        holder.messagesTextView.setTextColor(Color.parseColor(textColor))
        holder.viaTextView.setTextColor(Color.parseColor(textColor))
        holder.retweetByUserTextView.setTextColor(Color.parseColor(textColor))

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

data class ViewHolder(val userNameTextView: TextView, val screenNameTextView: TextView,
                      val messagesTextView: TextView, val viaTextView: TextView,
                      val retweetByUserTextView: TextView,val userIconImageView: ImageView)