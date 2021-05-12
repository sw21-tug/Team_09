package com.tugraz.asd.modernnewsgroupapp.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugraz.asd.modernnewsgroupapp.R
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import kotlinx.android.synthetic.main.subgroup_list_entry.view.*

class SwipeAdapter(private val items: MutableList<Newsgroup>) : RecyclerView.Adapter<SwipeAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position].name)
    }

    override fun getItemCount(): Int = items.size

    /*fun addItem(name: String) {
        items.add(name)
        notifyItemInserted(items.size)
    }*/

    fun removeAt(position: Int) {
        items[position].subscribed = false
        items.removeAt(position)
        notifyItemRemoved(position)

    }



    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.subgroup_list_entry, parent, false)) {

        fun bind(name: String) = with(itemView) {
            tv_subgroup_name.text = name
        }
    }
}
