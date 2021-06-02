package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowMessageThreadsBinding
import kotlinx.android.synthetic.main.fragment_show_message_threads.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.net.nntp.Article
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentShowMessages : Fragment() {
    private lateinit var binding: FragmentShowMessageThreadsBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController

    val header : MutableList<String> = ArrayList()
    val body : MutableList<MutableList<String>> = ArrayList()
    var body_buffer : MutableList<String> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowMessageThreadsBinding.inflate(layoutInflater)
        viewModel = activity?.run {
            ViewModelProvider(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        viewModel.controller.observe(viewLifecycleOwner) {
            if(!::controller.isInitialized || controller.currentArticles == null) {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        viewModel.controller.value!!.fetchArticles()
                        controller = viewModel.controller.value!!
                        viewModel.controller.postValue(viewModel.controller.value)
                    }
                }
            } else {
                controller = viewModel.controller.value!!
                onControllerChange()
            }

        }



        return binding.root
    }

    private fun onControllerChange() {
        if(controller.currentNewsgroup!!.alias.isNullOrEmpty()){
            binding.headerText.setText(controller.currentNewsgroup!!.name) }
        else {
            binding.headerText.setText(controller.currentNewsgroup!!.alias)
        }

        controller.currentArticles?.let {
            showMessages(it, 0)
            body.add(body_buffer)
            body_buffer = ArrayList()
            body.removeFirst()
            expandableView_show_messages.setAdapter(ExpandableListAdapter(requireActivity(), expandableView_show_messages, header, body))


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener() {
            onButtonBackClick()
        }

        binding.buttonCreateThread.setOnClickListener() {
            onButtonCreateThreadClick()
        }
    }

    fun onButtonBackClick()
    {
        controller.currentNewsgroup = null
        controller.currentArticles = null
        findNavController().navigate(R.id.action_FragmentMessageThreads_to_FragmentShowSubgroups)
    }

    private fun onButtonCreateThreadClick() {
        findNavController().navigate(R.id.action_FragmentMessageThreads_to_FragmentCreateThread)
    }

    fun formatDate(date: String): String {
        val dateShort = date.substring(5, 25)
        val parser = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val output: String = formatter.format(parser.parse(dateShort))

        return output
    }

    fun showMessages(article: Article, depth: Int) {
        if(article.articleNumber > 0 && !(article.subject.startsWith("Re"))) {
            header.add(formatDate(article.date) + System.getProperty("line.separator") + article.subject)
            body.add(body_buffer)
            body_buffer = ArrayList()
        }

        if (article.kid != null) {
            if(article.kid.articleNumber > 0) {
                body_buffer.add(formatDate(article.kid.date) + System.getProperty("line.separator") + article.kid.subject)
            }
            showMessages(article.kid, depth + 1)
        }
        if (article.next != null) {
            showMessages(article.next, depth)
        }
    }

}