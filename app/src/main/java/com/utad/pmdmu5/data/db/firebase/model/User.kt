package com.utad.pmdmu5.data.db.firebase.model

data class User(
    val email: String? = null,
    val passwd: String? = null,
    var isLoggedIn: Boolean = false
)
