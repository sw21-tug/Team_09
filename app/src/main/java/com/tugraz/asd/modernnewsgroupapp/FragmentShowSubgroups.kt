package com.tugraz.asd.modernnewsgroupapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowSubgroupsBinding
import com.tugraz.asd.modernnewsgroupapp.helper.SwipeAdapter
import com.tugraz.asd.modernnewsgroupapp.helper.SwipeToDeleteCallback

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentShowSubgroups : Fragment() {

    private lateinit var viewModel: ServerObservable
    private lateinit var ngs: NewsgroupController
    private lateinit var binding: FragmentShowSubgroupsBinding
    private lateinit var controller: NewsgroupController

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        binding = FragmentShowSubgroupsBinding.inflate(layoutInflater)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            controller = viewModel.data.value!!
        })

        controller = viewModel.data.value!!

        //val recyclerView = binding.recyclerView


        val subscribedNewsgroups = controller.currentServer.newsGroups?.filter { newsgroup -> newsgroup.subscribed }
        val newsgroupStrings: MutableList<String> = mutableListOf()

        //val scale = getResources().getDisplayMetrics().density;

        if (subscribedNewsgroups != null) {
            for(ng in subscribedNewsgroups) {

                newsgroupStrings.add(ng.name)

                /*val layout = layoutInflater.inflate(R.layout.subgroup_list_entry, null)
                val subgroupTextView = layout!!.findViewById<TextView>(R.id.tv_subgroup_name)
                subgroupTextView.text = ng.name

                binding.recyclerView.addView(layout)*/

                /*val textview = TextView(activity)
                val drawable = resources.getDrawable(R.drawable.border_top)
                textview.text = ng.name
                textview.width = ViewGroup.LayoutParams.MATCH_PARENT
                textview.height = (80 * scale.toInt())
                textview.gravity = Gravity.CENTER or Gravity.LEFT
                textview.setPadding(50 * scale.toInt(), 0, 0, 0)
                textview.setTextColor(Color.DKGRAY)
                textview.background = drawable
                textview.textSize = 20f
                textview.setTypeface(Typeface.DEFAULT_BOLD)

                binding.viewShowSubgroups.addView(textview)
                val view = inflater.inflate(R.layout.fragment_show_subgroups, container, false)
                textview.setOnClickListener() {
                    //FragmentEditNewsgroupBinding.inflate(layoutInflater)
                    val action = FragmentShowSubgroupsDirections.actionFragmentShowSubgroupsToFragmentEditNewsgroup(textview.text as String, textview.text as String )
                    Navigation.findNavController(requireView()).navigate(action)

                }*/
            }

            binding.recyclerView.layoutManager = LinearLayoutManager(context)

            binding.recyclerView.adapter = SwipeAdapter(newsgroupStrings)

            val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = binding.recyclerView.adapter as SwipeAdapter
                    adapter.removeAt(viewHolder.adapterPosition)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(binding.recyclerView)

            binding.buttonAddSubgroups.setOnClickListener() {
                findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentSubscribe)
            }
        }


        val list: MutableList<String> = ArrayList()
        var currentServerIndex = 0

        for ((key, _) in  controller.servers) {
            var newsgroupServer = ""
            if(key.alias?.isEmpty()!!)
            {
                newsgroupServer = key.host.toString()
            }
            else
            {
                newsgroupServer = key.alias.toString() + " <" + key.host.toString() + ">"
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