package com.techydeveloper.gtupapers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

class Branch : AppCompatActivity() {

    lateinit var branch_recv : RecyclerView
    lateinit var branchAdapter: BranchAdapter
    lateinit var branchArrayList: ArrayList<BranchModel>
    lateinit var course_name : TextView

    lateinit var firebase : DatabaseReference
    lateinit var lottieView1 : LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch)

        supportActionBar?.hide()

        branch_recv = findViewById(R.id.branch_recv)
        course_name = findViewById(R.id.course_name)

        lottieView1 = findViewById(R.id.lottieView1)

        var action_bar_name : String= intent.getStringExtra("COURSE")!!

        course_name.text = action_bar_name

        branchArrayList = ArrayList()
        branchAdapter = BranchAdapter(this,branchArrayList)

        branch_recv.layoutManager = LinearLayoutManager(this)
        branch_recv.adapter=branchAdapter

        firebase = Firebase.database.getReference("Branch")

        firebase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(singleShot : DataSnapshot in snapshot.children){
                    var branchModel : BranchModel? = singleShot.getValue(BranchModel::class.java)
                    branchArrayList.add(branchModel!!)
                }

                branchAdapter.notifyDataSetChanged()
                lottieView1.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}