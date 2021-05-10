package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentEditNewsgroupBinding
import kotlinx.android.synthetic.main.fragment_edit_newsgroup.*
import kotlinx.android.synthetic.main.fragment_edit_newsgroup.view.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentEditNewsgroup : Fragment() {

    val args: FragmentEditNewsgroupArgs by navArgs()

    private lateinit var binding: FragmentEditNewsgroupBinding
    private lateinit var viewModel: ServerObservable
    private lateinit var controller: NewsgroupController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentEditNewsgroupBinding.inflate(layoutInflater)
        val view = binding.root

        binding.buttonSaveNewsgroup.setOnClickListener() {
            onButtonSaveNewsgroupClick()
        }
        binding.buttonCloseProfile.setOnClickListener() {
            findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
        }
        binding.buttonDeleteNewsgroup.setOnClickListener() {
            deleteServer()
            if(controller.servers.size == 0)
            {
                findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentAddNewsgroup)
            }
            else
            {
                controller.currentServer = controller.servers.keys.first()
                System.out.println(controller.currentServer)
                findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")
        controller = viewModel.data.value!!

        binding.editTextNewsgroupAlias.setText(controller.currentServer.alias)


        val title = args.title
        val name = args.name


        if ( title == "default" ) {
            binding.headerNewsgroupName.text = controller.currentServer.host
            binding.bodyNewsgroupName.text = controller.currentServer.host
            news_or_sub.text = getString(R.string.label_newsgroup_name)
        }
        else {
            view.header_newsgroup_name.setText(title)
            view.body_newsgroup_name.setText(name)
            news_or_sub.text = getString(R.string.label_subgroup_name)
        }

    }

    fun deleteServer()
    {
        controller.removeCurrentServer()
    }

    fun onButtonSaveNewsgroupClick()
    {
        val serverAlias = binding.editTextNewsgroupAlias.text
        controller.renameCurrentAlias(serverAlias.toString())
        findNavController().navigate(R.id.action_FragmentEditNewsgroup_to_FragmentShowSubgroups)
    }

    var textToSet: String? = null
    fun ChangeText(text: String?) {
        textToSet = text
    }
}