package com.example.push_alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.readPendingIntentOrNullFromParcel
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remotemessage: RemoteMessage) {
        super.onMessageReceived(remotemessage)

        createNotificationChannel() // 채널 생성 함수

        // firebase 안에서 내용 받아오기(remotemessage.data[])
        val type = remotemessage.data["type"] //type 받아오기 (3가지, custom, expand , normal)
            ?.let { NotificationType.valueOf(it) } // 자체 type 값이 있는 지 없는지 확인
        type ?: return
        val title = remotemessage.data["title"] // title 받아오기
        val message = remotemessage.data["message"] //message 받아오기

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 설정

        NotificationManagerCompat.from(this)
            .notify(type.id, createNotification(type,title,message))
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID, // id
                CHANNEL_NAME, // name
                NotificationManager.IMPORTANCE_DEFAULT // 중요도
            )
            channel.description = CHANNEL_DESCRIPTION // 표현 어떻게 할껀지

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }
    private fun createNotification(
        type:NotificationType,
        title:String?,
        message:String?
    ): Notification {

        val intent = Intent(this, MainActivity::class.java)
            .apply {
                putExtra("notificationType","${type.title} 타입")
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
        val pendingIntent = PendingIntent.getActivity(this, type.id, intent, FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        when(type){
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE ->{
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            emoji
                        )
                )
            }
            NotificationType.CUSTOM -> {
                notificationBuilder
                    .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(
                        RemoteViews(
                            packageName,
                            R.layout.view_custom_notification
                        ).apply {
                            setTextViewText(R.id.title,title)
                            setTextViewText(R.id.message,message)
                        }
                    )
            }
        }
        return notificationBuilder.build()
    }
    companion object{
        private const val CHANNEL_NAME = "Emoji_party"
        private const val CHANNEL_DESCRIPTION = " Emoji Party 를 위한 채널"
        private const val CHANNEL_ID = "Channel Id"
        private const val emoji : String =
            "\uD83D\uDE00 \uD83D\uDE03 \uD83D\uDE04 \uD83D\uDE01" +
                    " \uD83D\uDE06 \uD83D\uDE05 \uD83D\uDE02 \uD83E\uDD23" +
                    " \uD83E\uDD72 \uD83D\uDE0A \uD83D\uDE07 \uD83D\uDE42" +
                    " \uD83D\uDE43 \uD83D\uDE09 \uD83D\uDE0C \uD83D\uDE0D" +
                    " \uD83E\uDD70 \uD83D\uDE18 \uD83D\uDE17 \uD83D\uDE19" +
                    " \uD83D\uDE1A \uD83D\uDE0B \uD83D\uDE1B \uD83D\uDE1D " +
                    " \uD83D\uDE1C \uD83E\uDD2A \uD83E\uDD28 \uD83E\uDDD0" +
                    " \uD83E\uDD13 \uD83D\uDE0E \uD83E\uDD78 \uD83E\uDD29" +
                    " \uD83E\uDD73 \uD83D\uDE0F \uD83D\uDE12 \uD83D\uDE1E"
    }
}