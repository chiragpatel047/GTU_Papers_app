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

class Sems : AppCompatActivity() {


    lateinit var sem_recv : RecyclerView
    lateinit var firebase : DatabaseReference
    lateinit var semArrayList : ArrayList<SemModel>
    lateinit var semAdapter: SemAdapter

    lateinit var sem_name: TextView

    lateinit var lottieView1 : LottieAnimationView

    companion object{
        lateinit var branch_name : String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sems)

        supportActionBar?.hide()

        sem_recv = findViewById(R.id.sem_recv)
        sem_name = findViewById(R.id.sem_name)

        lottieView1 = findViewById(R.id.lottieView1)

        branch_name = intent.getStringExtra("BRANCH_NAME")!!;

        sem_name.text = branch_name

        sem_recv.layoutManager = LinearLayoutManager(this)

        semArrayList = ArrayList()
        semAdapter = SemAdapter(this,semArrayList)

        sem_recv.adapter = semAdapter

        firebase = Firebase.database.getReference("Branch")
            .child(branch_name)
            .child("SemList")

        firebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(singleShot : DataSnapshot in snapshot.children){
                    var semModel : SemModel = singleShot.getValue(SemModel::class.java)!!
                    semArrayList.add(semModel)
                }

                semAdapter.notifyDataSetChanged()
                lottieView1.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}