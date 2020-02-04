package com.example.aidkit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn1 = findViewById<Button>(R.id.HelseBtn)
        val btn2 = findViewById<Button>(R.id.SosialtBtn)
        val btn3 = findViewById<Button>(R.id.OkonomiBtn)


        val helseActivity = Intent(this, HelseActivity::class.java)
        val sosialtActivity = Intent(this, SosialtActivity::class.java)
        val okonomiActivity = Intent(this, OkonomiActivity::class.java)

        btn1.setOnClickListener{
            startActivity(helseActivity)
        }

        btn2.setOnClickListener{
            startActivity(sosialtActivity)
        }

        btn3.setOnClickListener{
            startActivity(okonomiActivity)
        }

    }
}
