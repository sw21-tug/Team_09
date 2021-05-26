package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentCreateThreadBinding
import com.tugraz.asd.modernnewsgroupapp.helper.Feedback
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

        binding.buttonSend.setOnClickListener {
            onButtonSendClick()
        }
        viewModel = activity?.run {
            ViewModelProvider(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.controller.observe(viewLifecycleOwner) {
            controller = viewModel.controller.value!!
            onControllerChange()
        }
        binding.buttonCloseThread.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentCreateThread_to_FragmentMessageThreads)
        }
        return binding.root
    }

    fun onControllerChange() {
        if (controller.currentNewsgroup!!.alias.isNullOrEmpty()) {
            binding.headerCreateThread.setText(controller.currentNewsgroup!!.name)
        } else {
            binding.headerCreateThread.setText(controller.currentNewsgroup!!.alias)
        }
    }

    fun onButtonSendClick() {
        val subject = binding.threadSubject.text
        val text = binding.threadMessage.text

        if (subject.toString() == "" ) {
            Feedback.showError(this.requireView(), getString(R.string.feedback_missing_subject))
            return
        } else if (text.toString() == "") {
            Feedback.showError(this.requireView(), getString(R.string.feedback_missing_message))
            return
        }

        if(!controller.postArticle(subject.toString(), text.toString())) {
            Feedback.showError(this.requireView(), getString(R.string.feedback_send_fail))
            findNavController().navigate(R.id.action_FragmentCreateThread_to_FragmentMessageThreads)
        }
    }

}