package com.tugraz.asd.modernnewsgroupapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowSubgroupsBinding
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer

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

        val subscribed_newsgroups = controller.currentServer?.newsGroups?.filter { newsgroup -> newsgroup.subscribed == true}
        val scale = getResources().getDisplayMetrics().density;

        if (subscribed_newsgroups != null) {
            for ((index, ng) in subscribed_newsgroups.withIndex()) {
                val textview = TextView(activity)
                textview.text = ng.name
                textview.width = ViewGroup.LayoutParams.MATCH_PARENT
                textview.height = (100 * scale.toInt())
                textview.gravity = Gravity.CENTER or Gravity.LEFT
                textview.setPadding(50 * scale.toInt(), 0,0,0)
                textview.setTextColor(Color.DKGRAY)
                textview.textSize = 18f
                textview.setTypeface(Typeface.DEFAULT_BOLD)
                textview.setOnClickListener{
                    controller.currentServer.currentNewsgroup = ng
                    findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentMessageThreads)
                }
                binding.viewShowSubgroups.addView(textview)
            }

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