package com.example.aura_app

import com.example.aura_app.repository.AuthRepoImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*

class AuthUnitTest {

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    private lateinit var authRepo: AuthRepoImpl

    @Captor
    private lateinit var captor: ArgumentCaptor<OnCompleteListener<AuthResult>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        authRepo = AuthRepoImpl(mockAuth)
    }

    @Test
    fun testLogin_Successful() {
        val email = "test@example.com"
        val password = "testPassword"
        var resultMessage = "Initial"

        // Mock successful login
        `when`(mockTask.isSuccessful).thenReturn(true)
        `when`(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockTask)

        val callback = { success: Boolean, message: String -> resultMessage = message }

        authRepo.login(email, password, callback)

        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Login successful", resultMessage)
    }

    @Test
    fun testLogin_Failure() {
        val email = "test@example.com"
        val password = "testPassword"
        var resultMessage = "Initial"

        // Mock failed login
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockTask)

        val callback = { success: Boolean, message: String -> resultMessage = message }

        authRepo.login(email, password, callback)

        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Login failed", resultMessage)
    }

    @Test
    fun testSignup_Successful() {
        val email = "test@example.com"
        val password = "testPassword"
        var resultMessage = "Initial"

        // Mock successful signup
        `when`(mockTask.isSuccessful).thenReturn(true)
        `when`(mockAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockTask)

        val callback = { success: Boolean, message: String -> resultMessage = message }

        authRepo.signup(email, password, callback)

        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Signup successful", resultMessage)
    }

    @Test
    fun testSignup_Failure() {
        val email = "test@example.com"
        val password = "testPassword"
        var resultMessage = "Initial"

        // Mock failed signup
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockTask)

        val callback = { success: Boolean, message: String -> resultMessage = message }

        authRepo.signup(email, password, callback)

        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Signup failed", resultMessage)
    }

    @Test
    fun testLogout() {
        var resultMessage = "Initial"

        val callback = { success: Boolean, message: String -> resultMessage = message }

        authRepo.logout(callback)

        assertEquals("Logout successful", resultMessage)
    }
}
