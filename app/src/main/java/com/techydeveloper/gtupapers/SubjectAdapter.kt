package com.techydeveloper.gtupapers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubjectAdapter(myContext: Context, myList : ArrayList<SubjectModel>) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    var context : Context = myContext
    var subjectArrayList : ArrayList<SubjectModel> = myList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.single_brach_layout,parent,false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        var singleSubject : SubjectModel = subjectArrayList.get(position)

        holder.subject_name.text = singleSubject.subject_name

        holder.itemView.setOnClickListener {
            var intent : Intent = Intent(context,Years::class.java)
            intent.putExtra("SUBNAME",singleSubject.subject_name)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return subjectArrayList.size
    }

    public class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var subject_logo : ImageView = itemView.findViewById(R.id.branch_logo)
        var subject_name : TextView = itemView.findViewById(R.id.branch_name)

    }

}