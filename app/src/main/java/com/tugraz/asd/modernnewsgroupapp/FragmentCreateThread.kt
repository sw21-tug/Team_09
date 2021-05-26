package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentCreateThreadBinding
import org.apache.commons.net.nntp.Article

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentCreateThread : Fragment() {
    private lateinit var binding: FragmentCreateThreadBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController
    private lateinit var articles: ArrayList<Article>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateThreadBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")
        controller = viewModel.controller.value!!

        if (controller.currentNewsgroup!!.alias.isNullOrEmpty()) {
            binding.headerCreateThread.setText(controller.currentNewsgroup!!.name)
        } else {
            binding.headerCreateThread.setText(controller.currentNewsgroup!!.alias)
        }
    }

}