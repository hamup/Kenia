package hamu.hoge.kotlin.com.kenia

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class TweetActionDialogFragment: DialogFragment() {

    lateinit var onSelectedItem: DialogInterface.OnClickListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Tweet Action")
                    .setItems(R.array.TweetAction, onSelectedItem)

            builder.create()
        } ?:throw IllegalStateException("Activity cannot be null")
    }
}