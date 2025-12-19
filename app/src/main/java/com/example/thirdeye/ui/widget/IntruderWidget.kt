package com.example.thirdeye.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import com.example.thirdeye.R
import kotlinx.coroutines.awaitAll
import java.io.File


class IntruderWidget : AppWidgetProvider() {

    companion object {
        fun updateWidgetDirect(context: Context, state: String, dataTime: String, isLocked: Boolean = false) {
            val awm = AppWidgetManager.getInstance(context)
            val ids = awm.getAppWidgetIds(ComponentName(context, IntruderWidget::class.java))

            val views = RemoteViews(context.packageName, R.layout.intruder_widget)
            views.setTextViewText(R.id.tvIntrusionState, state)
            views.setTextViewText(R.id.tvLastIntrusionTime, dataTime)

            val file = File(context.cacheDir, "widget_intruder.png")
            if (file.exists()) {
                val bitmap = if (isLocked) {

                    BitmapFactory.decodeResource(context.resources, R.drawable.emptyicon)
                } else {
                    BitmapFactory.decodeFile(file.absolutePath)
                }
                views.setImageViewBitmap(R.id.ivIntruder, bitmap)
            }

            for (id in ids) {
                awm.updateAppWidget(id, views)
            }
        }
    }
}
