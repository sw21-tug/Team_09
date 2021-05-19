package com.tugraz.asd.modernnewsgroupapp.helper

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tugraz.asd.modernnewsgroupapp.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class SimpleSwipeCallback(private val context: Context, private val adapter: SubscribedListAdapter)
    : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        if (direction == ItemTouchHelper.LEFT) {
            adapter.removeAt(viewHolder.adapterPosition, context)
        } else if (direction == ItemTouchHelper.RIGHT) {
            adapter.editAt(viewHolder.adapterPosition, context)
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(getColor(context, R.color.ERROR_RED))
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeRightBackgroundColor(getColor(context, R.color.SUCCESS_GREEN))
                .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                .create()
                .decorate()

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}