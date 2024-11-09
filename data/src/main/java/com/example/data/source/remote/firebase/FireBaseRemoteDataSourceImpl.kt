package com.example.data.source.remote.firebase

import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireBaseRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth:FirebaseAuth
) : FireBaseRemoteDataSourceRepository {

    override suspend fun signInWithGoogle(credentialResponse: GetCredentialResponse): Result<Boolean> {
        return try {
            val googleIdCredentialToken = getGoogleIdTokenCredential(credentialResponse)
            val authCredential: AuthCredential = GoogleAuthProvider.getCredential(googleIdCredentialToken, null)
            val authResult = firebaseAuth.signInWithCredential(authCredential).await()
            if (authResult.user != null){
                Result.success(true)
            }else{
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isUserAuthentication(): Flow<Boolean> {
        return flow {
            emit(firebaseAuth.currentUser != null)
        }
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

}