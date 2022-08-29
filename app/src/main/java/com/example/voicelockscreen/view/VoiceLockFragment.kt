package com.example.voicelockscreen.view


import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.fragment.app.Fragment
import com.example.voicelockscreen.R
import com.example.voicelockscreen.service.VoiceLockService
import com.example.voicelockscreen.sharepreference.PreferenceHelper.customPreference
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetupVoiceLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.onService
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_voice_lock.*


class VoiceLockFragment : Fragment() {

    var isEnableService = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkOverlayPermission()
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } == PackageManager.PERMISSION_GRANTED)
            Toast.makeText(context, "Write external storage is granted", Toast.LENGTH_LONG).show()
        else
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
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
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val requiredPermission: String = android.Manifest.permission.RECORD_AUDIO

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
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivityForResult(intent, Util.PERM_REQUEST_CODE_DRAW_OVERLAYS)
            //}
        }
    }

//    private fun promptSpeechInput() {
//        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        intent.putExtra(
//            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//        )
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//        intent.putExtra(
//            RecognizerIntent.EXTRA_PROMPT,
//            getString(R.string.speech_prompt)
//        )
//        try {
//            startActivityForResult(intent, Util.REQ_CODE_SPEECH_INPUT)
//        } catch (a: ActivityNotFoundException) {
//            Toast.makeText(
//                context,
//                getString(R.string.speech_not_supported),
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            Util.REQ_CODE_SPEECH_INPUT -> {
//                if (resultCode == RESULT_OK && data != null) {
//                    val result: ArrayList<String> =
//                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
//                    val prefs = context?.let { customPreference(it, Util.CUSTOM_PREF_NAME) }
//                    prefs?.input = result[0]
//
//                }
//            }
//            else -> {}
//        }
//    }

    companion object {
        //
    }
}