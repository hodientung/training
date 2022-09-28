package com.example.voicelockscreen

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.voicelockscreen.permission.ManagePermissions
import com.example.voicelockscreen.view.BaseActivity
import com.example.voicelockscreen.view.VoiceLockFragment

private const val REQUEST_OVERLAY_PERMISSION = 155

class MainActivity : BaseActivity() {

    private lateinit var managePermissions: ManagePermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setupPermission()
        supportFragmentManager.beginTransaction().add(R.id.content_frame, VoiceLockFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setFlags(1024, 1024)

    }

    private fun setupPermission() {
        val list = listOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        checkOverlayPermission()
        managePermissions = ManagePermissions(this, list, 123)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            managePermissions.checkPermissions()
    }

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // send user to the device settings
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivityForResult(myIntent, REQUEST_OVERLAY_PERMISSION)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
//                    Toast.makeText(
//                        this,
//                        "Display other app permission already granted.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
                    // permission not granted...
                }
            }

        }
    }
}