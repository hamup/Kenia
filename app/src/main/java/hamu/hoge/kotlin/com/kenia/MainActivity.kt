package hamu.hoge.kotlin.com.kenia

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.twitter.sdk.android.core.TwitterCore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (TwitterCore.getInstance().sessionManager.activeSession?.userId == null) {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this,TimeLineActivity::class.java)
            startActivity(intent)
        }
    }
}
