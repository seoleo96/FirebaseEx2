package com.example.firebaseex2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firebaseex2.databinding.FragmentEnterCodeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class EnterCodeFragment : Fragment() {

    private var _binding: FragmentEnterCodeBinding? = null
    private lateinit var AUTH: FirebaseAuth
    private val args: EnterCodeFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEnterCodeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        AUTH = FirebaseAuth.getInstance()


        binding.editTextPhone2.doAfterTextChanged { editable ->
            if (editable?.length!! < 6 || editable.isEmpty()) {
                binding.send.isEnabled = false
            } else {
                binding.send.isEnabled = true
                binding.content.isVisible = false
                binding.progressBarConstr.isVisible = true
                enterCode()
            }
        }

        return root
    }

    private fun enterCode() {
        val code = binding.editTextPhone2.text.toString()
        val id = args.id.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.content.isVisible = false
                binding.progressBarConstr.isVisible = true
                findNavController().popBackStack()
            } else {
                binding.content.isVisible = true
                binding.progressBarConstr.isVisible = false
                binding.textView3.text = "неправильный код проверьте пж"
            }
        }
    }

}