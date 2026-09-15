package com.example.campusskillswap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val email =
            findViewById<EditText>(R.id.etEmail)

        val password =
            findViewById<EditText>(R.id.etPassword)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        val tvRegister =
            findViewById<TextView>(R.id.tvRegister)

        tvRegister.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }

        btnLogin.setOnClickListener {

            val enteredEmail =
                email.text.toString().trim()

            val enteredPassword =
                password.text.toString()

            if (enteredEmail.isEmpty() ||
                enteredPassword.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val users =
                UserStorage.getUsers(this)

            var loginSuccessful = false
            var loggedInName = ""

            for (i in 0 until users.length()) {

                val user =
                    users.getJSONObject(i)

                if (
                    enteredEmail.equals(
                        user.getString("email"),
                        ignoreCase = true
                    ) &&
                    enteredPassword ==
                    user.getString("password")
                ) {

                    loginSuccessful = true

                    loggedInName =
                        user.getString("name")

                    break
                }
            }

            if (loginSuccessful) {

                getSharedPreferences(
                    "CampusSkillSwap",
                    MODE_PRIVATE
                )
                    .edit()
                    .putString(
                        "current_email",
                        enteredEmail
                    )
                    .putString(
                        "current_name",
                        loggedInName
                    )
                    .apply()

                Toast.makeText(
                    this,
                    "Login successful 👋",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this,
                        HomeActivity::class.java
                    )
                )

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Invalid email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}