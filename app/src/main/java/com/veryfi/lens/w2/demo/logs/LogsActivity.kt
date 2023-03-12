package com.veryfi.lens.w2.demo.logs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lriccardo.timelineview.TimelineDecorator
import com.veryfi.lens.VeryfiLens
import com.veryfi.lens.VeryfiLensDelegate
import com.veryfi.lens.w2.demo.R
import com.veryfi.lens.w2.demo.databinding.ActivityLogsBinding
import com.veryfi.lens.w2.demo.helpers.ThemeHelper
import org.json.JSONObject

class LogsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogsBinding
    private var logsEventData: ArrayList<Log> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ThemeHelper.setSecondaryColorToStatusBar(this)

        val adapter = LogsAdapter()
        setUpToolBar()
        setupLogsList(adapter)
        setUpVeryfiLensDelegate(adapter)
        VeryfiLens.showCamera()
    }

    private fun setUpToolBar() = with(binding.topAppBar) {
        setSupportActionBar(this)
        setNavigationIcon(R.drawable.ic_vector_close_shape)
        setNavigationOnClickListener { finish() }
    }

    private fun setupLogsList(logsAdapter: LogsAdapter) = with(binding.timelineRv) {
        layoutManager = LinearLayoutManager(this@LogsActivity, LinearLayoutManager.VERTICAL, false)
        adapter = logsAdapter
        val colorPrimary = ThemeHelper.getPrimaryColor(this@LogsActivity)
        addItemDecoration(
            TimelineDecorator(
                position = TimelineDecorator.Position.Left,
                indicatorColor = colorPrimary,
                lineColor = colorPrimary
            )
        )
    }

    private fun setUpVeryfiLensDelegate(adapter: LogsAdapter) {
        VeryfiLens.setDelegate(object : VeryfiLensDelegate {
            override fun veryfiLensClose(json: JSONObject) { showLogs(json, adapter) }
            override fun veryfiLensError(json: JSONObject) { showLogs(json, adapter) }
            override fun veryfiLensSuccess(json: JSONObject) { showLogs(json, adapter) }
            override fun veryfiLensUpdate(json: JSONObject) { showLogs(json, adapter) }
        })
    }

    private fun showLogs(json: JSONObject, adapter: LogsAdapter) {
        val status = if (json.has(STATUS)) json.getString(STATUS) else ""
        val title = when (status) {
            START -> getString(R.string.logs_start_uploading)
            IN_PROGRESS -> {
                when (json.getString(MSG)) {
                    IMG_THUMBNAIL -> getString(R.string.logs_thumbnail)
                    IMG_ORIGINAL -> getString(R.string.logs_original_image)
                    PROGRESS -> getString(R.string.logs_extraction)
                    else -> ""
                }
            }
            DONE -> getString(R.string.logs_result)
            REMOVED -> getString(R.string.logs_remove_cache_data)
            EXCEPTION -> getString(R.string.logs_exception)
            ERROR -> getString(R.string.logs_error)
            FAILED -> getString(R.string.logs_failed)
            else -> "Other"
        }

        if (status.equals(CLOSE).not()) {
            val log = Log(title = title, message = json)
            logsEventData.add(log)
            adapter.addItem(log)
            binding.timelineRv.scrollToPosition(adapter.itemCount - 1)
        } else {
            if (logsEventData.size == 0) finish()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Do nothing
    }

    companion object {
        const val STATUS = "status"
        const val START = "start"
        const val IN_PROGRESS = "inprogress"
        const val MSG = "msg"
        const val IMG_THUMBNAIL = "img_thumbnail_path"
        const val IMG_ORIGINAL = "img_original_path"
        const val PROGRESS = "progress"
        const val DONE = "done"
        const val REMOVED = "removed"
        const val EXCEPTION = "exception"
        const val ERROR = "error"
        const val FAILED = "failed"
        const val CLOSE = "close"
    }
}