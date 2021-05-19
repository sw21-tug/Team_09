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
import org.apache.commons.net.nntp.Threadable
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentShowMessages : Fragment() {
    private lateinit var binding: FragmentShowMessageThreadsBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController
    private lateinit var articles: Threadable

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
        val scale = getResources().getDisplayMetrics().density

        /*for(article in articles.reversed())
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
        }
        */
        binding.buttonBack.setOnClickListener() {
            onButtonBackClick()
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
            binding.headerText.setText(controller.currentServer.currentNewsgroup!!.name) }else {
            binding.headerText.setText(controller.currentServer.currentNewsgroup!!.alias)
            binding.headerSubgroupName.setText(controller.currentServer.currentNewsgroup!!.name)
        }
    }

    fun onButtonBackClick()
    {
        findNavController().navigate(R.id.action_FragmentMessageThreads_to_FragmentShowSubgroups)
    }

    fun formatDate(date: String): String {
        val dateShort = date.substring(5, 25)
        val parser = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val output: String = formatter.format(parser.parse(dateShort))

        return output
    }

}