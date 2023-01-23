package com.techydeveloper.gtupapers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SemAdapter(myContext: Context, myList : ArrayList<SemModel>) : RecyclerView.Adapter<SemAdapter.SemViewHolder>() {

    var context : Context = myContext
    var semArrayList : ArrayList<SemModel> = myList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.single_brach_layout,parent,false)
        return SemAdapter.SemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SemViewHolder, position: Int) {
        var singleSem : SemModel = semArrayList.get(position)
        holder.sem_name.text = singleSem.sem_name

        holder.itemView.setOnClickListener {
            var intent : Intent = Intent(context,Subjects::class.java)
            intent.putExtra("SEMNAME",singleSem.sem_name)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return semArrayList.size
    }
    class SemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sem_logo : ImageView = itemView.findViewById(R.id.branch_logo)
        var sem_name : TextView = itemView.findViewById(R.id.branch_name)
    }
}