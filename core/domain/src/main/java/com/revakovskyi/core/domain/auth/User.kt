package com.revakovskyi.core.domain.auth

data class User(
    val userId: String,
    val username: String?,
    val email: String?,
    val profilePictureUrl: String?,
)
