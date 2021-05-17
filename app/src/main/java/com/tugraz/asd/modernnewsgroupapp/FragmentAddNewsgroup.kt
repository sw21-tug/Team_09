package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        // Inflate the layout for this fragment
        binding = FragmentAddNewsgroupBinding.inflate(layoutInflater)
        val view = binding.root
        binding.buttonSubscribe.setOnClickListener() {
            onButtonSubscribeClick()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")


        viewModel.controller.observe(viewLifecycleOwner, Observer {
            if(viewModel.controller.value!!.skipSetup) {
                viewModel.controller.value!!.skipSetup = false
                findNavController().navigate(R.id.action_FragmentAddNewsgroup_to_FragmentShowSubgroups)
            }
        })


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

        val controller = NewsgroupController()
        val server = NewsgroupServer(host = hostname.toString())

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


        if( viewModel.controller.value == null)
        {
            viewModel.controller.value = controller
        }
        viewModel.controller.value!!.currentServer = server
        viewModel.controller.value!!.addServer(server)

        findNavController().navigate(R.id.action_AddNewsgroup_to_Subscribe)

    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}