package com.example.firebaseex2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firebaseex2.databinding.FragmentHomeBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var AUTH: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        AUTH = FirebaseAuth.getInstance()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast("Добро пожаловать")
                    } else showToast(task.exception?.message.toString())
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                binding.progressBar.isVisible = false
                binding.sendMessageTv.isVisible = false
                val action = HomeFragmentDirections.actionNavigationHomeToEnterCodeFragment(id)
                findNavController().navigate(action)
            }
        }

        binding.send.setOnClickListener {
            binding.apply {
                progressBar.isVisible = true
                sendMessageTv.isVisible = true
                editTextPhone.isVisible = false
                send.isVisible = false
            }
            showToast(binding.editTextPhone.text.toString())
            sendCode()
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun sendCode() {
        if (binding.editTextPhone.text.toString().isEmpty()) {
            showToast("Enter phone")
        } else {
            authUser()
        }
    }

    private fun authUser() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            binding.editTextPhone.text.toString(),
            30,
            TimeUnit.SECONDS,
            requireActivity(),
            mCallback
        )
    }
}