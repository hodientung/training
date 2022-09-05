package com.example.voicelockscreen.view


import android.Manifest
import android.Manifest.permission.SYSTEM_ALERT_WINDOW
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.fragment.app.Fragment
import com.example.voicelockscreen.R
import com.example.voicelockscreen.permission.ManagePermissions
import com.example.voicelockscreen.service.VoiceLockService
import com.example.voicelockscreen.sharepreference.PreferenceHelper.customPreference
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetupVoiceLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.onService
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_voice_lock.*


class VoiceLockFragment : Fragment() {

    private var isEnableService = false
    private lateinit var managePermissions: ManagePermissions
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // Handle Permission granted/rejected
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted) {
                    // Permission is granted
                } else {
                    // Permission is denied
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = listOf(Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, SYSTEM_ALERT_WINDOW)
        managePermissions = ManagePermissions(requireActivity(),list,123)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            managePermissions.checkPermissions()
//        activityResultLauncher.launch(
//            list.toTypedArray()
//        )
//        checkOverlayPermission()

//        if (context?.let {
//                checkCallingOrSelfPermission(
//                    it,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//            } == PackageManager.PERMISSION_GRANTED)
//            Toast.makeText(context, "Write external storage is granted", Toast.LENGTH_LONG).show()
//        else
//            activity?.let {
//                ActivityCompat.requestPermissions(
//                    it,
//                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                    1
//                )
//            }
    }

    private fun requestPermission(permissionType: String, requestCode: Int) {
        val permission = ContextCompat.checkSelfPermission(
            requireActivity(),
            permissionType
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(permissionType), requestCode
            )
        } else
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(permissionType), requestCode
            )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if (grantResults.isEmpty() || grantResults[0]
                    != PackageManager.PERMISSION_GRANTED
                ) {

                    Toast.makeText(
                        requireContext(),
                        "Record permission required",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    requestPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        102
                    )
                }
                return
            }


            102 -> {
                val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    val showRotation = shouldShowRequestPermissionRationale(permission)
                    if (!showRotation) {
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("App Permission")
                            .setMessage(
                                "You must allow this app to access video files on your device" + "\n\n" + "Now follow the below steps" +
                                        "\n\n" + "Open settings from below button" + "\n" + "Clock on Permissions" + "\n" + "Allow access for storage"
                            ).setPositiveButton(
                                "Open settings"
                            ) { _, _ ->
                                val intent =
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", context?.packageName, null)
                                intent.data = uri
                                startActivityForResult(intent, 12)
                            }.create().show()
                    } else {
                        activity?.let {
                            ActivityCompat.requestPermissions(
                                it,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 102
                            )
                        }
                    }
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.fragment_voice_lock,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        val prefs = context?.let { customPreference(it, Util.CUSTOM_PREF_NAME) }

        btnStartService.setOnClickListener {
            isEnableService = true
            prefs?.onService = isEnableService
            startService()
        }
        btnStopService.setOnClickListener {
            isEnableService = false
            prefs?.onService = isEnableService
            stopService()
        }
        btnVoiceLock.setOnClickListener {
            if (prefs?.isSetupVoiceLock == true)
            // open fragment enter old password to open voice lock screen setup new password
                activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                    ?.replace(R.id.content_frame, ValidateVoiceLockChangeFragment())?.commit()
            else
            //exchange to setup new password
                activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                    ?.replace(R.id.content_frame, AlternativeLockFragment())?.commit()
        }

        btnPinLock.setOnClickListener {
            if (prefs?.isSetupVoiceLock == true)
                activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                    ?.replace(R.id.content_frame, ValidatePinLockChangeFragment())?.commit()
            else Toast.makeText(context, "First Set The Voice Password", Toast.LENGTH_LONG).show()
        }

        btnTheme.setOnClickListener {
            // refer fragment list theme
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.content_frame, ThemeFragment())?.commit()
        }

        btnVideoGallery.setOnClickListener {
            //Show list folder contain video in storage
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.content_frame, VideoFolderFragment())?.commit()
        }

        btnImageGallery.setOnClickListener {
            //Show list folder contain image in storage
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.content_frame, ImageFolderFragment())?.commit()
        }
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(context)) {
            val intent = Intent(context, VoiceLockService::class.java)
            context?.startService(intent)
        }
    }

    private fun stopService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(context)) {
            val intent = Intent(context, VoiceLockService::class.java)
            context?.stopService(intent)
        }
    }

    private fun checkOverlayPermission() {
        val requiredPermission: String = Manifest.permission.RECORD_AUDIO
        // If the user previously denied this permission then show a message explaining why
        // this permission is needed
        if (activity?.let {
                checkCallingOrSelfPermission(
                    it,
                    requiredPermission
                )
            } == PackageManager.PERMISSION_DENIED) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(requiredPermission),
                    101
                )
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivityForResult(intent, Util.PERM_REQUEST_CODE_DRAW_OVERLAYS)
            //}
        }
    }
}