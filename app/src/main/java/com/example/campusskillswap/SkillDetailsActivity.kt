package com.example.campusskillswap

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class SkillDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill_details)

        val skillName = intent.getStringExtra("skill_name") ?: "Skill"
        val skillIcon = intent.getStringExtra("skill_icon") ?: "⭐"

        val tvSkillIcon = findViewById<TextView>(R.id.tvSkillIcon)
        val tvSkillName = findViewById<TextView>(R.id.tvSkillName)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val studentContainer =
            findViewById<ConstraintLayout>(R.id.studentContainer)

        val btnSwapRequest = findViewById<Button>(R.id.btnSwapRequest)
        val btnBack = findViewById<Button>(R.id.btnBack)

        tvSkillIcon.text = skillIcon
        tvSkillName.text = skillName

        tvDescription.text =
            "Learn $skillName from fellow students and improve your practical skills through skill exchange and collaboration."

        val preferences =
            getSharedPreferences("CampusSkillSwap", MODE_PRIVATE)

        val currentEmail =
            preferences.getString("current_email", "") ?: ""

        val users = UserStorage.getUsersWhoTeach(this, skillName)

        var previousCardId: Int? = null
        var providerCount = 0

        for (i in 0 until users.length()) {

            val user = users.getJSONObject(i)

            val providerName = user.getString("name")
            val providerEmail = user.getString("email")

            // Don't show current logged-in user
            if (providerEmail.equals(currentEmail, ignoreCase = true)) {
                continue
            }

            providerCount++

            // -----------------------------
            // PROVIDER CARD
            // -----------------------------

            val card = ConstraintLayout(this)
            card.id = View.generateViewId()

            val cardHeight = dpToPx(105)

            val cardParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                cardHeight
            )

            cardParams.topMargin = dpToPx(10)

            if (previousCardId == null) {
                cardParams.topToTop =
                    ConstraintLayout.LayoutParams.PARENT_ID
            } else {
                cardParams.topToBottom = previousCardId!!
            }

            cardParams.startToStart =
                ConstraintLayout.LayoutParams.PARENT_ID

            cardParams.endToEnd =
                ConstraintLayout.LayoutParams.PARENT_ID

            studentContainer.addView(card, cardParams)

            // -----------------------------
            // REQUEST BUTTON
            // -----------------------------

            val requestButton = Button(this)

            requestButton.id = View.generateViewId()
            requestButton.text = "🤝\nRequest"
            requestButton.textSize = 12f

            val buttonParams = ConstraintLayout.LayoutParams(
                dpToPx(95),
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            buttonParams.endToEnd =
                ConstraintLayout.LayoutParams.PARENT_ID

            buttonParams.topToTop =
                ConstraintLayout.LayoutParams.PARENT_ID

            buttonParams.bottomToBottom =
                ConstraintLayout.LayoutParams.PARENT_ID

            card.addView(requestButton, buttonParams)

            // -----------------------------
            // STUDENT INFORMATION
            // -----------------------------

            val studentText = TextView(this)

            studentText.id = View.generateViewId()

            studentText.text =
                "👤 $providerName\n" +
                        "📧 $providerEmail\n" +
                        "⭐ $skillName Provider"

            studentText.textSize = 14f
            studentText.setTextColor(Color.DKGRAY)
            studentText.setPadding(
                dpToPx(4),
                dpToPx(4),
                dpToPx(4),
                dpToPx(4)
            )

            val textParams = ConstraintLayout.LayoutParams(
                0,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            textParams.startToStart =
                ConstraintLayout.LayoutParams.PARENT_ID

            textParams.topToTop =
                ConstraintLayout.LayoutParams.PARENT_ID

            textParams.bottomToBottom =
                ConstraintLayout.LayoutParams.PARENT_ID

            // IMPORTANT:
            // Text ends before Request button
            textParams.endToStart = requestButton.id

            textParams.marginEnd = dpToPx(8)

            card.addView(studentText, textParams)

            // -----------------------------
            // REQUEST CLICK
            // -----------------------------

            requestButton.setOnClickListener {

                val intent =
                    Intent(this, SwapRequestActivity::class.java)

                intent.putExtra("skill_name", skillName)
                intent.putExtra("skill_icon", skillIcon)
                intent.putExtra("receiver_email", providerEmail)

                startActivity(intent)
            }

            previousCardId = card.id
        }

        // -----------------------------
        // NO PROVIDER FOUND
        // -----------------------------

        if (providerCount == 0) {

            val noStudentText = TextView(this)

            noStudentText.id = View.generateViewId()

            noStudentText.text =
                "😔 No other student is currently teaching this skill."

            noStudentText.textSize = 16f
            noStudentText.setTextColor(Color.DKGRAY)

            val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            params.topToTop =
                ConstraintLayout.LayoutParams.PARENT_ID

            params.bottomToBottom =
                ConstraintLayout.LayoutParams.PARENT_ID

            params.startToStart =
                ConstraintLayout.LayoutParams.PARENT_ID

            params.endToEnd =
                ConstraintLayout.LayoutParams.PARENT_ID

            studentContainer.addView(noStudentText, params)
        }

        // Global old request button is no longer needed
        btnSwapRequest.visibility = View.GONE

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}