package com.example.campusskillswap

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RequestsActivity : AppCompatActivity() {

    private lateinit var incomingContainer: LinearLayout
    private lateinit var sentContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests)

        incomingContainer = findViewById(R.id.incomingContainer)
        sentContainer = findViewById(R.id.sentContainer)

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }

        loadRequests()
    }

    override fun onResume() {
        super.onResume()

        if (::incomingContainer.isInitialized &&
            ::sentContainer.isInitialized
        ) {
            loadRequests()
        }
    }

    private fun loadRequests() {

        incomingContainer.removeAllViews()
        sentContainer.removeAllViews()

        val preferences =
            getSharedPreferences("CampusSkillSwap", MODE_PRIVATE)

        val currentEmail =
            preferences.getString("current_email", "") ?: ""

        if (currentEmail.isEmpty()) {
            return
        }

        // ==============================
        // INCOMING REQUESTS
        // ==============================

        val incomingRequests =
            RequestStorage.getReceivedRequests(
                this,
                currentEmail
            )

        if (incomingRequests.length() == 0) {

            addEmptyMessage(
                incomingContainer,
                "No incoming requests yet 📬"
            )

        } else {

            for (i in 0 until incomingRequests.length()) {

                val request =
                    incomingRequests.getJSONObject(i)

                addIncomingRequest(request)
            }
        }

        // ==============================
        // SENT REQUESTS
        // ==============================

        val sentRequests =
            RequestStorage.getSentRequests(
                this,
                currentEmail
            )

        if (sentRequests.length() == 0) {

            addEmptyMessage(
                sentContainer,
                "No sent requests yet 📤"
            )

        } else {

            for (i in 0 until sentRequests.length()) {

                val request =
                    sentRequests.getJSONObject(i)

                addSentRequest(request)
            }
        }
    }

    // ============================================================
    // INCOMING REQUEST
    // ============================================================

    private fun addIncomingRequest(
        request: org.json.JSONObject
    ) {

        val senderName =
            request.optString(
                "senderName",
                "Student"
            )

        val skillName =
            request.optString(
                "skillName",
                "Skill"
            )

        val teachSkill =
            request.optString(
                "teachSkill",
                "Not specified"
            )

        val message =
            request.optString(
                "message",
                ""
            )

        val status =
            request.optString(
                "status",
                "Pending"
            )

        val requestId =
            request.optString(
                "id",
                ""
            )

        val card = LinearLayout(this)

        card.orientation =
            LinearLayout.VERTICAL

        card.setPadding(
            8,
            8,
            8,
            12
        )

        // Student
        val studentText = TextView(this)

        studentText.text =
            "👤 $senderName"

        studentText.textSize = 16f

        card.addView(studentText)

        // Learning skill
        val learningText = TextView(this)

        learningText.text =
            "📚 Wants to learn: $skillName"

        learningText.textSize = 14f

        card.addView(learningText)

        // Can teach
        val teachText = TextView(this)

        teachText.text =
            "🧠 Can teach: $teachSkill"

        teachText.textSize = 14f

        card.addView(teachText)

        // Message
        if (message.isNotEmpty()) {

            val messageText = TextView(this)

            messageText.text =
                "💬 $message"

            messageText.textSize = 14f

            card.addView(messageText)
        }

        // Status
        val statusText = TextView(this)

        statusText.text =
            "Status: $status"

        statusText.textSize = 14f

        statusText.setPadding(
            0,
            12,
            0,
            8
        )

        card.addView(statusText)

        // ==============================
        // ACCEPT / REJECT
        // ==============================

        if (status.equals("Pending", ignoreCase = true)) {

            val buttonLayout =
                LinearLayout(this)

            buttonLayout.orientation =
                LinearLayout.HORIZONTAL

            val acceptButton =
                Button(this)

            acceptButton.text =
                "ACCEPT ✅"

            val rejectButton =
                Button(this)

            rejectButton.text =
                "REJECT ❌"

            val buttonParams =
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )

            buttonParams.setMargins(
                4,
                0,
                4,
                0
            )

            buttonLayout.addView(
                acceptButton,
                buttonParams
            )

            buttonLayout.addView(
                rejectButton,
                buttonParams
            )

            card.addView(buttonLayout)

            // ACCEPT
            acceptButton.setOnClickListener {

                if (requestId.isNotEmpty()) {

                    RequestStorage.updateRequestStatus(
                        this,
                        requestId,
                        "Accepted"
                    )

                    loadRequests()
                }
            }

            // REJECT
            rejectButton.setOnClickListener {

                if (requestId.isNotEmpty()) {

                    RequestStorage.updateRequestStatus(
                        this,
                        requestId,
                        "Rejected"
                    )

                    loadRequests()
                }
            }
        }

        incomingContainer.addView(card)
    }

    // ============================================================
    // SENT REQUEST
    // ============================================================

    private fun addSentRequest(
        request: org.json.JSONObject
    ) {

        val receiverName =
            request.optString(
                "receiverName",
                "Student"
            )

        val skillName =
            request.optString(
                "skillName",
                "Skill"
            )

        val teachSkill =
            request.optString(
                "teachSkill",
                "Not specified"
            )

        val status =
            request.optString(
                "status",
                "Pending"
            )

        val message =
            request.optString(
                "message",
                ""
            )

        val card = LinearLayout(this)

        card.orientation =
            LinearLayout.VERTICAL

        card.setPadding(
            8,
            8,
            8,
            12
        )

        // Receiver
        val receiverText =
            TextView(this)

        receiverText.text =
            "🤝 Request to: $receiverName"

        receiverText.textSize = 15f

        card.addView(receiverText)

        // Learning
        val learningText =
            TextView(this)

        learningText.text =
            "📚 Learning: $skillName"

        learningText.textSize = 14f

        card.addView(learningText)

        // Can teach
        val teachText =
            TextView(this)

        teachText.text =
            "🧠 You can teach: $teachSkill"

        teachText.textSize = 14f

        card.addView(teachText)

        // Message
        if (message.isNotEmpty()) {

            val messageText =
                TextView(this)

            messageText.text =
                "💬 $message"

            messageText.textSize = 14f

            card.addView(messageText)
        }

        // Status
        val statusText =
            TextView(this)

        statusText.text =
            "Status: $status"

        statusText.textSize = 14f

        statusText.setPadding(
            0,
            12,
            0,
            8
        )

        card.addView(statusText)

        sentContainer.addView(card)
    }

    // ============================================================
    // EMPTY MESSAGE
    // ============================================================

    private fun addEmptyMessage(
        container: LinearLayout,
        message: String
    ) {

        val text =
            TextView(this)

        text.text = message

        text.textSize = 14f

        text.setTextColor(
            android.graphics.Color.GRAY
        )

        text.setPadding(
            8,
            8,
            8,
            16
        )

        container.addView(text)
    }
}