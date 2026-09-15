package com.example.campusskillswap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val preferences =
            getSharedPreferences(
                "CampusSkillSwap",
                MODE_PRIVATE
            )

        val studentName =
            preferences.getString(
                "current_name",
                "Student"
            )

        val helloText =
            findViewById<TextView>(R.id.tvHello)

        helloText.text =
            "Hello, $studentName! 👋"

        // Explore Skills
        findViewById<Button>(R.id.btnExplore).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ExploreActivity::class.java
                )
            )
        }

        // My Swap Requests
        findViewById<Button>(R.id.btnRequests).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RequestsActivity::class.java
                )
            )
        }

        // My Profile
        findViewById<Button>(R.id.btnProfile).setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ProfileActivity::class.java
                )
            )
        }
    }
}