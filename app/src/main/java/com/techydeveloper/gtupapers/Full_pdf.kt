package com.techydeveloper.gtupapers

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class Full_pdf : AppCompatActivity() {

    lateinit var pdfView : PDFView

    var storageRef: StorageReference? = null
    var storage: FirebaseStorage? = null

    lateinit var year_name : TextView
    lateinit var img_download : ImageView

    lateinit var paperName : String
    lateinit var paperLink : String

    private var mRewardedAd: RewardedAd? = null

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_full_pdf)

        supportActionBar?.hide()

        pdfView = findViewById(R.id.pdfView)
        pdfView.setBackgroundColor(Color.WHITE)

        year_name = findViewById(R.id.year_name)
        img_download = findViewById(R.id.img_download)

        paperName = intent.getStringExtra("YEAR_NAME")!!
        paperLink = intent.getStringExtra("PAPER_LINK")!!

        year_name.text = paperName

        val adView = AdView(this)
        adView.adUnitId = "ca-app-pub-6633507436089608/5751184818"

        mAdView = findViewById(R.id.adView)
        val bannerAdRequest = AdRequest.Builder().build()
        
        mAdView.loadAd(bannerAdRequest)

        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(this,"ca-app-pub-6633507436089608/9367281607", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mRewardedAd = null
            }
            override fun onAdLoaded(rewardedAd: RewardedAd) {
                mRewardedAd = rewardedAd
            }
        })

        storage = Firebase.storage

        storageRef = storage!!.reference.child("PaperPdf/"+Sems.branch_name+"/"+Subjects.sem_name+"/"+Years.subName+"/"+paperName)

        var loadingView : View = LayoutInflater.from(this).inflate(R.layout.loading_dialog,null)

        var loadingDialog : AlertDialog = AlertDialog.Builder(this).setView(loadingView).create()

        loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        loadingDialog.show()

        storageRef!!.stream.addOnSuccessListener() {
            pdfView.fromStream(it.getStream()).load()

            loadingDialog.dismiss()

        }.addOnFailureListener {

            Toast.makeText(this,"exception : "+it.message,Toast.LENGTH_LONG).show()
        }

        img_download.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this@Full_pdf,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) !==
                PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@Full_pdf,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        this@Full_pdf,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this@Full_pdf,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                    )
                }
            }else{
                if (mRewardedAd != null) {
                    mRewardedAd?.show(this, OnUserEarnedRewardListener() {

                        Toast.makeText(this,"Downloading",Toast.LENGTH_LONG).show()
                        downloadPDF()

                        fun onUserEarnedReward(rewardItem: RewardItem) {
                            var rewardAmount = rewardItem.amount
                            var rewardType = rewardItem.type
                        }
                    })

                }else{

                    Toast.makeText(this,"Downloading",Toast.LENGTH_LONG).show()
                    downloadPDF()

                }
            }
        }
    }

    fun downloadPDF(){

        val downloadRequest : DownloadManager.Request = DownloadManager.Request(Uri.parse(paperLink))

        downloadRequest.setTitle("GTU Paper")
        downloadRequest.setDescription(paperName + ".pdf")
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        downloadRequest.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            paperName + ".pdf"
        )

        val downloadManager: DownloadManager =
            getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        downloadManager.enqueue(downloadRequest)
        Toast.makeText(this, "Downloading ... ", Toast.LENGTH_LONG).show()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@Full_pdf,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) ===
                                PackageManager.PERMISSION_GRANTED)) {

                        downloadPDF()

                        //Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please allow permission to download", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

}