package com.example.campusskillswap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_password)

        val etEmail =
            findViewById<EditText>(R.id.etEmail)

        val etNewPassword =
            findViewById<EditText>(R.id.etNewPassword)

        val etConfirmPassword =
            findViewById<EditText>(R.id.etConfirmPassword)

        val btnResetPassword =
            findViewById<Button>(R.id.btnResetPassword)

        val tvBackToLogin =
            findViewById<TextView>(R.id.tvBackToLogin)

        tvBackToLogin.setOnClickListener {
            finish()
        }

        btnResetPassword.setOnClickListener {

            val email =
                etEmail.text.toString().trim()

            val newPassword =
                etNewPassword.text.toString()

            val confirmPassword =
                etConfirmPassword.text.toString()

            // Check empty fields
            if (
                email.isEmpty() ||
                newPassword.isEmpty() ||
                confirmPassword.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Check password length
            if (newPassword.length < 6) {
                Toast.makeText(
                    this,
                    "Password must be at least 6 characters",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Check passwords match
            if (newPassword != confirmPassword) {
                Toast.makeText(
                    this,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Check whether email exists
            val users =
                UserStorage.getUsers(this)

            var emailExists = false

            for (i in 0 until users.length()) {

                val user =
                    users.getJSONObject(i)

                if (
                    user.getString("email")
                        .equals(
                            email,
                            ignoreCase = true
                        )
                ) {
                    emailExists = true
                    break
                }
            }

            if (!emailExists) {

                Toast.makeText(
                    this,
                    "No account found with this email",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Update password
            val updated =
                UserStorage.updatePassword(
                    this,
                    email,
                    newPassword
                )

            if (updated) {

                Toast.makeText(
                    this,
                    "Password reset successful 🔐",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Unable to reset password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}