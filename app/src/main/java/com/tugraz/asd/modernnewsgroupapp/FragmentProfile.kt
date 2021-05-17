package com.tugraz.asd.modernnewsgroupapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tugraz.asd.modernnewsgroupapp.databinding.FragmentProfileBinding
import java.util.*
import java.util.Locale;
import android.os.Bundle;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FragmentProfile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var res: Resources
    private lateinit var dm: DisplayMetrics
    private lateinit var conf: Configuration
    private lateinit var currentLocale: Locale

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view = binding.root

        val spinner: Spinner = binding.spinnerLanguage
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                view.context,
                R.array.languages_list,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        res = resources
        dm = res.getDisplayMetrics()
        conf = res.getConfiguration()

        currentLocale = conf.locale

        for ((i, lang) in res.getStringArray(R.array.languages_list_values).withIndex()) {
            if(lang == currentLocale.language) {
                binding.spinnerLanguage.setSelection(i)
            }
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCloseProfile.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentProfile_to_FragmentShowSubgroups)
        }

        binding.buttonSaveProfile.setOnClickListener {
            val lang = res.getStringArray(R.array.languages_list_values)[binding.spinnerLanguage.selectedItemPosition];
            val new_locale = Locale(lang)

            if(currentLocale.language != new_locale.language) {
                conf.locale = new_locale
                res.updateConfiguration(conf, dm)
                currentLocale = new_locale
                val refresh = Intent(requireView().context, MainActivity::class.java)
                startActivity(refresh)
            } else {
                findNavController().navigate(R.id.action_FragmentProfile_to_FragmentShowSubgroups)
            }
        }
    }
}