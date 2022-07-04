package com.fab.happybeer.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fab.happybeer.core.Result
import com.fab.happybeer.data.model.User
import com.fab.happybeer.repository.user.UserRepository
import com.fab.happybeer.ui.fromBase64
import com.fab.happybeer.ui.toBase64
import kotlinx.coroutines.Dispatchers

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun saveUser(username: String, password: String) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            kotlin.runCatching {
                val user = User(username, password.toBase64())
                userRepository.saveUser(user)
            }.onSuccess { result ->
                emit(result)
            }.onFailure { throwable ->
                emit(Result.Failure(Exception(throwable.message)))
            }
        }

    fun loginUser(username: String, password: String) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
            kotlin.runCatching {
                userRepository.getUser(username, password.toBase64())
            }.onSuccess { result ->
                emit(result)
            }.onFailure { throwable ->
                emit(Result.Failure(Exception(throwable.message)))
            }
        }
}

class UserViewModelFactory(
    private val repo: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(repo)
    }

}