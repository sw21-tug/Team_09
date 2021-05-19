package com.tugraz.asd.modernnewsgroupapp

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowMessageThreadsBinding
import org.apache.commons.net.nntp.Article
import java.io.PrintStream
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentShowMessages : Fragment() {
    private lateinit var binding: FragmentShowMessageThreadsBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController
    lateinit var articles: Article

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowMessageThreadsBinding.inflate(layoutInflater)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")
        controller = viewModel.data.value!!

        println("The element at ${controller.currentServer.currentNewsgroup}")

        val thread = Thread {
            articles = controller.fetchArticles(controller.currentServer)!!
        }
        try {
            thread.start()
        } catch (e: Exception) {
            when(e) {
                is NewsgroupConnection.NewsgroupConnectionException -> {
                    // TODO: show error message
                    System.out.println("Error on Server connection: " + e.message)
                }
                else -> {
                    throw e
                }
            }
        }

        thread.join()
        showMessages(articles, 0)
       /* for(article in articles)
        {
            val textview = TextView(activity)
            textview.text = formatDate(article.date) + System.getProperty("line.separator") + article.subject
            textview.width = ViewGroup.LayoutParams.MATCH_PARENT
            textview.height = (100 * scale.toInt())
            textview.gravity = Gravity.CENTER or Gravity.LEFT
            textview.setPadding(50 * scale.toInt(), 0, 0, 0)
            textview.textSize = 16f
            textview.setTextColor(Color.DKGRAY)
            binding.viewShowMessages.addView(textview)
        }*/

        binding.buttonBack.setOnClickListener() {
            onButtonBackClick()
        }

        binding.buttonCreateThread.setOnClickListener() {
            onButtonCreateThreadClick()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")
        controller = viewModel.data.value!!

        if(controller.currentServer.currentNewsgroup!!.alias.isEmpty()){
            binding.headerText.setText(controller.currentServer.currentNewsgroup!!.name) }
        else {
            binding.headerText.setText(controller.currentServer.currentNewsgroup!!.alias)
        }
    }

    fun onButtonBackClick()
    {
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
        for (i in 0 until depth) {
            //ps.print("==>")
        }
        //ps.println(article.subject + "\t" + article.from + "\t" + article.articleId)
        val scale = getResources().getDisplayMetrics().density
        val textview = TextView(activity)
        textview.text = System.getProperty("line.separator") + article.subject
        textview.width = ViewGroup.LayoutParams.MATCH_PARENT
        textview.height = (100 * scale.toInt())
        textview.gravity = Gravity.CENTER or Gravity.LEFT
        textview.setPadding(1 + depth * 50 * scale.toInt(), 0, 0, 0)
        textview.textSize = 16f
        textview.setTextColor(Color.DKGRAY)
        binding.viewShowMessages.addView(textview)
        if (article.kid != null) {
            showMessages(article.kid, depth + 1)
        }
        if (article.next != null) {
            showMessages(article.next, depth)
        }
    }

}