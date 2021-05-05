package com.tugraz.asd.modernnewsgroupapp

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentSubscribeBinding
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */


class FragmentSubscribe : Fragment() {
    private lateinit var binding: FragmentSubscribeBinding
    private lateinit var viewModel: ServerObservable

    // use this list for subscribed newsgroups
    private var newsgroupList: ArrayList<Newsgroup>? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSubscribeBinding.inflate(layoutInflater)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        binding.buttonFinish.setOnClickListener() {
            onButtonFinishClick()
        }

        binding.buttonBack.setOnClickListener() {
            onButtonBackClick()
        }

        binding.editTextGroupFilter.addTextChangedListener(filterTextWatcher)

        viewModel.data.observe(viewLifecycleOwner, Observer {

            val newsgroupsToAdd = ArrayList<Newsgroup>()

            for ((key, value) in  viewModel.data.value!!.servers) {
                if (key.active) {
                    newsgroupList = key.newsGroups as ArrayList<Newsgroup>
                }
            }

            //newsgroupList = viewModel.data.value?.newsGroups!! as ArrayList<Newsgroup>
            for (newsgroup in newsgroupList!!) {

                if (newsgroup.isSubgroup()) {
                    newsgroup.setParentNewsgroup()
                    newsgroup.setHierarchyLevel()

                    // add parent newsgroup which does not exist (no subscribe possibility)
                    if (!newsgroupsToAdd.any{it.name == newsgroup.parent} && !newsgroupList!!.any {it.name == newsgroup.parent}) {
                        val new = Newsgroup(newsgroup.parent!!)
                        new.setParentNewsgroup()
                        new.setHierarchyLevel()
                        newsgroupsToAdd.add(new)
                    }
                } else {
                    // newsgroup without subgroups
                    newsgroup.setHierarchyLevel()
                }
            }
            newsgroupList!!.addAll(newsgroupsToAdd)
            createHierarchyView(binding.viewSubscribe, newsgroupList!!, 0)
        })

        return binding.root
    }

    private fun onButtonBackClick() {
        findNavController().navigate(R.id.action_FragmentSubscribe_to_FragmentAddNewsgroup2)
    }

    private fun createHierarchyView(parentLayout: LinearLayout, newsgroups: List<Newsgroup>, level: Int) {

        for (item in newsgroups.filter { it.hierarchyLevel == level }) {

            var layout = layoutInflater.inflate(R.layout.row_first, null)
            val listTitleTextView = layout!!.findViewById<TextView>(R.id.textViewName)
            listTitleTextView.setTypeface(null, Typeface.BOLD)
            listTitleTextView.text = item.name

            // recursive level down if newsgroup has subgroups
            if (newsgroupList!!.any { it.parent == item.name }) {
                val nextLevelLayout: LinearLayout = layout.findViewById(R.id.linear_scroll)
                nextLevelLayout.visibility = View.GONE

                // set onClick listener for expanding / reducing elements
                listTitleTextView.setOnClickListener {
                    if (nextLevelLayout.visibility == View.GONE)
                        nextLevelLayout.visibility = View.VISIBLE
                    else
                        nextLevelLayout.visibility = View.GONE
                }
                createHierarchyView(nextLevelLayout, newsgroupList!!.filter { it.parent == item.name }, level+1)
            } else {
                // use checkbox layout instead to subscribe / unsubscribe
                layout = layoutInflater.inflate(R.layout.checkbox_item, null)
                val checkbox = layout!!.findViewById<CheckBox>(R.id.checkBox)
                checkbox.text = item.name

                // mark checkbox as checked if already subscribed
                if (item.subscribed) {
                    checkbox.isChecked = true
                }

                // subscribe / unsubscribe newsgroup if checked / unchecked
                checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        newsgroupList!!.find { it.name == checkbox.text }?.subscribe()
                    } else {
                        newsgroupList!!.find { it.name == checkbox.text }?.unsubscribe()
                    }
                }
            }
            parentLayout.addView(layout)
        }
    }

    private val filterTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            recursiveNewsgroupFilter(binding.viewSubscribe, s)
        }
    }

    fun recursiveNewsgroupFilter(parent: LinearLayout, s: CharSequence?) {
        for (child in parent.children) {
            if (child is LinearLayout) {
                recursiveNewsgroupFilter(child, s)
                // get rid of newsgroups not matching filter text
                if (!parent.children.any{it is LinearLayout && it.visibility == View.VISIBLE}) {
                    parent.visibility = View.GONE
                } else {
                    parent.visibility = View.VISIBLE
                }
            } else {
                if (child is CheckBox) {
                    if(!child.text.contains(s!!)) {
                        parent.visibility = View.GONE
                    } else {
                        parent.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    /*fun collapseNewsgroups(parent: LinearLayout, avoidLevel: Boolean) {
        for (child in parent.children) {
            if (child is LinearLayout) {
                collapseNewsgroups(child, false)
                if (!avoidLevel) {
                    child.visibility = View.GONE
                }
            }
        }
    }*/

    fun onButtonFinishClick() {
        /*
         TODO use class attribute "newsgroupList" for further processing
         all of subscribed newsgroups are saved in there
         */

        /*binding.viewSubscribe.forEach {
            val checkbox = it as CheckBox
            if(checkbox.isChecked) {
                var newsgroupServer_ : NewsgroupServer? = null
                for ((key, value) in  viewModel.data.value!!.servers) {
                    if(key.active == true)
                    {
                        newsgroupServer_ = key
                    }
                }

                val ng = newsgroupServer_?.newsGroups?.filter{ ng -> checkbox.text == ng.name}
                if (ng != null) {
                    ng.elementAt(0).subscribed = true
                }
            }
        }*/
        findNavController().navigate(R.id.action_FragmentSubscribe_to_FragmentShowSubgroups)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}