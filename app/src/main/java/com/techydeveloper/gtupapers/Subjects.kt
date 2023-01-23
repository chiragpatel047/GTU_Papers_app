package com.techydeveloper.gtupapers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Subjects : AppCompatActivity() {

    lateinit var branch_name_txt : TextView
    lateinit var firebase : DatabaseReference
    lateinit var subjectArrayList : ArrayList<SubjectModel>
    lateinit var subjectAdapter: SubjectAdapter

    lateinit var subject_recv : RecyclerView

    lateinit var lottieView1 : LottieAnimationView

    companion object{
        lateinit var sem_name : String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects)

        supportActionBar?.hide()

        sem_name= intent.getStringExtra("SEMNAME")!!;

        branch_name_txt = findViewById(R.id.branch_name)
        subject_recv = findViewById(R.id.subject_recv)

        lottieView1 = findViewById(R.id.lottieView1)

        branch_name_txt.text = sem_name

        subject_recv.layoutManager = LinearLayoutManager(this)

        subjectArrayList = ArrayList()
        subjectAdapter = SubjectAdapter(this,subjectArrayList)

        subject_recv.adapter=subjectAdapter

        firebase = Firebase.database.getReference("Branch")
            .child(Sems.branch_name)
            .child("SemList")
            .child(sem_name)
            .child("SubjectList")

        firebase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(singleShot : DataSnapshot in snapshot.children){
                    var subjectModel : SubjectModel = singleShot.getValue(SubjectModel::class.java)!!
                    subjectArrayList.add(subjectModel)
                }
                subjectAdapter.notifyDataSetChanged()
                lottieView1.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}