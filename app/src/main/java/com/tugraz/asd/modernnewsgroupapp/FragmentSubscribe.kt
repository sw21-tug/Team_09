package com.tugraz.asd.modernnewsgroupapp

import CustomExpandableListAdapter
import android.content.DialogInterface
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.ExpandableListData.data
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentAddNewsgroupBinding
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentSubscribeBinding
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import kotlinx.android.synthetic.main.fragment_subscribe.*
import java.util.ArrayList
import kotlinx.android.synthetic.main.fragment_subscribe.view.*
import kotlinx.android.synthetic.main.view_subscribe.view.*
import kotlinx.android.synthetic.main.group_list_item.view.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */


class FragmentSubscribe : Fragment() {
    private lateinit var binding: FragmentSubscribeBinding
    private lateinit var viewModel: ServerObseravble
    private val subgroupsList: MutableList<String> = ArrayList()
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

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

        expandableListView = inflater!!.inflate(R.layout.group_list_item, container, false) as ExpandableListView?
        binding.viewSubscribe.addView(expandableListView)
        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(this.requireContext(), titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                        this.requireContext(),
                        (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                        Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                        this.requireContext(),
                        (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                        Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                Toast.makeText(
                        this.requireContext(),
                        "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(
                                titleList as
                                        ArrayList<String>
                                )
                                [groupPosition]]!!.get(
                                childPosition
                        ),
                        Toast.LENGTH_SHORT
                ).show()
                false
            }
        }

        viewModel.data.observe(viewLifecycleOwner, Observer {
            for(newsgroup in viewModel.data.value?.newsGroups!!) {

                System.out.println("Adding NG: " + newsgroup.name)

                // if there are at least 2 dots make a checkBox within a spinner

                // Create a fucntion that counts the number of newsgroups whose name contains the same
                // name after the first dot
                //
                //

                if (newsgroup.name.contains("tu-graz.anzeigen")) {
                    // Create a drop-down of 2 (or more) subgroups
                    //subgroupsList.add(newsgroup.name)

                    if ( subgroupsList.size > 8) {
               //         createDropDownEntries(newsgroup.name, subgroupsList)
                    }
                }
                else
                {
                    // Create a checkbox of a subgroup
                 //   createListEntry(newsgroup.name, 10)
                }
            }
        })
        val view = binding.root
        return view
    }

    private val filterTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val checkboxes = binding.viewSubscribe.children

            for (box in checkboxes) {
                val c: CheckBox = box as CheckBox;

                if(!c.text.contains(s!!)) {
                    c.visibility = View.GONE;
                } else {
                    c.visibility = View.VISIBLE;
                }

            }
        }
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*view.findViewById<Button>(R.id.button_subscribe).setOnClickListener {
            findNavController().navigate(R.id.action_AddNewsgroup_to_Subscribe)
        }*/
    }

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

        binding.viewSubscribe.addView(check)
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