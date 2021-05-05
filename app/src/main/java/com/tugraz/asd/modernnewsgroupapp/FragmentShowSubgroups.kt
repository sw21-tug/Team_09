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
import androidx.fragment.app.Fragment
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

        var newsgroupServer_ : NewsgroupServer? = null
        for ((key, value) in  viewModel.data.value!!.servers) {
            if(key.active == true)
            {
                newsgroupServer_ = key
            }
        }

        val subscribed_newsgroups = newsgroupServer_?.newsGroups?.filter { newsgroup -> newsgroup.subscribed == true}
        val scale = getResources().getDisplayMetrics().density;

        if (subscribed_newsgroups != null) {
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
        }

        // TODO Make a list



        var ngArray = arrayOfNulls<String>(viewModel.data.value!!.servers.size)
        val list: MutableList<String> = ArrayList()
        for ((key, value) in  viewModel.data.value!!.servers) {
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
        }



        val spinner : Spinner = binding.newsgroupsList


        val adapter: ArrayAdapter<Any?> = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item,
            list as List<Any?>
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        // Initializing an ArrayAdapter
        spinner.setSelection(0)
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