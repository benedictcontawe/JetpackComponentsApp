package com.example.jetpackcomponentsapp

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object ManifestPermission {

    private val TAG = ManifestPermission::class.java.simpleName

    const val SETTINGS_PERMISSION_CODE = 1000
    const val CONTACT_PERMISSION_CODE = 1007

    val contactPermission =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                arrayOf(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_PHONE_NUMBERS
                )
            } else {
                arrayOf(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS
                )
            }

    fun checkSelfPermission(context : Context, permissions : Array<String>, isGranted : () -> Unit = {}, isDenied : () -> Unit = {}) {
        Log.d(TAG,"checkSelfPermission($context,${permissions.contentToString()},isGranted(),isDenied())")
        if (permissions.filter { permission -> ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED}.isEmpty()) {
            Log.d(TAG,"allGranted()")
            isGranted()
        }
        else {
            Log.d(TAG,"denied()")
            isDenied()
        }
    }

    fun requestPermissions(activity : Activity, permissions : Array<String>, requestCode : Int) {
        Log.d(TAG,"requestPermissions($activity,${permissions.contentToString()},$requestCode")
        ActivityCompat.requestPermissions(activity, permissions,requestCode)
    }

    fun checkPermissionsResult(activity : Activity, permissions : Array<String>, grantResults : IntArray, isGranted : () -> Unit = {}, isNeverAskAgain : () -> Unit = {}, isDenied : () -> Unit) {
        when {
            grantResults.all { results -> results ==  PackageManager.PERMISSION_GRANTED} -> {
                Log.d(TAG,"isGranted()")
                isGranted()
            }
            permissions.filter { permission -> ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) && ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED }.none() -> {
                Log.d(TAG,"isNeverAskAgain()")
                isNeverAskAgain()
            }
            grantResults.filter { results -> results ==  PackageManager.PERMISSION_DENIED}.isNotEmpty() -> {
                Log.d(TAG,"isDenied()")
                isDenied()
            }
        }
    }

    public fun showRationalDialog(activity : Activity) {
        Log.d(TAG,"showRationalDialog($activity")
        val builder = activity.let { AlertDialog.Builder(it) }
        builder.setTitle("Manifest Permissions")
        builder.setMessage("Go to App Permission Settings?")
        builder.setPositiveButton("SETTINGS") { dialog, which ->
            dialog.dismiss()
            showAppPermissionSettings(activity)
        }
        builder.setNegativeButton("NOT NOW") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun showAppPermissionSettings(activity : Activity) {
        Log.d("PermissionsResult", "showAppPermissionSettings()")
        val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", activity.packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        activity.startActivityForResult(intent, SETTINGS_PERMISSION_CODE)
    }
}