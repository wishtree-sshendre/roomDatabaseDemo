package com.example.basicroomdatabase

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
const val notificationID = 1
const val channelID = "channel1"
class NotificationClass:BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent){

//        println("title_Extra")
//        Log.d("title@@@",intent.extras!!.get("title_Extra").toString())
//        Log.d("desc@@@",intent.extras!!.get("desc_Extra").toString())
//
        val i = Intent(context,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,notificationID,i,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra("title_Extra"))
                .setContentText(intent.getStringExtra("desc_Extra")).setPriority(NotificationCompat.PRIORITY_HIGH).setDefaults(NotificationCompat.DEFAULT_ALL).setContentIntent(pendingIntent)
                .build()

            val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(notificationID, notification)
    }
}