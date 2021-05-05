package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentSubscribeBinding
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentSubscribe : Fragment() {
    private lateinit var binding: FragmentSubscribeBinding
    private lateinit var viewModel: ServerObservable

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubscribeBinding.inflate(layoutInflater)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        binding.buttonFinish.setOnClickListener() {
            onButtonFinishClick()
        }

        binding.editTextGroupFilter.addTextChangedListener(filterTextWatcher);

        viewModel.data.observe(viewLifecycleOwner, Observer {
            var newsgroupServer_ : NewsgroupServer? = null
            for ((key, value) in  viewModel.data.value!!.servers) {
                if(key.active == true)
                {
                    newsgroupServer_ = key
                }
            }

            if (newsgroupServer_ != null) {
                for(newsgroup in newsgroupServer_.newsGroups!!) {

                    System.out.println("Adding NG: " + newsgroup.name)

                    val check = CheckBox(activity)
                    check.text = newsgroup.name
                    check.setPadding(10, 10, 10, 10)
                    val params = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(5, 5, 5, 5)
                    params.gravity = Gravity.NO_GRAVITY
                    check.layoutParams = params
                    check.gravity = Gravity.CENTER

                    binding.viewSubscribe.addView(check)
                }
            }
        })
        val view = binding.root
        return view
    }

    private val filterTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val checkboxes = binding.viewSubscribe.children

            for (box in checkboxes) {
                val c: CheckBox = box as CheckBox;

                if(!c.text.contains(s!!)) {
                    c.visibility = View.GONE;
                } else {
                    c.visibility = View.VISIBLE;
                }

            }
        }
    }

    fun onButtonFinishClick() {
        binding.viewSubscribe.forEach {
            val checkbox = it as CheckBox
            if(checkbox.isChecked) {
                var newsgroupServer_ : NewsgroupServer? = null
                for ((key, value) in  viewModel.data.value!!.servers) {
                    if(key.active == true)
                    {
                        newsgroupServer_ = key
                    }
                }

                val ng = newsgroupServer_?.newsGroups?.filter{ ng -> checkbox.text == ng.name}
                if (ng != null) {
                    ng.elementAt(0).subscribed = true
                }
            }
        }
        findNavController().navigate(R.id.action_FragmentSubscribe_to_FragmentShowSubgroups)
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*view.findViewById<Button>(R.id.button_subscribe).setOnClickListener {
            findNavController().navigate(R.id.action_AddNewsgroup_to_Subscribe)
        }*/
    }



}