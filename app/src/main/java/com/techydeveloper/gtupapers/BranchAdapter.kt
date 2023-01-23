package com.techydeveloper.gtupapers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BranchAdapter(myContext: Context, myList : ArrayList<BranchModel>) : RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {

    var context : Context = myContext
    var branchArrayList : ArrayList<BranchModel> = myList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.single_brach_layout,parent,false)
        return BranchViewHolder(view)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        var singleBranch : BranchModel = branchArrayList.get(position)

        holder.brach_name.text = singleBranch.branch_name

        Glide.with(context)
            .load(singleBranch.branch_logo)
            .into(holder.brach_logo)

        holder.itemView.setOnClickListener {

            var intent : Intent = Intent(context,Sems::class.java)
            intent.putExtra("BRANCH_NAME",singleBranch.branch_name)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return branchArrayList.size
    }

    public class BranchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var brach_logo : ImageView= itemView.findViewById(R.id.branch_logo)
        var brach_name : TextView= itemView.findViewById(R.id.branch_name)

    }
}