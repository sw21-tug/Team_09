package com.tugraz.asd.modernnewsgroupapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowSubgroupsBinding
import com.tugraz.asd.modernnewsgroupapp.helper.Feedback
import com.tugraz.asd.modernnewsgroupapp.helper.SimpleSwipeCallback
import com.tugraz.asd.modernnewsgroupapp.helper.SubscribedListAdapter
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentShowSubgroups : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: ServerObservable
    private lateinit var binding: FragmentShowSubgroupsBinding
    private lateinit var controller: NewsgroupController

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = activity?.run {
            ViewModelProvider(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        binding = FragmentShowSubgroupsBinding.inflate(layoutInflater)

        viewModel.controller.observe(viewLifecycleOwner, {
            controller = viewModel.controller.value!!
            onControllerChange()
        })

        return binding.root
    }

    private fun onControllerChange() {
        if(controller.currentNewsgroup != null)
        {
            findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentMessageThreads)
        }
        if(controller.isCurrentNewsgroupsInitialised()) {
            val subscribedNewsgroups = controller.currentNewsgroups.filter { newsgroup -> newsgroup.subscribed } as MutableList<Newsgroup>

            val recycleAdapter = SubscribedListAdapter(subscribedNewsgroups, viewModel)
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = recycleAdapter

            val itemTouchHelper = ItemTouchHelper(SimpleSwipeCallback(requireContext(), recycleAdapter))
            itemTouchHelper.attachToRecyclerView(binding.recyclerView)

            binding.buttonAddSubgroups.setOnClickListener {
                findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentSubscribe)
            }


            val list: MutableList<String> = ArrayList()
            var currentServerIndex = 0

            for ((key, _) in controller.servers) {

                val newsgroupServer = if (key.alias.isEmpty()) {
                    key.host
                } else {
                    key.alias + " <" + key.host + ">"
                }

                list.add(newsgroupServer)

                if (key == viewModel.controller.value!!.currentServer) {
                    currentServerIndex = list.size - 1
                }
            }
            val spinner: Spinner = binding.newsgroupsList
            val adapter: ArrayAdapter<Any?> = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item,
                list as List<Any?>
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            // Initializing an ArrayAdapter
            spinner.setSelection(currentServerIndex)
            spinner.onItemSelectedListener = this


        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

       val con_list : ArrayList<NewsgroupConnection> = ArrayList(viewModel.controller.value!!.servers.values)
        val con = con_list[position]

        if(viewModel.controller.value!!.currentServer != con.server)
        {
            val thread = Thread {
                lifecycleScope.launch {
                    viewModel.controller.value!!.currentServer = con.server
                    controller.loadNewsgroupsFromDB()
                    println("NG loaded from server")
                    onControllerChange()

                }
            }

            try {
                thread.start()
            } catch (e: Exception) {
                when(e) {
                    is NewsgroupConnection.NewsgroupConnectionException -> {
                        Feedback.showError(this.requireView(), getString(R.string.feedback_server_connection_error))
                        println("Error on Server connection: " + e.message)
                        return
                    }
                    else -> {
                        throw e
                    }
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.button_edit_newsgroup).setOnClickListener {
            findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentEditNewsgroup)
        }

        view.findViewById<ImageButton>(R.id.button_show_profile).setOnClickListener {
            findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentProfile)
        }

        view.findViewById<ImageButton>(R.id.button_add_server).setOnClickListener {
            findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentAddNewsgroup)
        }
    }



}