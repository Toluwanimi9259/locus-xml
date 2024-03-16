package com.techafresh.locusx

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.birjuvachhani.locus.Locus
import com.birjuvachhani.locus.isDenied
import com.birjuvachhani.locus.isFatal
import com.birjuvachhani.locus.isPermanentlyDenied
import com.birjuvachhani.locus.isSettingsDenied
import com.birjuvachhani.locus.isSettingsResolutionFailed
import com.techafresh.locusx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Locus.setLogging(true)

        Locus.startLocationUpdates(this@MainActivity){
            it.location?.let {
                Log.d("TAG", "onCreate: ${it.latitude} \n Provider = ${it.provider}")
            }
            it.error?.let {error ->
                Log.d("TAG", "onCreate: Error = ${error.message}")
                when {
                    error.isDenied -> {
                    /* When permission is denied */
                        setLog("Permission Denied!!" , applicationContext)
                    }
                    error.isPermanentlyDenied -> {
                    /* When permission is permanently denied */
                        setLog("Permission Permanently Denied!!" , applicationContext)
                    }
                    error.isSettingsResolutionFailed -> { /* When location settings resolution is failed */ }
                    error.isSettingsDenied -> { /* When user denies to allow required location settings */ }
                    error.isFatal -> { /* None of above */ }
                }
            }
        }

    }
}

fun setLog(message : String , context: Context){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    Log.d("SETLOG", "setLog: $message")
}