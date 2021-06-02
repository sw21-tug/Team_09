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
import java.text.SimpleDateFormat

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

        getReplyThreadMessage(controller)

        binding.replyHeaderText.text = controller.currentReplyArticle?.subject
        binding.tvReplyMessageDate.text = formatDate(controller.currentReplyArticle?.date)

        binding.tvThreadReplyMessagesBody.text = messageThread

        return binding.root
    }

    private fun getReplyThreadMessage(controller: NewsgroupController)
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
        //findNavController().navigate(R.id.action_FragmentMessageThreads_to_FragmentCreateThread)
    }

    fun formatDate(date: String?): String {
        var output = ""
        if(!date.isNullOrEmpty()) {
            val dateShort = date?.substring(5, 25)
            val parser = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
            output = formatter.format(parser.parse(dateShort))
        }
        return output
    }
}