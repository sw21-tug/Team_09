package com.tugraz.asd.modernnewsgroupapp.helper

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.tugraz.asd.modernnewsgroupapp.R
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import kotlinx.android.synthetic.main.subgroup_list_entry.view.*

class SubscribedListAdapter(private val items: MutableList<Newsgroup>) : RecyclerView.Adapter<SubscribedListAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val newsgroup = items[position]
        if (!newsgroup.alias.isNullOrEmpty()) {
            holder.bind(newsgroup.alias + " <${newsgroup.name}>")
        } else {
            holder.bind(newsgroup.name)
        }
    }

    override fun getItemCount(): Int = items.size

    fun removeAt(position: Int, context: Context) {
        val ctx = context as Activity
        items[position].subscribed = false
        Feedback.showInfo(ctx.findViewById(R.id.recyclerView), "Unsubscribed from ${items[position].name}")
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun editAt(position: Int, context: Context) {
        showEditDialog(items[position], context)
    }

    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.subgroup_list_entry, parent, false)) {

        fun bind(name: String) = with(itemView) {
            tv_subgroup_name.text = name
        }
    }

    private fun showEditDialog(newsgroup: Newsgroup, context: Context)
    {
        val ctx = context as Activity
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Alias for ${newsgroup.name}")

        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        if (!newsgroup.alias.isNullOrEmpty()) {
            input.setText(newsgroup.alias.toString())
        }
        builder.setView(input)

        builder.setPositiveButton("Save") { _, _ ->
            newsgroup.alias = input.text.toString()
            this.notifyDataSetChanged()
            Feedback.showSuccess(ctx.findViewById(R.id.recyclerView), "Alias set for ${newsgroup.name}")
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            this.notifyDataSetChanged()
            dialog.cancel()
        }
        builder.show()
    }
}
