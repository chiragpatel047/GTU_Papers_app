package com.techydeveloper.gtupapers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class YearPaperAdapter(myContext: Context, myList : ArrayList<PaperModel>) : RecyclerView.Adapter<YearPaperAdapter.YearViewModel>() {

    var context : Context = myContext
    var paperArrayList : ArrayList<PaperModel> = myList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewModel {
        var view : View = LayoutInflater.from(context).inflate(R.layout.single_brach_layout,parent,false)
        return YearViewModel(view)
    }

    override fun onBindViewHolder(holder: YearViewModel, position: Int) {
        var singlePaper : PaperModel = paperArrayList.get(position)

        holder.subject_name.text = singlePaper.paper_name
        holder.subject_logo.setImageResource(R.drawable.file)

        holder.itemView.setOnClickListener {
            var intent : Intent = Intent(context,Full_pdf::class.java)
            intent.putExtra("YEAR_NAME",singlePaper.paper_name)
            intent.putExtra("PAPER_LINK",singlePaper.paper_link)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return paperArrayList.size
    }

    public class YearViewModel(itemView: View) : RecyclerView.ViewHolder(itemView){

        var subject_logo : ImageView = itemView.findViewById(R.id.branch_logo)
        var subject_name : TextView = itemView.findViewById(R.id.branch_name)

    }

}