package com.tugraz.asd.modernnewsgroupapp

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentAddNewsgroupBinding
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentSubscribeBinding
import com.tugraz.asd.modernnewsgroupapp.vo.Newsgroup


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentSubscribe : Fragment() {
    private lateinit var binding: FragmentSubscribeBinding
    private lateinit var viewModel: ServerObseravble

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

        viewModel.data.observe(viewLifecycleOwner, Observer {
            for(newsgroup in viewModel.data.value?.newsGroups!!) {

                System.out.println("Adding NG: " + newsgroup.name)

                val check = CheckBox(activity)
                check.text = newsgroup.name
                check.setPadding(10, 10, 10, 10)
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(5, 5, 5, 5)
                params.gravity = Gravity.NO_GRAVITY
                check.layoutParams = params
                check.gravity = Gravity.CENTER

                binding.viewSubscribe.addView(check)
            }
        })
        val view = binding.root
        return view
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*view.findViewById<Button>(R.id.button_subscribe).setOnClickListener {
            findNavController().navigate(R.id.action_AddNewsgroup_to_Subscribe)
        }*/
    }



}