package com.utad.pmdmu5.ui.registers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.utad.pmdmu5.databinding.FragmentCreateBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateFragment : Fragment() {

    private lateinit var _binding: FragmentCreateBinding
    private val binding: FragmentCreateBinding get() = _binding
    private val viewModel: CreateViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oberseveViewModelResponse()
        binding.btnRegister.setOnClickListener {
            createUser()
        }
    }

    private fun oberseveViewModelResponse() {
        viewModel.createResponse.observe(viewLifecycleOwner) { state ->
            if (state.isError) {
                Toast.makeText(
                    requireContext(),
                    "Datos no validos",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (state.isCreate) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Usuario creado", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun createUser() {
        val email = binding.etCreateEmail.text.toString()
        val password = binding.etCreatePasswd.text.toString()

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.doLoginFireBase(email, password)
            }
        } else {
            Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT)
                .show()
        }

    }
}
