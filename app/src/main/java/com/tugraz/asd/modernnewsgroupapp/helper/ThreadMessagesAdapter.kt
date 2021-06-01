package com.tugraz.asd.modernnewsgroupapp.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.tugraz.asd.modernnewsgroupapp.R
import org.apache.commons.net.nntp.Article

class ThreadMessagesAdapter(
    var context: Context,
    private var expandableListView: ExpandableListView,
    private var header: MutableList<Article>,
    private var body: HashMap<String, String>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return header.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroup(groupPosition: Int): String {
        val article = header[groupPosition]
        return article.subject
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String {
        return body[header[groupPosition].articleId].toString()
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

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        var threadHeaderView = convertView
        if(threadHeaderView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            threadHeaderView = inflater.inflate(R.layout.layout_thread, null)
        }
        val title = threadHeaderView?.findViewById<TextView>(R.id.thread_title)
        title?.text = getGroup(groupPosition)
        threadHeaderView?.findViewById<ImageView>(R.id.image_arrow)?.isVisible = false

        if (getChildrenCount(groupPosition) > 0) {

            threadHeaderView?.findViewById<ImageView>(R.id.image_arrow)?.isVisible = true
            threadHeaderView?.findViewById<ImageView>(R.id.image_arrow)?.setSelected(isExpanded)

            threadHeaderView?.setOnClickListener {
                if (expandableListView.isGroupExpanded(groupPosition)) {
                    expandableListView.collapseGroup(groupPosition)
                } else {
                    expandableListView.expandGroup(groupPosition)
                }
            }
        }

        return threadHeaderView!!
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        var threadBodyView = convertView
        if(threadBodyView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            threadBodyView = inflater.inflate(R.layout.thread_message_layout, null)
        }
        val messageBody = threadBodyView?.findViewById<TextView>(R.id.tv_thread_message)
        messageBody?.text = getChild(groupPosition, childPosition)

        return threadBodyView!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }
}