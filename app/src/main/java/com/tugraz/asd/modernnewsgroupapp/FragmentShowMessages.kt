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
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentShowMessageThreadsBinding
import org.apache.commons.net.nntp.Article

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentShowMessages : Fragment() {
    private lateinit var binding: FragmentShowMessageThreadsBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController
    private lateinit var articles: ArrayList<Article>

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
            articles = controller.fetchArtcles(controller.currentServer)!!
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
        val scale = getResources().getDisplayMetrics().density;
        for(article in articles)
        {
            val textview = TextView(activity)
            textview.text = article.subject
            textview.width = ViewGroup.LayoutParams.MATCH_PARENT
            textview.height = (80 * scale.toInt())
            textview.gravity = Gravity.CENTER or Gravity.LEFT
            textview.setPadding(50 * scale.toInt(), 0,0,0)
            textview.setTextColor(Color.DKGRAY)
            binding.viewShowMessageThreads.addView(textview)
        }

        //var con = controller.getConnection(controller.currentServer)
        //var messageThreads = con?.getMessages(controller.currentServer.currentNewsgroup)
        print("hi")

        binding.headerSubgroupName.text = controller.currentServer.currentNewsgroup!!.name

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}