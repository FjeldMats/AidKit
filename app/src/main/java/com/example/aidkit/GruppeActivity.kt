package com.example.aidkit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class GruppeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gruppe)

        val int = intent
        findViewById<TextView>(R.id.GruppeNavn).text = intent.getStringExtra("navn")
        findViewById<TextView>(R.id.GruppeLink).text = intent.getStringExtra("link")
        findViewById<TextView>(R.id.gruppeDesc).text = intent.getStringExtra("desc")


    }
}
