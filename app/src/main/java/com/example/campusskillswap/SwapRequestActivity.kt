package com.example.campusskillswap

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SwapRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_request)

        val skillName =
            intent.getStringExtra("skill_name") ?: "Skill"

        val skillIcon =
            intent.getStringExtra("skill_icon") ?: "⭐"

        val receiverEmail =
            intent.getStringExtra("receiver_email") ?: ""

        val tvSkillToLearn =
            findViewById<TextView>(R.id.tvSkillToLearn)

        val etTeachSkill =
            findViewById<EditText>(R.id.etTeachSkill)

        val etMessage =
            findViewById<EditText>(R.id.etMessage)

        val btnSendRequest =
            findViewById<Button>(R.id.btnSendRequest)

        val btnCancel =
            findViewById<Button>(R.id.btnCancel)

        tvSkillToLearn.text =
            "$skillIcon $skillName"

        btnSendRequest.setOnClickListener {

            val teachSkill =
                etTeachSkill.text.toString().trim()

            val message =
                etMessage.text.toString().trim()

            if (teachSkill.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter a skill you can teach",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (message.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please write a message",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (receiverEmail.isEmpty()) {

                Toast.makeText(
                    this,
                    "Student information not found",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val preferences =
                getSharedPreferences(
                    "CampusSkillSwap",
                    MODE_PRIVATE
                )

            val senderEmail =
                preferences.getString(
                    "current_email",
                    ""
                ) ?: ""

            val senderName =
                preferences.getString(
                    "current_name",
                    "Student"
                ) ?: "Student"

            // Find receiver's name
            val users =
                UserStorage.getUsers(this)

            var receiverName = "Student"

            for (i in 0 until users.length()) {

                val user =
                    users.getJSONObject(i)

                if (user.getString("email")
                        .equals(receiverEmail, ignoreCase = true)
                ) {

                    receiverName =
                        user.getString("name")

                    break
                }
            }

            RequestStorage.saveRequest(
                context = this,
                senderEmail = senderEmail,
                senderName = senderName,
                receiverEmail = receiverEmail,
                receiverName = receiverName,
                skillName = skillName,
                skillIcon = skillIcon,
                teachSkill = teachSkill,
                message = message
            )

            Toast.makeText(
                this,
                "Request sent to $receiverName! 🎉",
                Toast.LENGTH_LONG
            ).show()

            finish()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }
}