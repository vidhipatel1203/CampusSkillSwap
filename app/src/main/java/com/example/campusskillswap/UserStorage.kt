package com.example.campusskillswap

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object UserStorage {

    private const val PREF_NAME = "CampusSkillSwap"
    private const val USERS_KEY = "users"

    // ============================================================
    // SAVE USER
    // ============================================================

    fun saveUser(
        context: Context,
        name: String,
        email: String,
        password: String,
        skills: List<String>
    ) {

        val preferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val users =
            JSONArray(
                preferences.getString(
                    USERS_KEY,
                    "[]"
                )
            )

        val user = JSONObject()

        user.put("name", name)
        user.put("email", email)
        user.put("password", password)

        val skillArray = JSONArray()

        for (skill in skills) {
            skillArray.put(skill)
        }

        user.put("skills", skillArray)

        users.put(user)

        preferences.edit()
            .putString(
                USERS_KEY,
                users.toString()
            )
            .apply()
    }

    // ============================================================
    // GET ALL USERS
    // ============================================================

    fun getUsers(
        context: Context
    ): JSONArray {

        val preferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        return JSONArray(
            preferences.getString(
                USERS_KEY,
                "[]"
            )
        )
    }

    // ============================================================
    // GET USERS WHO TEACH A SKILL
    // ============================================================

    fun getUsersWhoTeach(
        context: Context,
        skill: String
    ): JSONArray {

        val users =
            getUsers(context)

        val matchingUsers =
            JSONArray()

        for (i in 0 until users.length()) {

            val user =
                users.getJSONObject(i)

            val skills =
                user.getJSONArray("skills")

            for (j in 0 until skills.length()) {

                if (
                    skills.getString(j)
                        .equals(
                            skill,
                            ignoreCase = true
                        )
                ) {

                    matchingUsers.put(user)

                    break
                }
            }
        }

        return matchingUsers
    }

    // ============================================================
    // UPDATE PASSWORD
    // ============================================================

    fun updatePassword(
        context: Context,
        email: String,
        newPassword: String
    ): Boolean {

        val preferences =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val users =
            JSONArray(
                preferences.getString(
                    USERS_KEY,
                    "[]"
                )
            )

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

                user.put(
                    "password",
                    newPassword
                )

                preferences.edit()
                    .putString(
                        USERS_KEY,
                        users.toString()
                    )
                    .apply()

                return true
            }
        }

        return false
    }
}