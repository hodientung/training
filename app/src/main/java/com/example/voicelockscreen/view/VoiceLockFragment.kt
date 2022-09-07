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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.voicelockscreen.R
import com.example.voicelockscreen.model.DataModelFunction
import com.example.voicelockscreen.permission.ManagePermissions
import com.example.voicelockscreen.service.VoiceLockService
import com.example.voicelockscreen.sharepreference.PreferenceHelper.customPreference
import com.example.voicelockscreen.sharepreference.PreferenceHelper.isSetupVoiceLock
import com.example.voicelockscreen.sharepreference.PreferenceHelper.onService
import com.example.voicelockscreen.utils.Util
import kotlinx.android.synthetic.main.fragment_voice_lock.*
import java.util.ArrayList


class VoiceLockFragment : Fragment() {

    private var isEnableService = false
    private lateinit var managePermissions: ManagePermissions
    private lateinit var adapterFunction: RecyclerViewFunction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPermission()
    }

    private fun setupPermission() {
        val list = listOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, SYSTEM_ALERT_WINDOW
        )
        checkOverlayPermission()
        managePermissions = ManagePermissions(requireActivity(), list, 123)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            managePermissions.checkPermissions()
    }

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(requireContext())) {
                // send user to the device settings
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivity(myIntent)
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
        initView()
        initAction()
    }

    private fun initView() {
        rvFunction.layoutManager = GridLayoutManager(context, 2)
        adapterFunction = RecyclerViewFunction(context)
        adapterFunction.functionList = Util.getFunctionList(resources)
        rvFunction.adapter = adapterFunction
    }

    private fun initAction() {
        val prefs = context?.let { customPreference(it, Util.CUSTOM_PREF_NAME) }
        switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            if (prefs?.isSetupVoiceLock == true) {
                if (isChecked) {
                    isEnableService = true
                    prefs.onService = isEnableService
                    startService()
                } else {
                    isEnableService = false
                    prefs.onService = isEnableService
                    stopService()
                }
            } else {
                switchMaterial.isChecked = false
                Toast.makeText(context, "First Set The Voice Password", Toast.LENGTH_LONG).show()
            }
        }

        adapterFunction.onItemClicked = {
            when (it) {
                0 -> {
                        if (prefs?.isSetupVoiceLock == true)
                        // open fragment enter old password to open voice lock screen setup new password
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.addToBackStack(null)
                                ?.replace(R.id.content_frame, ValidateVoiceLockChangeFragment())
                                ?.commit()
                        else
                        //exchange to security question
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.addToBackStack(null)
                                ?.replace(R.id.content_frame, SecurityQuestionFragment())?.commit()
                }
                1 -> {
                    if (prefs?.isSetupVoiceLock == true)
                        activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                            ?.replace(R.id.content_frame, ValidatePinLockChangeFragment())?.commit()
                    else Toast.makeText(context, "First Set The Voice Password", Toast.LENGTH_LONG)
                        .show()
                }
                2 -> {
                    //to do theme
                }
                3 -> {
                    // refer fragment list theme
                    activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                        ?.replace(R.id.content_frame, ThemeFragment())?.commit()
                }
                4 -> {
                    //Show list folder contain image in storage
                    activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                        ?.replace(R.id.content_frame, ImageFolderFragment())?.commit()
                }
                5 -> {
                    //Show list folder contain video in storage
                    activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                        ?.replace(R.id.content_frame, VideoFolderFragment())?.commit()
                }
                6 -> {

                }
                7 -> {

                }
            }
        }

//        btnStartService.setOnClickListener {
//            isEnableService = true
//            prefs?.onService = isEnableService
//            startService()
//        }
//        btnStopService.setOnClickListener {
//            isEnableService = false
//            prefs?.onService = isEnableService
//            stopService()
//        }
//        btnVoiceLock.setOnClickListener {
//            if (prefs?.isSetupVoiceLock == true)
//            // open fragment enter old password to open voice lock screen setup new password
//                activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
//                    ?.replace(R.id.content_frame, ValidateVoiceLockChangeFragment())?.commit()
//            else
//            //exchange to security question
//            //exchange to setup new password
//                activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
//                    ?.replace(R.id.content_frame, SecurityQuestionFragment())?.commit()
//        }
//
//        btnPinLock.setOnClickListener {
//            if (prefs?.isSetupVoiceLock == true)
//                activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
//                    ?.replace(R.id.content_frame, ValidatePinLockChangeFragment())?.commit()
//            else Toast.makeText(context, "First Set The Voice Password", Toast.LENGTH_LONG).show()
//        }
//
//        btnTheme.setOnClickListener {
//            // refer fragment list theme
//            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
//                ?.replace(R.id.content_frame, ThemeFragment())?.commit()
//        }
//
//        btnVideoGallery.setOnClickListener {
//            //Show list folder contain video in storage
//            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
//                ?.replace(R.id.content_frame, VideoFolderFragment())?.commit()
//        }
//
//        btnImageGallery.setOnClickListener {
//            //Show list folder contain image in storage
//            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
//                ?.replace(R.id.content_frame, ImageFolderFragment())?.commit()
//        }
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
}