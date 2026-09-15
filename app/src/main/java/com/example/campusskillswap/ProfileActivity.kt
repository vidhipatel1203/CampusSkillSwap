package com.example.campusskillswap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvName =
            findViewById<TextView>(R.id.tvName)

        val tvEmail =
            findViewById<TextView>(R.id.tvEmail)

        val tvSkills =
            findViewById<TextView>(R.id.tvSkills)

        val preferences =
            getSharedPreferences(
                "CampusSkillSwap",
                MODE_PRIVATE
            )

        val currentEmail =
            preferences.getString(
                "current_email",
                ""
            ) ?: ""

        val users =
            UserStorage.getUsers(this)

        var foundUser = false

        for (i in 0 until users.length()) {

            val user =
                users.getJSONObject(i)

            val email =
                user.getString("email")

            if (
                email.equals(
                    currentEmail,
                    ignoreCase = true
                )
            ) {

                val name =
                    user.getString("name")

                tvName.text = name
                tvEmail.text = email

                val skills =
                    user.getJSONArray("skills")

                val skillText =
                    StringBuilder()

                for (j in 0 until skills.length()) {

                    skillText.append("⭐ ")
                    skillText.append(
                        skills.getString(j)
                    )

                    if (j < skills.length() - 1) {
                        skillText.append("\n\n")
                    }
                }

                tvSkills.text =
                    skillText.toString()

                foundUser = true

                break
            }
        }

        if (!foundUser) {

            tvName.text = "Student"
            tvEmail.text = "Email not found"
            tvSkills.text = "No skills available"
        }

        // Logout
        findViewById<Button>(R.id.btnLogout).setOnClickListener {

            preferences.edit()
                .remove("current_email")
                .remove("current_name")
                .apply()

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finishAffinity()
        }

        // Back to Home
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}