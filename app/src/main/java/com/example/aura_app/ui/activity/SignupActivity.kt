package com.example.aura_app.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aura_app.R
import com.example.aura_app.databinding.ActivitySignupBinding
import com.example.aura_app.model.UserModel
import com.example.aura_app.repository.UserRepositoryImpl
import com.example.aura_app.viewModel.UserViewModel

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.signUpBtn.setOnClickListener {

            var email = binding.Email.text.toString()
            var username = binding.UsernameSignup.text.toString()
            var password = binding.passS.text.toString()

            userViewModel.signup(email,password) { success, message, userId ->
                if (success){
                    var userModel =  UserModel(userId,username,email)
                    userViewModel.addUserToDatabase(userId, userModel) { success, message ->
                        if (success) {
                            Toast.makeText(
                                this@SignupActivity,
                                message, Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@SignupActivity,
                                message, Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@SignupActivity,
                        message, Toast.LENGTH_LONG
                    ).show()
                }
            }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}
