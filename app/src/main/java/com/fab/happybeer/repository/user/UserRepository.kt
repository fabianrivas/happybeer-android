package com.fab.happybeer.repository.user

import com.fab.happybeer.core.Result
import com.fab.happybeer.data.model.User

interface UserRepository {

    suspend fun saveUser(userEntity: User): Result<Long>

    suspend fun getUser(username: String, password: String): Result<Boolean>
}