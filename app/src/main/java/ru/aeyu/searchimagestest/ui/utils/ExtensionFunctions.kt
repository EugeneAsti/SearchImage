package ru.aeyu.searchimagestest.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.aeyu.searchimagestest.R

fun ImageView.getImageFromRemote(
    context: Context,
    url: String,
    progressBarWidget: ProgressBar
) {
    Glide.with(context)
        .load(url)
        .listener(GlideRequestListener(progressBarWidget, this, context))
        .into(this)
}

class GlideRequestListener(
    private val progressBarWidget: ProgressBar,
    private val imageView: ImageView,
    private val context: Context
        ) : RequestListener<Drawable> {
    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        imageView.background =
            AppCompatResources.getDrawable(context, R.drawable.ic_image_not_loaded)
        progressBarWidget.isVisible = false
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        progressBarWidget.isVisible = false
        return false
    }
}
