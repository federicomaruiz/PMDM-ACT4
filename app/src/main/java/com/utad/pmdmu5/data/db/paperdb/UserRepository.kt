package com.utad.pmdmu5.data.db.paperdb

import com.utad.pmdmu5.data.db.firebase.model.User
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    private val USER_BOOK_NAME = "users"

    fun getUserByEmail(email: String): User? {
        return Paper.book(USER_BOOK_NAME).read<User>(email)
    }

    suspend fun saveUser(email:String, user: User) {
        Paper.book(USER_BOOK_NAME).write(email,user)
    }

    fun deleteUserByEmail(email: String) {
        Paper.book(USER_BOOK_NAME).delete(email)
    }

    suspend fun checkUser(): Boolean {
        return withContext(Dispatchers.IO) {
            val keyList: List<String> = Paper.book(USER_BOOK_NAME).allKeys
            keyList.any { key ->
                val user: User? = Paper.book(USER_BOOK_NAME).read(key)
                user != null && user.isLoggedIn
            }
        }
    }

}