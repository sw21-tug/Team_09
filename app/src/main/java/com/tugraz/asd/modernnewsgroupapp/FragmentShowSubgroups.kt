package com.tugraz.asd.modernnewsgroupapp

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowSubgroupsBinding

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
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        binding = FragmentShowSubgroupsBinding.inflate(layoutInflater)

        viewModel.controller.observe(viewLifecycleOwner, Observer {
            controller = viewModel.controller.value!!

            val subscribed_newsgroups = controller.currentNewsgroups.filter { newsgroup -> newsgroup.subscribed == true}
            val scale = getResources().getDisplayMetrics().density;

            for(ng in subscribed_newsgroups) {
                val textview = TextView(activity)
                val drawable = resources.getDrawable(R.drawable.border_top)
                textview.text = ng.name
                textview.width = ViewGroup.LayoutParams.MATCH_PARENT
                textview.height = (80 * scale.toInt())
                textview.gravity = Gravity.CENTER or Gravity.LEFT
                textview.setPadding(50 * scale.toInt(), 0,0,0)
                textview.setTextColor(Color.DKGRAY)
                textview.background = drawable
                textview.textSize = 20f
                textview.setTypeface(Typeface.DEFAULT_BOLD)

                binding.viewShowSubgroups.addView(textview)
            }

            binding.buttonAddSubgroups.setOnClickListener() {
                findNavController().navigate(R.id.action_FragmentShowSubgroups_to_FragmentSubscribe)
            }


            val list: MutableList<String> = ArrayList()
            var currentServerIndex = 0

            for ((key, _) in  controller.servers) {
                var newsgroupServer = ""
                if(key.alias.isEmpty())
                {
                    newsgroupServer = key.host.toString()
                }
                else
                {
                    newsgroupServer = key.alias.toString() + " <" + key.host.toString() + ">"
                }
                list.add(newsgroupServer)

                if(key == viewModel.controller.value!!.currentServer) {
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
        })


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