package com.appynitty.swachbharatabhiyanlibrary.utils;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.appynitty.swachbharatabhiyanlibrary.R;

public class NotificationCreator {

    private static final int NOTIFICATION_ID = 1094;
    public static final String CHANNEL_ID = "my_service";
    private static Notification notification;

    public static Notification getNotification(Context context) {

        if (notification == null) {
            Bitmap largeBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round);
            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(context.getString(R.string.app_notification_description))
                    .setSmallIcon(R.drawable.ic_noti_icon)
                    .setLargeIcon(largeBitmap)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(largeBitmap)
                            .bigLargeIcon(null))
                    .setColor(context.getResources().getColor(R.color.colorPrimary, context.getResources().newTheme()))
                    .setPriority(Notification.PRIORITY_MAX)
                    .build();
        }

        return notification;
    }

    public static int getNotificationId() {
        return NOTIFICATION_ID;
    }
}
