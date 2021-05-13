package com.tugraz.asd.modernnewsgroupapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowSubgroupsBinding
import com.tugraz.asd.modernnewsgroupapp.helper.SimpleSwipeCallback
import com.tugraz.asd.modernnewsgroupapp.helper.SubscribedListAdapter
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentShowSubgroups : Fragment() {

    private lateinit var viewModel: ServerObservable
    private lateinit var binding: FragmentShowSubgroupsBinding
    private lateinit var controller: NewsgroupController

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = activity?.run {
            ViewModelProvider(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        binding = FragmentShowSubgroupsBinding.inflate(layoutInflater)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            controller = viewModel.data.value!!
        })
        controller = viewModel.data.value!!


        val subscribedNewsgroups = controller.currentServer.newsGroups?.filter { newsgroup -> newsgroup.subscribed } as MutableList<Newsgroup>

        val recycleAdapter = SubscribedListAdapter(subscribedNewsgroups)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = recycleAdapter

        val itemTouchHelper = ItemTouchHelper(SimpleSwipeCallback(requireContext(), recycleAdapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.buttonAddSubgroups.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentSubscribe)
        }


        val list: MutableList<String> = ArrayList()
        var currentServerIndex = 0

        for ((key, _) in  controller.servers) {

            val newsgroupServer = if (key.alias.isEmpty()) {
                key.host
            } else {
                key.alias + " <" + key.host + ">"
            }

            list.add(newsgroupServer)

            if(key == viewModel.data.value!!.currentServer) {
                currentServerIndex = list.size -1
            }
        }

        val spinner : Spinner = binding.newsgroupsList
        val adapter: ArrayAdapter<Any?> = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item,
                list as List<Any?>
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Initializing an ArrayAdapter
        spinner.setSelection(currentServerIndex)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.button_edit_newsgroup).setOnClickListener {
            findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentEditNewsgroup)
        }

        view.findViewById<ImageButton>(R.id.button_show_profile).setOnClickListener {
            findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentProfile)
        }
    }

}