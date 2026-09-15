package com.example.campusskillswap

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ExploreActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var btnAndroid: Button
    private lateinit var btnWeb: Button
    private lateinit var btnPython: Button
    private lateinit var btnDesign: Button
    private lateinit var btnPhotography: Button
    private lateinit var btnGuitar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_explore)

        etSearch = findViewById(R.id.etSearch)

        btnAndroid = findViewById(R.id.btnAndroid)
        btnWeb = findViewById(R.id.btnWeb)
        btnPython = findViewById(R.id.btnPython)
        btnDesign = findViewById(R.id.btnDesign)
        btnPhotography = findViewById(R.id.btnPhotography)
        btnGuitar = findViewById(R.id.btnGuitar)

        btnAndroid.setOnClickListener {
            openSkillDetails("Android Development", "💻")
        }

        btnWeb.setOnClickListener {
            openSkillDetails("Web Development", "🌐")
        }

        btnPython.setOnClickListener {
            openSkillDetails("Python", "🐍")
        }

        btnDesign.setOnClickListener {
            openSkillDetails("Graphic Design", "🎨")
        }

        btnPhotography.setOnClickListener {
            openSkillDetails("Photography", "📸")
        }

        btnGuitar.setOnClickListener {
            openSkillDetails("Guitar", "🎸")
        }

        etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                filterSkills(
                    s.toString().trim().lowercase()
                )
            }

            override fun afterTextChanged(
                s: Editable?
            ) {
            }
        })
    }

    private fun filterSkills(searchText: String) {

        if (searchText.isEmpty()) {

            btnAndroid.visibility = View.VISIBLE
            btnWeb.visibility = View.VISIBLE
            btnPython.visibility = View.VISIBLE
            btnDesign.visibility = View.VISIBLE
            btnPhotography.visibility = View.VISIBLE
            btnGuitar.visibility = View.VISIBLE

            return
        }

        btnAndroid.visibility =
            if (
                "android development".contains(searchText) ||
                "android".contains(searchText)
            ) View.VISIBLE
            else View.GONE

        btnWeb.visibility =
            if (
                "web development".contains(searchText) ||
                "web".contains(searchText)
            ) View.VISIBLE
            else View.GONE

        btnPython.visibility =
            if (
                "python".contains(searchText)
            ) View.VISIBLE
            else View.GONE

        btnDesign.visibility =
            if (
                "graphic design".contains(searchText) ||
                "design".contains(searchText)
            ) View.VISIBLE
            else View.GONE

        btnPhotography.visibility =
            if (
                "photography".contains(searchText) ||
                "photo".contains(searchText)
            ) View.VISIBLE
            else View.GONE

        btnGuitar.visibility =
            if (
                "guitar".contains(searchText)
            ) View.VISIBLE
            else View.GONE
    }

    private fun openSkillDetails(
        skillName: String,
        skillIcon: String
    ) {

        val intent =
            Intent(
                this,
                SkillDetailsActivity::class.java
            )

        intent.putExtra(
            "skill_name",
            skillName
        )

        intent.putExtra(
            "skill_icon",
            skillIcon
        )

        startActivity(intent)
    }
}