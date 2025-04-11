package com.cosmic.youassessment.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    val currentUser: Flow<FirebaseUser?>
    suspend fun signUp(email: String, password: String): Result<FirebaseUser>
    suspend fun login(email: String, password: String): Result<FirebaseUser>
    suspend fun logout()
}

class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {
    private val currentUserFlow = MutableStateFlow<FirebaseUser?>(auth.currentUser)

    init {
        auth.addAuthStateListener { firebaseAuth ->
            currentUserFlow.tryEmit(firebaseAuth.currentUser)
        }
    }

    override val currentUser: Flow<FirebaseUser?> = currentUserFlow

    override suspend fun signUp(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }
}