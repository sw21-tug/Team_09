package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowMessageThreadsBinding
import kotlinx.android.synthetic.main.fragment_show_message_threads.*
import org.apache.commons.net.nntp.Article
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentShowMessages : Fragment() {
    private lateinit var binding: FragmentShowMessageThreadsBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController
    lateinit var articles: Article
    var testList : MutableList<String> = ArrayList()

    val header : MutableList<Article> = ArrayList()
    val body : MutableList<MutableList<Article>> = ArrayList()
    var body_buffer : MutableList<Article> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShowMessageThreadsBinding.inflate(layoutInflater)
        viewModel = activity?.run {
            ViewModelProvider(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")
        controller = viewModel.controller.value!!

        println("The element at ${controller.currentNewsgroup}")

        val thread = Thread {
            articles = controller.currentServer?.let { controller.fetchArticles(it) }!!
        }
        try {
            thread.start()
        } catch (e: Exception) {
            when(e) {
                is NewsgroupConnection.NewsgroupConnectionException -> {
                    // TODO: show error message
                    println("Error on Server connection: " + e.message)
                }
                else -> {
                    throw e
                }
            }
        }

        thread.join()

        controller = viewModel.controller.value!!

        if(controller.currentNewsgroup!!.alias.isNullOrEmpty()){
            binding.headerText.setText(controller.currentNewsgroup!!.name) }
        else {
            binding.headerText.setText(controller.currentNewsgroup!!.alias)
        }

        viewModel.controller.observe(viewLifecycleOwner, {
            controller = viewModel.controller.value!!
            //onControllerChange()
            if(controller.currentArticle != null)
            {
                findNavController().navigate(R.id.action_FragmentMessageThreads_to_fragmentOpenThread)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showMessages(articles, 0)

        body.add(body_buffer)
        body_buffer = ArrayList()
        body.removeFirst()
        expandableView_show_messages.setAdapter(ExpandableListAdapter(requireActivity(), expandableView_show_messages, header, body, viewModel))

        binding.buttonBack.setOnClickListener() {
            onButtonBackClick()
        }

        binding.buttonCreateThread.setOnClickListener() {
            onButtonCreateThreadClick()
        }
    }

    private fun onButtonBackClick() {
        controller.currentNewsgroup = null
        findNavController().navigate(R.id.action_FragmentMessageThreads_to_FragmentShowSubgroups)
    }

    private fun onButtonCreateThreadClick() {
        findNavController().navigate(R.id.action_FragmentMessageThreads_to_FragmentCreateThread)
    }

    fun showMessages(article: Article, depth: Int) {
        if(article.articleNumber > 0 && !(article.subject.startsWith("Re"))) {
            header.add(article)
            body.add(body_buffer)
            body_buffer = ArrayList()
        }

        if (article.kid != null) {
            if(article.kid.articleNumber > 0) {
                body_buffer.add(article.kid)
            }
            showMessages(article.kid, depth + 1)
        }
        if (article.next != null) {
            showMessages(article.next, depth)
        }
    }

}