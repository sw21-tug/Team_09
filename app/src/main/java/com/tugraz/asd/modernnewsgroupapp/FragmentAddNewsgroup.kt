package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentAddNewsgroupBinding
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
            (activity as? MainActivity)?.skipSetup = false;
            findNavController().navigate(R.id.action_FragmentAddNewsgroup_to_FragmentShowSubgroups)
        }

        // Inflate the layout for this fragment
        binding = FragmentAddNewsgroupBinding.inflate(layoutInflater)
        val view = binding.root
        binding.buttonSubscribe.setOnClickListener() {
            onButtonSubscribeClick()
        }
        return view
    }


    fun onButtonSubscribeClick() {
        val email = binding.editTextEmail.text
        val name = binding.editTextName
        val hostname = binding.editTextNewsgroupServer.text
        val serverAlias = binding.editTextServerAlias.text


        if(!isValidEmail(email)) {
            // TODO: show error message
            return
        }

        val controller = viewModel.controller.value
        val server = NewsgroupServer(host = hostname.toString(), username = name.toString())

        if(controller == null) {
            return;
        }

        controller.addServer(server)

        System.out.println("Server: " + server.host)

        val thread = Thread {
            controller.fetchNewsGroups()
        }

        try {
            thread.start()
        } catch (e: Exception) {
             when(e) {
                is NewsgroupConnection.NewsgroupConnectionException -> {
                    // TODO: show error message
                    System.out.println("Error on Server connection: " + e.message)
                    return
                }
                else -> {
                    throw e
                }
            }
        }

        if(serverAlias.length > 0) {
            server.alias = serverAlias.toString()
        }

        thread.join()


        lifecycleScope.launch {
            (activity as? MainActivity)?.db?.newsgroupServerDao()?.insertAll(server)
        }



        controller.currentServer = server
        controller.addServer(server)

        findNavController().navigate(R.id.action_AddNewsgroup_to_Subscribe)

    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}