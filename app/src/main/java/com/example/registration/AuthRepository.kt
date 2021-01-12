package com.example.registration

import kotlinx.coroutines.Deferred

interface AuthRepository {
   suspend fun login(email: String, password: String): Deferred<String>
}