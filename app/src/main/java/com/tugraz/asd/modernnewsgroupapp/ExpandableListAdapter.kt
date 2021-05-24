package com.tugraz.asd.modernnewsgroupapp

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

class ExpandableListAdapter(var context: Context, var expandableListView: ExpandableListView, var header: MutableList<String>, var body: MutableList<MutableList<String>>) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return header.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body.get(groupPosition).size
    }

    override fun getGroup(groupPosition: Int): String {
        return header.get(groupPosition)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String {
        return body.get(groupPosition).get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if(convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_thread ,null)
        }
        val title = convertView?.findViewById<TextView>(R.id.thread_title)
        title?.text = getGroup(groupPosition)
        System.out.print(getChildrenCount(groupPosition))
        convertView?.findViewById<ImageView>(R.id.image_arrow)?.isVisible = false
        if(getChildrenCount(groupPosition) > 0) {
            convertView?.findViewById<ImageView>(R.id.image_arrow)?.isVisible = true
            convertView?.findViewById<ImageView>(R.id.image_arrow)?.setSelected(isExpanded)
            title?.setOnClickListener {
                if (expandableListView.isGroupExpanded(groupPosition))
                    expandableListView.collapseGroup(groupPosition)
                else
                    expandableListView.expandGroup(groupPosition)

            }
        }

        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
            if(convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = inflater.inflate(R.layout.layout_thread_child ,null)
            }
        val title = convertView?.findViewById<TextView>(R.id.thread_child)
        title?.text = getChild(groupPosition, childPosition)
        return convertView

    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}