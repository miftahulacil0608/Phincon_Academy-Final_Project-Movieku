package com.example.data.source.remote.firebase

import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FireBaseRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FireBaseRemoteDataSourceRepository {
    override suspend fun signUpWithEmailAndPassword(
        fullName: String,
        email: String,
        password: String
    ): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val createAccount = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                if (createAccount.user != null) {
                    updateProfile(fullName)
                    Result.success(true)
                } else {
                    Result.failure(IllegalArgumentException("Unknow Error"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val signInResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (signInResult.user != null) {
                    Result.success(true)
                } else {
                    Result.failure(IllegalArgumentException("Unknow Error"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val googleIdCredentialToken = getGoogleIdTokenCredential(credentialResponse)
                val authCredential: AuthCredential =
                    GoogleAuthProvider.getCredential(googleIdCredentialToken, null)
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                if (authResult.user != null) {
                    Result.success(true)
                } else {
                    Result.success(false)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override fun fetchFirebaseUser():FirebaseUser?{
        return firebaseAuth.currentUser
    }

    override fun signOut() = firebaseAuth.signOut()

    private fun getGoogleIdTokenCredential(credentialResponse: GetCredentialResponse): String? {
        return when (val credential = credentialResponse.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    //return idToken
                    googleIdTokenCredential.idToken
                } else {
                    null
                }
            }

            else -> null
        }
    }

    private fun updateProfile(fullName: String) {
        val userProfileChainRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(fullName)
            .build()
        try {
            firebaseAuth.currentUser?.updateProfile(userProfileChainRequest)
        } catch (e: Exception) {
            throw IllegalStateException(e)
        }
    }

}