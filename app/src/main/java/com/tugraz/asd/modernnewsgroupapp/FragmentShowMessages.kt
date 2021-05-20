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

    val header : MutableList<String> = ArrayList()
    val body : MutableList<MutableList<String>> = ArrayList()

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

        controller = viewModel.data.value!!

        if(controller.currentServer.currentNewsgroup!!.alias.isEmpty()){
            binding.headerText.setText(controller.currentServer.currentNewsgroup!!.name) }
        else {
            binding.headerText.setText(controller.currentServer.currentNewsgroup!!.alias)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        //TODO: Fill in correct data
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        showMessages(articles, 0)

        binding.buttonBack.setOnClickListener() {
            onButtonBackClick()
        }

        binding.buttonCreateThread.setOnClickListener() {
            onButtonCreateThreadClick()
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

        testList = ArrayList()
        var current_article : Article
        var article_temp = article

        do {
            current_article = article_temp
            if(article_temp.kid != null && depth == 0)
            {
                if(article_temp.from != null)
                    header.add(article_temp.from)
                while(article_temp.kid != null)
                {
                    testList.add(article_temp.kid.from)
                    article_temp = article_temp.kid
                }
                article_temp = current_article
            }
            body.add(testList)
            testList = ArrayList()
            article_temp = article_temp.next
        }
        while(article_temp.next != null)

        expandableView_show_messages.setAdapter(ExpandableListAdapter(requireActivity(), expandableView_show_messages, header, body))

        //ps.println(article.subject + "\t" + article.from + "\t" + article.articleId)
        /*val scale = getResources().getDisplayMetrics().density
        val textview = TextView(activity)
        textview.text = arrow + System.getProperty("line.separator") + article.subject
        textview.width = ViewGroup.LayoutParams.MATCH_PARENT
        textview.height = (100 * scale.toInt())
        textview.gravity = Gravity.CENTER or Gravity.LEFT
        textview.setPadding(1 + depth * 50 * scale.toInt(), 0, 0, 0)
        textview.textSize = 16f
        textview.setTextColor(Color.DKGRAY)
        binding.viewShowMessages.addView(textview)*/

    }

}