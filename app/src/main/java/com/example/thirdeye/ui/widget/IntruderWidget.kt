package com.example.thirdeye.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews
import com.example.thirdeye.R
import kotlinx.coroutines.awaitAll


class IntruderWidget(): AppWidgetProvider() {
    companion object{
        fun updateWidgetDirect(context: Context,state:String,dataTime:String){
            val aWM= AppWidgetManager.getInstance(context)
            val ids=aWM.getAppWidgetIds(ComponentName(context, IntruderWidget::class.java))

            if (ids.isEmpty())return

            val views= RemoteViews(context.packageName, R.layout.intruder_widget)
            views.setTextViewText(R.id.tvIntrusionState,state)
            views.setTextViewText(R.id.dateTime,dataTime)

            for (id in ids){
                aWM.updateAppWidget(id,views)
            }


        }



    }
}