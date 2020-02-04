package com.example.aidkit


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class OkonomiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.knapp_liste)

        val okonomi : Array<String> = arrayOf("Lånekassen", "Budkjett", "Mat","a","b","c","a","b","c","a","b","c","ølllll");

        var viewManager = LinearLayoutManager(this)
        var viewAdapter = MyAdapter(okonomi)

        val recyclerView = findViewById<RecyclerView>(R.id.KnappListe).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(false)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

    }
}
