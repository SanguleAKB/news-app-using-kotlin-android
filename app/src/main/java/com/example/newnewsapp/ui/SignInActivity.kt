package com.example.newnewsapp.ui

import android.content.Intent
import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.newnewsapp.R
import com.example.newnewsapp.User
import com.example.newnewsapp.databinding.ActivitySignInBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val userName = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInput(name, email, userName, password)) {
                val user = User(name, email, password, userName)

                database.child(userName).setValue(user).addOnSuccessListener {
                    Toast.makeText(this, "Sign Up success..", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validateInput(name: String, email: String, userName: String, password: String): Boolean {
        if (name.isBlank() || email.isBlank() || userName.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Please fill all information correctly..", Toast.LENGTH_LONG).show()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter valid email address...", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
