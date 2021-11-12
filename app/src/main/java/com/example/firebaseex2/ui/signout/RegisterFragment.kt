package com.example.firebaseex2.ui.signout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firebaseex2.R
import com.example.firebaseex2.databinding.FragmentRegisterBinding
import com.example.firebaseex2.domain.usecase.UIValidationState
import com.example.firebaseex2.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val registerViewModel: RegisterViewModel by viewModel()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var phoneNumber: String
//    private val signOut: SignOutFirebase by lazy {
//        SignOutFirebase.Base(requireContext())
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerViewModel.onCodeSent()
        registerViewModel.onCodeSent.observe(viewLifecycleOwner, { code ->
            if (code == "") {
                binding.mess.text = "Please enter phone number"
            } else {
                val action =
                    RegisterFragmentDirections.actionRegisterFragmentToEnterCodeFragment(
                        code)
                findNavController().navigate(action)
            }
        })
//        lifecycleScope.launch(Dispatchers.IO) {
//            signOutWthPhoneNumber.onCodeSent().collect { code ->
//                if (code == "") {
//                    withContext(Dispatchers.Main) {
//                        binding.mess.text = "Please enter phone number"
//                    }
//                } else {
//                    withContext(Dispatchers.Main) {
//                        val action =
//                            RegisterFragmentDirections.actionRegisterFragmentToEnterCodeFragment(
//                                code)
//                        findNavController().navigate(action)
//                    }
//                }
//            }
//        }


        binding.send.setOnClickListener {
            phoneNumber = binding.editTextPhone.text.toString()
            it.hideKeyboard()
            registerViewModel.validate(phoneNumber = phoneNumber)
            registerViewModel.uiValidateLiveData.observe(viewLifecycleOwner, { uiState ->
                when (uiState) {
                    is UIValidationState.IsEmpty -> {
                        binding.editTextPhone.apply {
                            error = "Please enter phone number"
                            requestFocus()
                        }
                    }
                    is UIValidationState.PhoneNumberLess -> {
                        binding.editTextPhone.apply {
                            error = "Phone number must contain at least 12 digits"
                            requestFocus()
                        }
                    }
                    else -> {
                        binding.apply {
                            progressBar.isVisible = true
                            sendMessageTv.isVisible = true
                            editTextPhone.isVisible = false
                            mess.isVisible = false
                            send.isVisible = false
                        }
//                        lifecycleScope.launch {
//                            signOutWthPhoneNumber.authUser(number = phoneNumber)
//                        }
                        registerViewModel.authUser(phoneNumber)
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}