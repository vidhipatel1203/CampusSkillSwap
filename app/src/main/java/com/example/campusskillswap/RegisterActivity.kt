package com.example.campusskillswap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val name = findViewById<EditText>(R.id.etName)
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val confirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        val cbAndroid = findViewById<CheckBox>(R.id.cbAndroid)
        val cbWeb = findViewById<CheckBox>(R.id.cbWeb)
        val cbPython = findViewById<CheckBox>(R.id.cbPython)
        val cbDesign = findViewById<CheckBox>(R.id.cbDesign)
        val cbPhotography = findViewById<CheckBox>(R.id.cbPhotography)
        val cbGuitar = findViewById<CheckBox>(R.id.cbGuitar)

        findViewById<TextView>(R.id.tvLogin).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        findViewById<Button>(R.id.btnRegister).setOnClickListener {

            val studentName = name.text.toString().trim()
            val studentEmail = email.text.toString().trim()
            val studentPassword = password.text.toString()
            val studentConfirmPassword = confirmPassword.text.toString()

            if (studentName.isEmpty() || studentEmail.isEmpty() ||
                studentPassword.isEmpty() || studentConfirmPassword.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (studentPassword != studentConfirmPassword) {
                Toast.makeText(
                    this,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedSkills = mutableListOf<String>()

            if (cbAndroid.isChecked) {
                selectedSkills.add("Android Development")
            }

            if (cbWeb.isChecked) {
                selectedSkills.add("Web Development")
            }

            if (cbPython.isChecked) {
                selectedSkills.add("Python")
            }

            if (cbDesign.isChecked) {
                selectedSkills.add("Graphic Design")
            }

            if (cbPhotography.isChecked) {
                selectedSkills.add("Photography")
            }

            if (cbGuitar.isChecked) {
                selectedSkills.add("Guitar")
            }

            if (selectedSkills.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please select at least one skill",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Check whether email already exists
            val users = UserStorage.getUsers(this)

            for (i in 0 until users.length()) {

                val existingUser = users.getJSONObject(i)

                if (existingUser.getString("email")
                        .equals(studentEmail, ignoreCase = true)
                ) {
                    Toast.makeText(
                        this,
                        "This email is already registered",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            }

            // Save new user
            UserStorage.saveUser(
                this,
                studentName,
                studentEmail,
                studentPassword,
                selectedSkills
            )

            Toast.makeText(
                this,
                "Registration successful! 🎉",
                Toast.LENGTH_SHORT
            ).show()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}