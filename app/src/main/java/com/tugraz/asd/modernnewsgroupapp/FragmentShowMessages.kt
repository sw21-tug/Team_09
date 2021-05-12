package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowMessageThreadsBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentShowMessages : Fragment() {
    private lateinit var binding: FragmentShowMessageThreadsBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        binding = FragmentShowMessageThreadsBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")
        controller = viewModel.data.value!!

        println("The element at ${controller.currentServer.currentNewsgroup}")
        var con = controller.getConnection(controller.currentServer)
        var messageThreads = con?.getMessages(controller.currentServer.currentNewsgroup)
        print("hi")

        binding.headerSubgroupName.text = controller.currentServer.currentNewsgroup!!.name

    }

}