package com.example.projekakhirppapb

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projekakhirppapb.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var sharePreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        sharePreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            binding.profileEmail.setText(currentUser.email)
        }

//        binding.edtProfileEmail.setText(auth.currentUser!!.email)

        binding.logoutButton.setOnClickListener{
            sharePreferences.edit().putBoolean("isLoggedIn", false).apply()

            startActivity(Intent(requireActivity(), MainActivity::class.java))
            Firebase.auth.signOut()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

    }
}