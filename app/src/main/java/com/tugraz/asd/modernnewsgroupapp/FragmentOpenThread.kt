package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentOpenThreadBinding
import com.tugraz.asd.modernnewsgroupapp.helper.Feedback
import com.tugraz.asd.modernnewsgroupapp.helper.Helper
import com.tugraz.asd.modernnewsgroupapp.helper.ThreadMessagesAdapter
import org.apache.commons.net.nntp.Article

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentOpenThread : Fragment() {

    private lateinit var binding: FragmentOpenThreadBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController

    private var messageThread: String? = null

    private val header : MutableList<Article> = ArrayList()
    private val body : HashMap<String, String> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOpenThreadBinding.inflate(layoutInflater)

        binding.buttonBack.setOnClickListener {
            onButtonBackClick()
        }

        binding.buttonReplyThread.setOnClickListener() {
            onButtonReplyThreadClick()
        }



        viewModel = activity?.run {
            ViewModelProvider(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        controller = viewModel.controller.value!!

        getThreadMessages()

        binding.headerText.text = controller.currentArticle?.subject
        binding.tvMessageDate.text = Helper.formatDate(controller.currentArticle?.date)

        binding.tvThreadMessagesBody.text = messageThread

        return binding.root
    }

    private fun getThreadMessages()
    {
        val thread = Thread {
            val currentArticle = controller.currentArticle
            messageThread = controller.fetchCurrentArticleBody()

            if (currentArticle?.kid != null) {
                generateReplyMessages(currentArticle.kid)

                binding.expandableViewShowReplies.setAdapter(
                    ThreadMessagesAdapter(
                        requireActivity(),
                        header,
                        body,
                        viewModel
                    )
                )

            } else {
                binding.expandableViewShowReplies.isVisible = false
            }
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

    private fun generateReplyMessages(article: Article) {

        header.add(article)
        val articleMessage = controller.fetchArticleBodyById(article.articleNumberLong)
        if (articleMessage != null) {
            body[article.articleId] = articleMessage
        }

        if (article.kid != null) {
            generateReplyMessages(article.kid)
        }

        if (article.next != null) {
            generateReplyMessages(article.next)
        }
    }

    private fun onButtonBackClick() {
        controller.currentArticle = null
        findNavController().navigate(R.id.action_fragmentOpenThread_to_FragmentMessageThreads)
    }

    private fun onButtonReplyThreadClick() {
        controller.currentReplyArticle = controller.currentArticle
         findNavController().navigate(R.id.action_fragmentOpenThread_to_fragmentOpenReplyThread)
    }
}