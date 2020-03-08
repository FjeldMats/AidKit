package com.example.aidkit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button

class MyGruppeAdapter(val myDataset: Array<Gruppe>) : RecyclerView.Adapter<MyGruppeAdapter.MyViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val butt: Button) : RecyclerView.ViewHolder(butt)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyGruppeAdapter.MyViewHolder {
        // create a new view
        val knapp = LayoutInflater.from(parent.context)
            .inflate(R.layout.knapp, parent, false) as Button
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(knapp)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        p0.butt.text = myDataset[p1].navn
        p0.butt.setOnClickListener {
            val gruppe = Intent(SosialtActivity(), GruppeActivity::class.java).putExtra("navn", myDataset[p1].navn)
            gruppe.putExtra("link", myDataset[p1].link)
            gruppe.putExtra("desc", myDataset[p1].description)
            startActivity(SosialtActivity(), gruppe, null)
        }
    }

}
