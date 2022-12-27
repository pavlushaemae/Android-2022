package com.itis.androidcourse.recycler.adapter

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecorator(
    context: Context,
    spacingDp: Float
) : RecyclerView.ItemDecoration() {

    private val spacingPx: Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        spacingDp,
        context.resources.displayMetrics
    ).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = spacingPx
        outRect.left = spacingPx
        outRect.right = spacingPx
    }
}