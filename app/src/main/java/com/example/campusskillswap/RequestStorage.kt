package com.example.campusskillswap

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object RequestStorage {

    private const val PREF_NAME = "CampusSkillSwap"
    private const val REQUESTS_KEY = "requests"

    // ============================================================
    // SAVE NEW REQUEST
    // ============================================================

    fun saveRequest(
        context: Context,
        senderEmail: String,
        senderName: String,
        receiverEmail: String,
        receiverName: String,
        skillName: String,
        skillIcon: String,
        teachSkill: String,
        message: String
    ) {

        val preferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val requests =
            JSONArray(
                preferences.getString(
                    REQUESTS_KEY,
                    "[]"
                )
            )

        val request = JSONObject()

        request.put(
            "id",
            System.currentTimeMillis().toString()
        )

        request.put(
            "senderEmail",
            senderEmail
        )

        request.put(
            "senderName",
            senderName
        )

        request.put(
            "receiverEmail",
            receiverEmail
        )

        request.put(
            "receiverName",
            receiverName
        )

        request.put(
            "skillName",
            skillName
        )

        request.put(
            "skillIcon",
            skillIcon
        )

        request.put(
            "teachSkill",
            teachSkill
        )

        request.put(
            "message",
            message
        )

        request.put(
            "status",
            "Pending"
        )

        requests.put(request)

        preferences.edit()
            .putString(
                REQUESTS_KEY,
                requests.toString()
            )
            .apply()
    }

    // ============================================================
    // GET ALL REQUESTS
    // ============================================================

    fun getRequests(
        context: Context
    ): JSONArray {

        val preferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        return JSONArray(
            preferences.getString(
                REQUESTS_KEY,
                "[]"
            )
        )
    }

    // ============================================================
    // GET SENT REQUESTS
    // ============================================================

    fun getSentRequests(
        context: Context,
        email: String
    ): JSONArray {

        val allRequests =
            getRequests(context)

        val sentRequests =
            JSONArray()

        for (i in 0 until allRequests.length()) {

            val request =
                allRequests.getJSONObject(i)

            if (
                request.getString(
                    "senderEmail"
                ).equals(
                    email,
                    ignoreCase = true
                )
            ) {

                sentRequests.put(request)
            }
        }

        return sentRequests
    }

    // ============================================================
    // GET RECEIVED REQUESTS
    // ============================================================

    fun getReceivedRequests(
        context: Context,
        email: String
    ): JSONArray {

        val allRequests =
            getRequests(context)

        val receivedRequests =
            JSONArray()

        for (i in 0 until allRequests.length()) {

            val request =
                allRequests.getJSONObject(i)

            if (
                request.getString(
                    "receiverEmail"
                ).equals(
                    email,
                    ignoreCase = true
                )
            ) {

                receivedRequests.put(request)
            }
        }

        return receivedRequests
    }

    // ============================================================
    // UPDATE REQUEST STATUS
    // ============================================================

    fun updateRequestStatus(
        context: Context,
        requestId: String,
        newStatus: String
    ) {

        val preferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val requests =
            JSONArray(
                preferences.getString(
                    REQUESTS_KEY,
                    "[]"
                )
            )

        for (i in 0 until requests.length()) {

            val request =
                requests.getJSONObject(i)

            if (
                request.getString("id") == requestId
            ) {

                request.put(
                    "status",
                    newStatus
                )

                break
            }
        }

        preferences.edit()
            .putString(
                REQUESTS_KEY,
                requests.toString()
            )
            .apply()
    }
}