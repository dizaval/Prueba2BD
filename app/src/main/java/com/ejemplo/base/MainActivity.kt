package com.ejemplo.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ejemplo.base.databinding.ActivityMainBinding
import com.ejemplo.base.db.FirebaseUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val TAG = "FIRESTORE"

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // we'll call functions here
        uploadData()
        readData()
    }

    private fun uploadData() {
        binding!!.btnUploadData.setOnClickListener {

            // create a dummy data
            val hashMap = hashMapOf<String, Any>(
                "name" to "Gonsa",
                "city" to "Nairobi",
                "age" to 0
            )

            // use the add() method to create a document inside users collection
            FirebaseUtils().fireStoreDatabase.collection("users")
                .add(hashMap)
                .addOnSuccessListener {
                    Log.d(TAG, "Added document with ID ${it.id}")
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error adding document $exception")
                }
        }
    }

    private fun readData(){
        binding!!.btnReadData.setOnClickListener {
            FirebaseUtils().fireStoreDatabase.collection("users")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.forEach { document ->
                        Log.d(TAG, "Read document with ID ${document.id}")
                        binding!!.textView1.text = "${document.data}"
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents $exception")
                }
        }

    }

}