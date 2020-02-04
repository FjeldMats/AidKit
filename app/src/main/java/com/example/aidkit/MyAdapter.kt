package com.example.aidkit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button

class MyAdapter(val myDataset: Array<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val butt: Button) : RecyclerView.ViewHolder(butt)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
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
        p0.butt.text = myDataset[p1]
    }

}
