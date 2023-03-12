package com.veryfi.lens.w2.demo.helpers

import android.app.Activity
import android.content.res.Configuration
import androidx.core.content.ContextCompat
import com.veryfi.lens.w2.demo.R

object ThemeHelper {

    fun setBackgroundColorToStatusBar(activity: Activity) {
        val color = when (activity.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> R.color.md_theme_dark_background
            Configuration.UI_MODE_NIGHT_NO -> R.color.md_theme_light_background
            else -> R.color.md_theme_light_background
        }
        activity.window?.statusBarColor = ContextCompat.getColor(activity, color)
    }

    fun setSecondaryColorToStatusBar(activity: Activity) {
        val color = when (activity.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> R.color.md_theme_dark_secondaryContainer
            Configuration.UI_MODE_NIGHT_NO -> R.color.md_theme_light_secondaryContainer
            else -> R.color.md_theme_light_secondaryContainer
        }
        activity.window?.statusBarColor = ContextCompat.getColor(activity, color)
    }

    fun getPrimaryColor(activity: Activity): Int {
        return when (activity.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> R.color.md_theme_dark_background
            Configuration.UI_MODE_NIGHT_NO -> R.color.md_theme_light_background
            else -> R.color.md_theme_light_background
        }
    }
}