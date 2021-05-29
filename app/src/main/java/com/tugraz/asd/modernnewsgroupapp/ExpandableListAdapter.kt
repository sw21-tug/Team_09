package com.tugraz.asd.modernnewsgroupapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import org.apache.commons.net.nntp.Article
import java.text.SimpleDateFormat

class ExpandableListAdapter(var context: Context,
                            private var expandableListView: ExpandableListView,
                            private var header: MutableList<Article>,
                            private var body: MutableList<MutableList<Article>>,
                            private val viewModel: ServerObservable) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return header.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): String {
        val article = header[groupPosition]
        return formatDate(article.date) + System.getProperty("line.separator") + article.subject
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String {
        val article = body[groupPosition][childPosition]
        return formatDate(article.date) + System.getProperty("line.separator") + article.subject
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
        convertView?.findViewById<ImageView>(R.id.image_arrow)?.isVisible = false
        if(getChildrenCount(groupPosition) > 0) {
            convertView?.findViewById<ImageView>(R.id.image_arrow)?.isVisible = true
            convertView?.findViewById<ImageView>(R.id.image_arrow)?.setSelected(isExpanded)

            // onClick listener on whole list entry element to expand / collapse
            convertView?.setOnClickListener {
                if (expandableListView.isGroupExpanded(groupPosition))
                    expandableListView.collapseGroup(groupPosition)
                else
                    expandableListView.expandGroup(groupPosition)

            }
        }
        // onClick listener on list entry name to show thread in own fragment
        title?.setOnClickListener {
            val controller = viewModel.controller.value!!
            controller.currentArticle = header[groupPosition]
            viewModel.controller.postValue(controller)
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

        // onClick listener on list entry name to show thread in own fragment
        title?.setOnClickListener {
            val controller = viewModel.controller.value!!
            controller.currentArticle = body[groupPosition][childPosition]
            viewModel.controller.postValue(controller)
        }

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    fun formatDate(date: String): String {
        val dateShort = date.substring(5, 25)
        val parser = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val output: String = formatter.format(parser.parse(dateShort))

        return output
    }
}