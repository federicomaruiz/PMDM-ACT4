package com.utad.pmdmu5.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.utad.pmdmu5.data.db.paperdb.UserRepository
import com.utad.pmdmu5.databinding.FragmentLoginBinding
import com.utad.pmdmu5.ui.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding: FragmentLoginBinding get() = _binding
    private val viewModel: LoginViewModel by viewModels()
    private val userRepository = UserRepository()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUser()
        oberseveViewModelResponse()
        listenBtnAcceder()
        listenBtnRegister()
    }

    private fun checkUser() {
        lifecycleScope.launch(Dispatchers.IO) {
            val isLoggedIn = userRepository.checkUser()
            if (isLoggedIn) {
                goHome()
            }
        }
    }

    private fun listenBtnRegister() {
        binding.btnNoRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToCreateFragment()
            findNavController().navigate(action)
        }
    }

    private fun listenBtnAcceder() {
        binding.bbtnAcceder.setOnClickListener {
            val username = binding.etEmail.text.toString().trim()
            val password = binding.etPasswd.text.toString().trim()
            if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
                viewModel.doLoginFire(username, password)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun oberseveViewModelResponse() {
        // Observa los cambios en el estado de la interfaz de usuario desde el ViewModel
        viewModel.loginResponse.observe(viewLifecycleOwner) { state ->
            if (state.isError) {
                Toast.makeText(
                    requireContext(),
                    "Datos no validos",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (state.isLogin) {
                goHome()
            }
        }

    }

    private fun goHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)

    }
}
