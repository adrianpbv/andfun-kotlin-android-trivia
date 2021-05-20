/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        binding.nextMatchButton.setOnClickListener{view: View ->
            //view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}",
            Toast.LENGTH_LONG).show()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)

        //keep this method to be functional if we want to add additional items at a later date
        //resolveActivity using the packageManger to make sure our shareIntent resolves to an activity
        if(null == getSharedIntent().resolveActivity(requireActivity().packageManager)){
            //hide the menu item if it doesn't resolve our activity
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    /**
     * This method create the trivia share intent
     */
    private fun getSharedIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND) //tells Android we want activities registered with
        // intent-filter to handle the send_action, which is useful for sharing

//        shareIntent.setType("text/plain") //mime type to locate the correct activity to share to
//            .putExtra(Intent.EXTRA_TEXT,
//            getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
//        return shareIntent

        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
            .setType("text/String")
            .intent //finishing off by building our intent.
    }

    private fun shareSuccess(){
        startActivity(getSharedIntent())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Sharing from the Menu
        when(item.itemId){
            R.id.share -> shareSuccess() //we're going to navigate to another app using a custom Intent
        }
        return super.onOptionsItemSelected(item)
    }
}
