package com.muftialies.made.finalsubmission.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.activity.FavoriteDetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteMovieWidget extends AppWidgetProvider {
    private static final String TOAST_ACTION = "com.muftialies.final.TOAST_ACTION";
    public static final String EXTRA_ITEM_1 = "com.muftialies.final.EXTRA_ITEM1";
    public static final String EXTRA_ITEM_2 = "com.muftialies.final.EXTRA_ITEM2";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_movie_widget);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.emp_view);

        Intent toastIntent = new Intent(context, FavoriteMovieWidget.class);
        toastIntent.setAction(FavoriteMovieWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                String  viewId = intent.getStringExtra(EXTRA_ITEM_1);
                String  viewName = intent.getStringExtra(EXTRA_ITEM_2);
                Intent detailFavorite = new Intent(context, FavoriteDetailActivity.class);
                detailFavorite.putExtra(FavoriteDetailActivity.EXTRA_ID, viewId);
                detailFavorite.putExtra(FavoriteDetailActivity.EXTRA_TITLE, viewName);
                detailFavorite.putExtra(FavoriteDetailActivity.EXTRA_SHOW, "movie");
                detailFavorite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detailFavorite);
            }
        }
    }

}

