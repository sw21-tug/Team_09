package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentAddNewsgroupBinding
import com.tugraz.asd.modernnewsgroupapp.helper.Feedback
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentAddNewsgroup : Fragment() {

    private lateinit var binding: FragmentAddNewsgroupBinding
    private lateinit var viewModel: ServerObservable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = activity?.run {
            ViewModelProvider(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")


        if((activity as? MainActivity)?.skipSetup!!) {
            (activity as? MainActivity)?.skipSetup = false
            findNavController().navigate(R.id.action_FragmentAddNewsgroup_to_FragmentShowSubgroups)
        }

        // Inflate the layout for this fragment
        binding = FragmentAddNewsgroupBinding.inflate(layoutInflater)
        binding.buttonSubscribe.setOnClickListener {
            onButtonSubscribeClick()
        }
        return binding.root
    }

    private fun onButtonSubscribeClick() {
        val email = binding.editTextEmail.text
        val name = binding.editTextName
        val hostname = binding.editTextNewsgroupServer.text
        val serverAlias = binding.editTextServerAlias.text

        // check if user provided valid name and email address for newsgroup server subscription
        if (name.text.toString() == "" && !isValidEmail(email)) {
            Feedback.showError(this.requireView(), getString(R.string.feedback_wrong_name_email))
            return
        } else if (name.text.toString() == "") {
            Feedback.showError(this.requireView(), getString(R.string.feedback_wrong_name))
            return
        } else if (!isValidEmail(email)) {
            Feedback.showError(this.requireView(), getString(R.string.feedback_wrong_email))
            return
        }

        val controller = viewModel.controller.value

        if(controller == null) {
            return
        }

        val server = NewsgroupServer(host = hostname.toString(), username = name.text.toString(), email = email.toString())
        controller.addServer(server)

        val thread = Thread {
            controller.fetchNewsGroups()
        }

        try {
            thread.start()
        } catch (e: Exception) {
            when(e) {
                is NewsgroupConnection.NewsgroupConnectionException -> {
                    Feedback.showError(this.requireView(), getString(R.string.feedback_server_connection_error))
                    println("Error on Server connection: " + e.message)
                    return
                }
                else -> {
                    throw e
                }
            }
        }

        if(serverAlias.isNotEmpty()) {
            server.alias = serverAlias.toString()
        }

        thread.join()


        lifecycleScope.launch {
            server.id =
                (activity as? MainActivity)?.db?.newsgroupServerDao()?.insert(server)?.toInt() ?: 0
        }


        lifecycleScope.launch {
            if (controller.currentServer != null)
                controller.setCurrentServerDB(controller.currentServer!!.id, false)

            controller.setCurrentServerDB(server.id, true)
        }

        controller.currentServer = server

        findNavController().navigate(R.id.action_AddNewsgroup_to_Subscribe)
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}