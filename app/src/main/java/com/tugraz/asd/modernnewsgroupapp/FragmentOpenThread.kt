package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentOpenThreadBinding
import com.tugraz.asd.modernnewsgroupapp.helper.Feedback

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentOpenThread : Fragment() {

    private lateinit var binding: FragmentOpenThreadBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController

    private var messageThread: String? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOpenThreadBinding.inflate(layoutInflater)

        binding.buttonBack.setOnClickListener {
            onButtonBackClick()
        }

        viewModel = activity?.run {
            ViewModelProvider(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        controller = viewModel.controller.value!!

        getMessageThread(controller)

        binding.headerText.text = controller.currentArticle?.subject
        binding.tvMessageDate.text = controller.currentArticle?.date

        binding.tvThreadMessagesBody.text = messageThread

        return binding.root
    }

    private fun getMessageThread(controller: NewsgroupController)
    {
        val thread = Thread {
            messageThread = controller.currentServer?.let { controller.fetchCurrentArticleBody(it) }!!
        }
        try {
            thread.start()
        } catch (e: Exception) {
            when(e) {
                is NewsgroupConnection.NewsgroupConnectionException -> {
                    Feedback.showError(requireView(), getString(R.string.feedback_server_connection_error))
                    println("Error on Server connection: " + e.message)
                }
                else -> {
                    throw e
                }
            }
        }
        thread.join()
    }

    private fun onButtonBackClick() {
        controller.currentArticle = null
        findNavController().navigate(R.id.action_fragmentOpenThread_to_FragmentMessageThreads)
    }

    private fun onButtonReplyThreadClick() {
        //findNavController().navigate(R.id.action_FragmentMessageThreads_to_FragmentCreateThread)
    }
}