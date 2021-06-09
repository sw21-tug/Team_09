package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentOpenReplyThreadBinding
import com.tugraz.asd.modernnewsgroupapp.helper.Feedback
import com.tugraz.asd.modernnewsgroupapp.helper.Helper

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentOpenReplyThread : Fragment() {

    private lateinit var binding: FragmentOpenReplyThreadBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController

    private var messageThread: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOpenReplyThreadBinding.inflate(layoutInflater)

        binding.buttonBack.setOnClickListener {
            onButtonBackClick()
        }

        viewModel = activity?.run {
            ViewModelProvider(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        controller = viewModel.controller.value!!

        getReplyThreadMessage()

        binding.replyHeaderText.text = controller.currentReplyArticle?.subject
        binding.tvReplyMessageDate.text = Helper.formatDate(controller.currentReplyArticle?.date)

        binding.tvThreadReplyMessagesBody.text = messageThread

        return binding.root
    }

    private fun getReplyThreadMessage()
    {
        val thread = Thread {
            val currentReplyArticle = controller.currentReplyArticle
            messageThread = controller.currentServer?.let { controller.fetchArticleBodyById(it, currentReplyArticle!!.articleNumberLong) }
        }
        try {
            thread.start()
        } catch (e: Exception) {
            when(e) {
                is NewsgroupConnection.NewsgroupConnectionException -> {
                    Feedback.showError(
                        requireView(),
                        getString(R.string.feedback_server_connection_error)
                    )
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
        controller.currentReplyArticle = null
        findNavController().navigate(R.id.action_fragmentOpenReplyThread_to_fragmentOpenThread)
    }

    private fun onButtonReplyThreadClick() {
        // TODO call to reply to current reply thread
        //findNavController().navigate(R.id.action_FragmentMessageThreads_to_FragmentCreateThread)
    }
}