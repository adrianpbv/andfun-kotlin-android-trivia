package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false)

//        binding.playButton.setOnClickListener{
//            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
//        }
//
        binding.playButton.setOnClickListener{ view: View -> //passing an anonymous function into
                                                            // the setOnClickListener
            //Navigation.findNavController(view).navigate(R.id.action_titleFragment_to_gameFragment)
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment) //KTX extension function for Android view class

        }
        setHasOptionsMenu(true) //tells android to put a menu
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //if the NavigationUI doesn't handle the selection we call super.onOptionsItemSelected(item)
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) ||
                super.onOptionsItemSelected(item)//it allow your app to directly handle the menu item without navigating

    }
}