package com.tugraz.asd.modernnewsgroupapp.helper

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.tugraz.asd.modernnewsgroupapp.R
import com.tugraz.asd.modernnewsgroupapp.ServerObservable
import org.apache.commons.net.nntp.Article

class ThreadMessagesAdapter(
    var context: Context,
    private var header: MutableList<Article>,
    private var body: HashMap<String, String>,
    private var viewModel: ServerObservable
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
            threadHeaderView = inflater.inflate(R.layout.layout_thread_child, null)
        }
        val title = threadHeaderView?.findViewById<TextView>(R.id.thread_child)
        title?.text = getGroup(groupPosition)
        title?.setTypeface(null, Typeface.BOLD)
        //threadHeaderView?.findViewById<ImageView>(R.id.image_arrow)?.isVisible = false

        threadHeaderView?.setOnClickListener {
            val controller = viewModel.controller.value!!
            controller.currentReplyArticle = header[groupPosition]
            viewModel.controller.postValue(controller)
            val navigation = Navigation.findNavController(threadHeaderView)
            navigation.navigate(R.id.action_fragmentOpenThread_to_fragmentOpenReplyThread)
        }

        /*if (getChildrenCount(groupPosition) > 0) {

            threadHeaderView?.findViewById<ImageView>(R.id.image_arrow)?.isVisible = true
            threadHeaderView?.findViewById<ImageView>(R.id.image_arrow)?.setSelected(isExpanded)

            threadHeaderView?.setOnClickListener {
                if (expandableListView.isGroupExpanded(groupPosition)) {
                    expandableListView.collapseGroup(groupPosition)
                } else {
                    expandableListView.expandGroup(groupPosition)
                }
            }
        }*/

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