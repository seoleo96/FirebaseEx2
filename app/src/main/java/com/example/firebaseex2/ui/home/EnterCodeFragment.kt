package com.example.firebaseex2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
                binding.forEditText.text = "send code"
            } else {
                binding.progressBarConstr.isVisible = true
                enterCode()
            }
        }

        binding.send.setOnClickListener {
            signOut()
        }

        return root
    }

    private fun signOut(){
        AUTH.currentUser?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(requireContext(), "deleted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "not deleted", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun enterCode() {
        val code = binding.editTextPhone2.text.toString()
        val id = args.id.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.progressBarConstr.isVisible = false
                binding.textView3.isVisible = true
                val a = task.result?.user?.phoneNumber + " " + AUTH.currentUser?.phoneNumber.toString()
                binding.textView3.text = a
                    Toast.makeText(requireContext(), task.result?.user?.phoneNumber, Toast.LENGTH_SHORT).show()

            } else {
                binding.content.isVisible = true
                binding.progressBarConstr.isVisible = false
                binding.textView3.text = "неправильный код проверьте пж"
            }
        }
    }

}