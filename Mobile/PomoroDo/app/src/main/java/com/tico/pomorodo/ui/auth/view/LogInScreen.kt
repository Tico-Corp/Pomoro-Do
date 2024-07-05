package com.tico.pomorodo.ui.auth.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialProviderConfigurationException
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.tico.pomorodo.BuildConfig
import com.tico.pomorodo.R
import com.tico.pomorodo.ui.auth.viewModel.AuthViewModel
import com.tico.pomorodo.ui.common.view.CustomTextButton
import com.tico.pomorodo.ui.common.view.executeToast
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcLogoGoogle
import com.tico.pomorodo.ui.iconpack.commonIconPack.IcTitle
import com.tico.pomorodo.ui.theme.IconPack
import com.tico.pomorodo.ui.theme.PomoroDoTheme
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

private const val TAG = "LoginScreen: "

@Composable
fun LogInScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigate: () -> Unit,
    isOffline: Boolean = false
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val hashedNonce = generateNonce()
    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.GOOGLE_AUTH_CLIENT_ID)
        .setAutoSelectEnabled(true)
        .setNonce(hashedNonce)
        .build()

    val request: GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    Surface(modifier = Modifier.fillMaxSize(), color = PomoroDoTheme.colorScheme.surface) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Icon(
                imageVector = IconPack.IcTitle,
                contentDescription = stringResource(R.string.content_ic_title),
                modifier = Modifier.padding(horizontal = 30.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 57.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isOffline) {
                    OfflineLogInButton(onClick = { navigate() })
                } else {
                    LogInButton(
                        onClick = {
                            coroutineScope.launch {
                                handleLogin(
                                    context = context,
                                    request = request,
                                    nonce = hashedNonce,
                                    onSuccess = { result ->
                                        viewModel.requestLogin()
                                        viewModel.setProfile(result.profilePictureUri)
                                        navigate()
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OfflineLogInButton(onClick: () -> Unit) {
    CustomTextButton(
        text = stringResource(id = R.string.content_offline_log_in),
        containerColor = PomoroDoTheme.colorScheme.background,
        contentColor = PomoroDoTheme.colorScheme.primaryContainer,
        textStyle = PomoroDoTheme.typography.laundryGothicRegular16,
        verticalPadding = 10.dp,
        borderColor = PomoroDoTheme.colorScheme.primaryContainer,
        borderWidth = 1.5.dp,
        onClick = onClick
    )
}

@Composable
fun LogInButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = PomoroDoTheme.colorScheme.gray70,
                shape = RoundedCornerShape(4.dp)
            )
            .height(40.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonColors(
            containerColor = PomoroDoTheme.colorScheme.background,
            contentColor = PomoroDoTheme.colorScheme.googleLoginText,
            disabledContainerColor = PomoroDoTheme.colorScheme.background,
            disabledContentColor = PomoroDoTheme.colorScheme.googleLoginText
        ),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = IconPack.IcLogoGoogle,
                contentDescription = stringResource(R.string.content_ic_logo_google),
                modifier = Modifier.size(18.dp),
                tint = Color.Unspecified
            )
            Text(
                text = stringResource(R.string.content_log_in_with_google),
                textAlign = TextAlign.Start,
                style = PomoroDoTheme.typography.robotoMedium14
            )
        }
    }
}

private suspend fun handleLogin(
    context: Context,
    request: GetCredentialRequest,
    nonce: String,
    onSuccess: (GoogleIdTokenCredential) -> Unit
) {
    val credentialManager = CredentialManager.create(context = context)

    runCatching {
        val result = credentialManager.getCredential(
            request = request,
            context = context,
        )
        Log.d(TAG, "requestGoogleLogin: runCatching")
        handleSignIn(result)
    }.onSuccess { result ->
        Log.d(TAG, "requestGoogleLogin: Success login!!")
        onSuccess(result)
    }.onFailure {
        Log.e(TAG, "requestGoogleLogin: onFailure")
        handleCredentialFailure(it, nonce, context, onSuccess)
    }
}

private fun generateNonce(): String {
    val ranNonce = UUID.randomUUID().toString()
    val bytes = ranNonce.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}

private suspend fun handleCredentialFailure(
    it: Throwable,
    nonce: String,
    context: Context,
    onSuccess: (GoogleIdTokenCredential) -> Unit
) {
    when (it) {
        is NoCredentialException -> {
            val signInWithGoogleOption: GetSignInWithGoogleOption =
                GetSignInWithGoogleOption.Builder(BuildConfig.GOOGLE_AUTH_CLIENT_ID)
                    .setNonce(nonce)
                    .build()

            val signInWithGoogleRequest: GetCredentialRequest =
                GetCredentialRequest.Builder()
                    .addCredentialOption(signInWithGoogleOption)
                    .build()
            Log.d(TAG, "handleCredentialFailure: NoCredentialException")
            handleLogin(
                context = context,
                request = signInWithGoogleRequest,
                nonce = nonce,
                onSuccess = onSuccess
            )
        }

        is GoogleIdTokenParsingException -> {
            Log.e(TAG, "Received an invalid google id token response", it)
        }

        is GetCredentialCancellationException -> {
            Log.e(TAG, "signInWithGoogleRequest: $it")
        }

        is GetCredentialProviderConfigurationException -> {
            context.executeToast(R.string.content_please_update_google_play_service)
        }

        else -> {
            Log.e(TAG, "signInWithPasskeyRequest: $it")
        }
    }
}

fun handleSignIn(result: GetCredentialResponse): GoogleIdTokenCredential {
    when (val credential = result.credential) {
        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                Log.i(TAG, "googleIdToken: $googleIdToken")
                Log.i(TAG, "id: ${googleIdTokenCredential.id}")
                Log.i(TAG, "type: ${googleIdTokenCredential.type}")
                Log.i(TAG, "data: ${googleIdTokenCredential.data}")
                Log.i(TAG, "givenName: ${googleIdTokenCredential.givenName}")
                Log.i(TAG, "displayName: ${googleIdTokenCredential.displayName}")
                Log.i(TAG, "familyName: ${googleIdTokenCredential.familyName}")
                Log.i(TAG, "phoneNumber: ${googleIdTokenCredential.phoneNumber}")
                Log.i(TAG, "profilePictureUri: ${googleIdTokenCredential.profilePictureUri}")

                return googleIdTokenCredential
            } else {
                throw UnexpectedException("Unexpected type of credential")
            }
        }

        else -> {
            throw UnexpectedException("Unexpected type of credential")
        }
    }
}

class UnexpectedException(message: String) : Exception(message)