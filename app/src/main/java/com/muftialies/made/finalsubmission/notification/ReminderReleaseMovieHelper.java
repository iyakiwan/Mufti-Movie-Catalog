package com.muftialies.made.finalsubmission.notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.muftialies.made.finalsubmission.BuildConfig;
import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.activity.DetailActivity;
import com.muftialies.made.finalsubmission.activity.MainActivity;
import com.muftialies.made.finalsubmission.model.ShowItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ReminderReleaseMovieHelper extends BroadcastReceiver {
    private final int ID_REMINDER = 105;
    private final static String GROUP_KEY = "group_key_emails";
    private ArrayList<ShowItems> resultItemMovie;

    @Override
    public void onReceive(final Context context, Intent intent) {
        resultReleaseMovieToday(new ResultCallback() {
            @Override
            public void onSuccess(ArrayList<ShowItems> showItems) {
                resultItemMovie = showItems;
                showReminderNotification(context);
            }

            @Override
            public void onFailure(boolean answer) {
                if (answer) {
                    Toast.makeText(context, context.getString(R.string.failure_data), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showReminderNotification(Context context) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Release Reminder";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder;
        Notification newMessageNotification1 = new Notification(), newMessageNotification2 = new Notification();

        int emailNotificationId1 = Integer.parseInt(resultItemMovie.get(0).getId());
        int emailNotificationId2 = Integer.parseInt(resultItemMovie.get(1).getId());

        if (resultItemMovie.size() == 1) {
            Intent intentDetail = new Intent(context, DetailActivity.class);
            intentDetail.putExtra(DetailActivity.EXTRA_ID, resultItemMovie.get(0).getId());
            intentDetail.putExtra(DetailActivity.EXTRA_TITLE, resultItemMovie.get(0).getTitle());
            intentDetail.putExtra(DetailActivity.EXTRA_SHOW, "movie");

            PendingIntent pendingIntentDetail = TaskStackBuilder.create(context)
                    .addNextIntent(intentDetail)
                    .getPendingIntent(111, PendingIntent.FLAG_UPDATE_CURRENT);

            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_date_range_black_24dp)
                    .setContentTitle(resultItemMovie.get(0).getTitle())
                    .setContentText(resultItemMovie.get(0).getOverview())
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound)
                    .setGroup(GROUP_KEY)
                    .setContentIntent(pendingIntentDetail)
                    .setAutoCancel(true);

        } else if (resultItemMovie.size() > 1) {
            Intent intentDetail1 = new Intent(context, DetailActivity.class);
            intentDetail1.putExtra(DetailActivity.EXTRA_ID, resultItemMovie.get(0).getId());
            intentDetail1.putExtra(DetailActivity.EXTRA_TITLE, resultItemMovie.get(0).getTitle());
            intentDetail1.putExtra(DetailActivity.EXTRA_SHOW, "movie");

            PendingIntent pendingIntentDetail1 = TaskStackBuilder.create(context)
                    .addNextIntent(intentDetail1)
                    .getPendingIntent(112, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent intentDetail2 = new Intent(context, DetailActivity.class);
            intentDetail2.putExtra(DetailActivity.EXTRA_ID, resultItemMovie.get(1).getId());
            intentDetail2.putExtra(DetailActivity.EXTRA_TITLE, resultItemMovie.get(1).getTitle());
            intentDetail2.putExtra(DetailActivity.EXTRA_SHOW, "movie");

            PendingIntent pendingIntentDetail2 = TaskStackBuilder.create(context)
                    .addNextIntent(intentDetail2)
                    .getPendingIntent(113, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine(resultItemMovie.get(0).getTitle())
                    .addLine(resultItemMovie.get(1).getTitle())
                    .setBigContentTitle("New Movie")
                    .setSummaryText("Movie Release Today");

            newMessageNotification1 = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_date_range_black_24dp)
                    .setContentTitle(resultItemMovie.get(0).getTitle())
                    .setContentText(resultItemMovie.get(0).getOverview())
                    .setChannelId(CHANNEL_ID)
                    .setGroup(GROUP_KEY)
                    .setContentIntent(pendingIntentDetail1)
                    .setAutoCancel(true)
                    .build();

            newMessageNotification2 = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_date_range_black_24dp)
                    .setContentTitle(resultItemMovie.get(1).getTitle())
                    .setContentText(resultItemMovie.get(1).getOverview())
                    .setChannelId(CHANNEL_ID)
                    .setGroup(GROUP_KEY)
                    .setContentIntent(pendingIntentDetail2)
                    .setAutoCancel(true)
                    .build();


            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("New Movie")
                    .setContentText("Movie Release Today")
                    .setSmallIcon(R.drawable.ic_date_range_black_24dp)
                    .setGroup(GROUP_KEY)
                    .setGroupSummary(true)
                    .setStyle(inboxStyle)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound);

        } else {
            Intent intentMain = new Intent(context, MainActivity.class);

            PendingIntent pendingIntentMain = TaskStackBuilder.create(context)
                    .addNextIntent(intentMain)
                    .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT);

            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_date_range_black_24dp)
                    .setContentTitle(context.getString(R.string.title_empty))
                    .setContentText(context.getString(R.string.desc_empty))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound)
                    .setGroup(GROUP_KEY)
                    .setContentIntent(pendingIntentMain)
                    .setAutoCancel(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            if (resultItemMovie.size() > 1) {
                notificationManagerCompat.notify(emailNotificationId1, newMessageNotification1);
                notificationManagerCompat.notify(emailNotificationId2, newMessageNotification2);
            }
            notificationManagerCompat.notify(ID_REMINDER, notification);
        }
    }

    public void setReminderReleaseMovie(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReleaseMovieHelper.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REMINDER, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, context.getString(R.string.set_reminder_release), Toast.LENGTH_SHORT).show();
    }

    public void cancelReminderReleaseMovie(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReleaseMovieHelper.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REMINDER, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, context.getString(R.string.cancel_reminder_release), Toast.LENGTH_SHORT).show();
    }

    private void resultReleaseMovieToday(final ResultCallback callback) {
        String API_KEY = BuildConfig.TMDB_API_KEY;
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateToday = sdf.format(date);

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ShowItems> releaseMovieToday = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US&primary_release_date.gte=" + dateToday + "&primary_release_date.lte=" + dateToday;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    if (responseObject.getString("total_results").equals("0")) {
                        releaseMovieToday.clear();
                    } else {
                        JSONArray list = responseObject.getJSONArray("results");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject jsonObject = list.getJSONObject(i);
                            ShowItems showItems = new ShowItems(jsonObject, "movie");
                            releaseMovieToday.add(showItems);
                        }
                    }
                    callback.onSuccess(releaseMovieToday);
                    callback.onFailure(false);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
                releaseMovieToday.clear();
                callback.onSuccess(releaseMovieToday);
                callback.onFailure(true);
            }
        });
    }

}


