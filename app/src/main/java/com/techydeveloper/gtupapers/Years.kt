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
import com.google.firebase.ktx.Firebase

class Years : AppCompatActivity() {

    lateinit var subject_name : TextView
    lateinit var paper_recv : RecyclerView
    lateinit var paperAdapter: YearPaperAdapter
    lateinit var paperArrayList : ArrayList<PaperModel>

    lateinit var firebase : DatabaseReference

    lateinit var lottieView1 : LottieAnimationView

    companion object{
        lateinit var subName : String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_years)

        supportActionBar?.hide()

        subject_name = findViewById(R.id.subject_name)
        paper_recv = findViewById(R.id.paper_recv)

        lottieView1 = findViewById(R.id.lottieView1)

        subName= intent.getStringExtra("SUBNAME")!!
        subject_name.text = subName

        paperArrayList = ArrayList()

        paperAdapter = YearPaperAdapter(this,paperArrayList)

        paper_recv.layoutManager = LinearLayoutManager(this)
        paper_recv.adapter = paperAdapter

        firebase = Firebase.database.getReference("Branch")
            .child(Sems.branch_name)
            .child("SemList")
            .child(Subjects.sem_name)
            .child("SubjectList")
            .child(subName)
            .child("PaperList")

        firebase.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                for(singleShot : DataSnapshot in snapshot.children){
                    var paperModel : PaperModel = singleShot.getValue(PaperModel::class.java)!!
                    paperArrayList.add(paperModel)
                }
                paperAdapter.notifyDataSetChanged()
                lottieView1.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}