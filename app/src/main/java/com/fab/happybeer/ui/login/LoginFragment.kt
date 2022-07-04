package com.fab.happybeer.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fab.happybeer.R
import com.fab.happybeer.core.Result
import com.fab.happybeer.data.local.AppDatabase
import com.fab.happybeer.data.local.user.LocalUserDataSource
import com.fab.happybeer.databinding.FragmentLoginBinding
import com.fab.happybeer.presentation.user.UserViewModel
import com.fab.happybeer.presentation.user.UserViewModelFactory
import com.fab.happybeer.repository.user.UserRepositoryImpl
import com.fab.happybeer.ui.hideKeyboard


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val viewmodel by viewModels<UserViewModel> {
        UserViewModelFactory(
            UserRepositoryImpl(
                LocalUserDataSource(
                    AppDatabase.getDatabase(
                        requireContext()
                    ).userDao()
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        setupLogin()
    }

    private fun setupLogin() {
        binding.btnLogin.setOnClickListener {
            it.hideKeyboard()
            val user = binding.etUser.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (validateForm(user, password)) {
                loginUser(user, password)
            }
        }
        binding.btRegister.setOnClickListener {
            it.hideKeyboard()
            val user = binding.etUser.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (validateForm(user, password)) {
                registerUser(user, password)
            }
        }
    }

    private fun validateForm(user: String, password: String): Boolean {
        var isValid = true
        if (user.isEmpty()) {
            binding.tilUser.error = getString(R.string.error_empty_field)
            isValid = false
        }
        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_empty_field)
            isValid = false
        }
        return isValid
    }

    private fun loginUser(username: String, password: String) {
        viewmodel.loginUser(username, password).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data)
                        findNavController().navigate(R.id.action_loginFragment_to_beerListFragment)
                    else {
                        showMessageError()
                    }
                }
                is Result.Failure -> {
                    showMessageError()
                }
            }
        })
    }

    private fun registerUser(username: String, password: String) {
        viewmodel.saveUser(username, password).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_beerListFragment)
                }
                is Result.Failure -> {
                    showMessageError()
                }
            }
        })
    }

    private fun showMessageError() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.title_login_error)
            .setCancelable(true)
            .setMessage(R.string.msg_login_error)
            .setPositiveButton(android.R.string.ok) { view, _ ->
                view.dismiss()
            }
            .create().show()
    }
}