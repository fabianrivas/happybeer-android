package com.fab.happybeer.repository.user

import com.fab.happybeer.core.Result
import com.fab.happybeer.data.local.user.LocalUserDataSource
import com.fab.happybeer.data.model.User

class UserRepositoryImpl(
    private val localUserDataSource: LocalUserDataSource
) : UserRepository {

    override suspend fun saveUser(userEntity: User): Result<Long> =
        localUserDataSource.saveUser(userEntity)

    override suspend fun getUser(username: String, password: String): Result<Boolean> {
        return localUserDataSource.getUser(username, password)
    }

}