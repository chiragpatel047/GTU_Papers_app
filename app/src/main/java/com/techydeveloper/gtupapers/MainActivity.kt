package com.techydeveloper.gtupapers

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var view_be_btech : View
    lateinit var img_nav : ImageView

    lateinit var database : DatabaseReference
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        view_be_btech = findViewById(R.id.view_be_btech)
        img_nav = findViewById(R.id.img_nav)

        MobileAds.initialize(this) {}

        val adView = AdView(this)
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        database = Firebase.database.getReference("Report")

        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                )
            }
        }

        var navView : View = LayoutInflater.from(this).inflate(R.layout.nav_sheet,null)

        var navBottomSheetDialog : BottomSheetDialog = BottomSheetDialog(this)

        navBottomSheetDialog.setContentView(navView)
        navBottomSheetDialog.create()


        img_nav.setOnClickListener {
            navBottomSheetDialog.show()

            var view_report : View = navView.findViewById(R.id.view_report)
            var view_privacy : View = navView.findViewById(R.id.view_privacy)
            var view_aboutus : View = navView.findViewById(R.id.view_aboutus)
            var view_feedback : View = navView.findViewById(R.id.view_feedback)

            view_report.setOnClickListener {
                navBottomSheetDialog.dismiss()

                var reportView : View = LayoutInflater.from(this).inflate(R.layout.report_dialog,null)

                var reportDialog : AlertDialog = AlertDialog.Builder(this)
                    .setView(reportView)
                    .create()

                reportDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                reportDialog.show()

                var report_submit : TextView = reportView.findViewById(R.id.report_submit)
                var report_editText : EditText = reportView.findViewById(R.id.report_editText)

                report_submit.setOnClickListener {

                    reportDialog.dismiss()
                    var report : String = report_editText.text.toString()

                    var reportModel : ReportModel = ReportModel(report)

                    database.push().setValue(reportModel).addOnSuccessListener {

                        Toast.makeText(this,"Successfully submit",Toast.LENGTH_LONG).show()

                    }

                }

            }

            view_privacy.setOnClickListener {
                navBottomSheetDialog.dismiss()

                var intent : Intent = Intent(this,Privacy::class.java)
                startActivity(intent)

            }

            view_feedback.setOnClickListener {
                navBottomSheetDialog.dismiss()

                var reportView : View = LayoutInflater.from(this).inflate(R.layout.report_dialog,null)

                var reportDialog : AlertDialog = AlertDialog.Builder(this)
                    .setView(reportView)
                    .create()

                reportDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                reportDialog.show()

                var report_submit : TextView = reportView.findViewById(R.id.report_submit)
                var report_indicator : TextView = reportView.findViewById(R.id.report_indicator)
                var report_editText : EditText = reportView.findViewById(R.id.report_editText)

                report_editText.setHint("Describe feedback here...")
                report_indicator.text="Feedback"

                report_submit.setOnClickListener {

                    reportDialog.dismiss()
                    var report : String = report_editText.text.toString()

                    var reportModel : ReportModel = ReportModel(report)

                    database.push().setValue(reportModel).addOnSuccessListener {

                        Toast.makeText(this,"Successfully submit",Toast.LENGTH_LONG).show()

                    }

                }

            }

            view_aboutus.setOnClickListener {
                navBottomSheetDialog.dismiss()

                var aboutView : View = LayoutInflater.from(this).inflate(R.layout.about_dialog,null)

                var aboutDialog : BottomSheetDialog = BottomSheetDialog(this)
                aboutDialog.setContentView(aboutView)
                aboutDialog.create()

                aboutDialog.show()
            }

        }

        view_be_btech.setOnClickListener {
            var intent : Intent = Intent(this,Branch::class.java)
            intent.putExtra("COURSE","B.E / B.TECH")
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        //Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    //Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

}