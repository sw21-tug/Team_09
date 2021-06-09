package com.tugraz.asd.modernnewsgroupapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowSubgroupsBinding
import com.tugraz.asd.modernnewsgroupapp.helper.SimpleSwipeCallback
import com.tugraz.asd.modernnewsgroupapp.helper.SubscribedListAdapter
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup
import kotlinx.coroutines.launch
import org.apache.commons.net.nntp.NNTPClient


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
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )

            val itemTouchHelper = ItemTouchHelper(
                SimpleSwipeCallback(
                    requireContext(),
                    recycleAdapter
                )
            )
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
            val adapter: ArrayAdapter<Any?> = ArrayAdapter(
                this.requireContext(), android.R.layout.simple_spinner_item,
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
            lifecycleScope.launch {
                controller.currentServer?.id?.let { controller.setCurrentServerDB(it, false) }
                controller.setCurrentServerDB(con.server.id, true)
                viewModel.controller.value!!.currentServer = con.server
                controller.loadNewsgroupsFromDB()
                println("NG loaded from server")
                onControllerChange()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Needed
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