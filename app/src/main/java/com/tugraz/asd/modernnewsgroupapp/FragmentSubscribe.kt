package com.tugraz.asd.modernnewsgroupapp

import CustomExpandableListAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ExpandableListAdapter
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentSubscribeBinding
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */


class FragmentSubscribe : Fragment() {
    private lateinit var binding: FragmentSubscribeBinding
    private lateinit var viewModel: ServerObseravble

    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null
    private var expandableListItem = HashMap<String, List<String>>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSubscribeBinding.inflate(layoutInflater)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObseravble::class.java)
        } ?: throw Exception("Invalid Activity")

        binding.buttonFinish.setOnClickListener() {
            onButtonFinishClick()
        }

        binding.buttonBack.setOnClickListener() {
            onButtonBackClick()
        }

        binding.editTextGroupFilter.addTextChangedListener(filterTextWatcher);

        viewModel.data.observe(viewLifecycleOwner, Observer {
            for (newsgroup in viewModel.data.value?.newsGroups!!) {

                if (newsgroup.isSubgroup()) {
                    newsgroup.setParentNewsgroup()
                    if (viewModel.data.value?.newsGroups!!.any { it.name.equals(newsgroup.parent) }) {
                        addToExpandableList(newsgroup.parent!!, newsgroup.parent!!)
                    }
                    addToExpandableList(newsgroup.parent!!, newsgroup.name)
                } else {
                    // TODO: if it is not a subgroup
                    //addToExpandableList("No subgroups", newsgroup.name)
                    expandableListItem[newsgroup.name] = ArrayList()
                }
            }

            val expandableListView = binding.expandableListView
            titleList = ArrayList(expandableListItem.keys)
            this.adapter = CustomExpandableListAdapter(requireContext(), titleList as ArrayList<String>, expandableListItem)
            expandableListView.setAdapter(this.adapter)
        })

        return binding.root
    }

    private fun addToExpandableList(key: String, value: String) {
        if (expandableListItem.containsKey(key)) {
            val list = expandableListItem[key] as MutableList<String>
            list.add(value)
            expandableListItem[key] = list
        } else {
            val children: MutableList<String> = ArrayList()
            children.add(value)
            expandableListItem[key] = children
        }
    }

    // TODO: filter through expandable list
    private val filterTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            /*val checkboxes = binding.expandableListView.children

            for (box in checkboxes) {
                val c: CheckBox = box as CheckBox;

                if(!c.text.contains(s!!)) {
                    c.visibility = View.GONE;
                } else {
                    c.visibility = View.VISIBLE;
                }
            }*/

            //for (i in 0 until adapter!!.groupCount) binding.expandableListView.expandGroup(i)

            for (i in 0 until (binding.expandableListView as ViewGroup).childCount) {
                val nextChild = (binding.expandableListView as ViewGroup).getChildAt(i)
                if (nextChild is CheckBox) {
                    if (!nextChild.text.contains(s!!)) {
                        nextChild.visibility = View.GONE
                    } else {
                        nextChild.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    // TODO: get all checkboxes from expandable list
    fun onButtonFinishClick() {
        binding.viewSubscribe.forEach {
            val checkbox = it as CheckBox
            if(checkbox.isChecked) {
                val ng = viewModel.data.value?.newsGroups?.filter{ng -> checkbox.text == ng.name}
                if (ng != null) {
                    ng.elementAt(0).subscribed = true
                }
            }
        }
    }

    private fun onButtonBackClick() {
        findNavController().navigate(R.id.action_FragmentSubscribe_to_FragmentAddNewsgroup)
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_subscribe).setOnClickListener {
            findNavController().navigate(R.id.action_AddNewsgroup_to_Subscribe)
        }
    }*/

    private fun createListEntry(newsgroup: String, padding: Int) {
        val check = CheckBox(activity)
        check.text = newsgroup
        check.setPadding(padding, 10, 10, 10)
        val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(padding, 5, 5, 5)
        params.gravity = Gravity.NO_GRAVITY
        check.layoutParams = params
        check.gravity = Gravity.CENTER

        //binding.viewSubscribe.addView(check)
    }

    private fun createDropDownEntries(title: String, subgroups: MutableList<String>) {
        /*
        Create a Spinner/Dropdown of all subgroups.
        E.G.
        Expand lv and then we'll be able to see
        lv.analysis
        lv.algorithmen etc.
         */

//        val listView = ListView(activity)


    }
}