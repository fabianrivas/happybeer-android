package com.fab.happybeer.data.local.user

import com.fab.happybeer.core.Result
import com.fab.happybeer.data.model.User

class LocalUserDataSource(private val userDao: UserDao) {

    suspend fun saveUser(user: User): Result<Long> =
        Result.Success(userDao.saveUser(user))

    suspend fun getUser(username: String, password: String): Result<Boolean> {
        var isUserValid = false
        userDao.getUser(username).let {
            if (password == it.password) isUserValid = true
        }
        return Result.Success(isUserValid)
    }
}