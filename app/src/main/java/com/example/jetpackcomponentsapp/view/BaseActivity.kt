package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.example.jetpackcomponentsapp.util.Coroutines
import com.example.jetpackcomponentsapp.util.ManifestPermission
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract public class BaseActivity : AppCompatActivity() {

    companion object {
        private val TAG = BaseActivity::class.java.getSimpleName()
    }

    protected fun logDebug(TAG : String, message : String) {
        Log.d(TAG,message)
    }

    protected fun showToast(messageToast : String?, duration : Int = Toast.LENGTH_SHORT) { logDebug(TAG, "showToast($messageToast)")
        Coroutines.main(this@BaseActivity, { scope -> scope.launch( block = {
            Toast.makeText(this@BaseActivity, messageToast, duration).show()
        }) })
    }

    protected fun showSnackBar(view : View, messageToast : String, duration : Int) { logDebug(TAG, "showSnackBar($messageToast)")
        Coroutines.main(this@BaseActivity, { scope -> scope.launch( block = {
            Snackbar.make(view, messageToast, duration).show()
        }) })
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        Coroutines.main(this@BaseActivity, { onSetObservers(it) })
    }

    protected abstract suspend fun onSetObservers(scope : CoroutineScope)
    //region Fragment Manager Methods
    protected fun replaceFragment(containerViewId : Int, fragment: BaseFragment) {
        logDebug(TAG, "replaceFragment($containerViewId,$fragment)")
        getSupportFragmentManager().beginTransaction()
            .replace(containerViewId, fragment, fragment::class.java.getSimpleName())
            .commitNow()
    }

    protected fun replaceFragment(containerViewId : Int, fragment: BaseFragment, enter : Int, exit : Int) {
        logDebug(TAG, "replaceFragment($containerViewId,$fragment)")
        getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(enter, exit)
            .replace(containerViewId, fragment, fragment::class.java.getSimpleName())
            .commitNow()
    }

    protected fun addToBackStackFragment(containerViewId : Int, fragment : BaseFragment) {
        logDebug(TAG, "addToBackStackFragment($containerViewId,$fragment)")
        if (getSupportFragmentManager().findFragmentByTag(fragment::class.java.getSimpleName()) == null)
            getSupportFragmentManager().beginTransaction()
                .add(containerViewId, fragment, fragment::class.java.getSimpleName())
                .addToBackStack(fragment::class.java.getSimpleName())
                .commit()
    }

    protected fun showDialogFragment(fragment : BaseDialogFragment) {
        fragment.show(getSupportFragmentManager().beginTransaction(), fragment.javaClass.getName())
    }

    protected fun showBottomSheetFragment(bottomSheetDialogFragment : BaseBottomSheetDialogFragment) {
        bottomSheetDialogFragment.setCancelable(true);
        bottomSheetDialogFragment.show(getSupportFragmentManager(),bottomSheetDialogFragment.javaClass.getName())
    }

    protected fun popBackStack() { logDebug(TAG, "popBackStack()")
        if (getSupportFragmentManager().getFragments().isNotEmpty()) getSupportFragmentManager().popBackStack()
    }

    protected fun popBackStack(pops : Int) { logDebug(TAG, "popBackStack($pops)")
        if(getSupportFragmentManager().getFragments().isNotEmpty() && pops <= 1) popBackStack()
        else if (getSupportFragmentManager().getFragments().isNotEmpty()) for (i in 0 until pops ) { logDebug(TAG, "popBackStack i $i")
            getSupportFragmentManager().popBackStack()
        }
    }

    protected fun popAllBackStack() { logDebug(TAG, "popAllBackStack()")
        if (getSupportFragmentManager().getFragments().isNotEmpty()) //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().getFragments().map { fragment -> getSupportFragmentManager().popBackStack(fragment.getTag(), FragmentManager.POP_BACK_STACK_INCLUSIVE) }
    }
    //endregion
    //region Manifest Permissions
    protected fun onRequestPermissions() { logDebug(TAG, "onRequestPermissions()")
        ManifestPermission.checkSelfPermission(this@BaseActivity, ManifestPermission.allPermissions, isGranted = { }, isDenied = {
            ManifestPermission.requestPermissions(this@BaseActivity, ManifestPermission.allPermissions, ManifestPermission.ALL_PERMISSION_CODE)
        })
    }
    override fun onRequestPermissionsResult(requestCode : Int, permissions : Array<String>, grantResults : IntArray) { logDebug(TAG, "onRequestPermissionsResult($requestCode, ${permissions.contentToString()}, ${grantResults.contentToString()})")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        ManifestPermission.checkPermissionsResult(this@BaseActivity, ManifestPermission.ALL_PERMISSION_CODE, requestCode, permissions, grantResults, isNeverAskAgain =  { onRequestPermissionsNeverAskAgain() }, isDenied = { onRequestPermissionsDenied() }, isGranted = { onRequestPermissionsGranted() })
    }

    open fun onRequestPermissionsNeverAskAgain() { logDebug(TAG, "onRequestPermissionsNeverAskAgain()")
        ManifestPermission.showRationaleDialog(this@BaseActivity, "Enable App Permissions to Proceed")
    }

    open fun onRequestPermissionsDenied() { logDebug(TAG, "onRequestPermissionsDenied()")
        onRequestPermissions()
    }

    open fun onRequestPermissionsGranted() { logDebug(TAG, "onRequestPermissionsGranted()") }
    //endregion
    //region onBackPressed Methods
    override fun onBackPressed() { logDebug(TAG,"onBackPressed()")
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) { logDebug(TAG,"popBackStack()")
            getSupportFragmentManager().popBackStack()
        } else { logDebug(TAG,"super.onBackPressed()")
            super.onBackPressed()
        }
    }

    protected fun onBackPressed(drawerLayout : DrawerLayout, navigationView : NavigationView) {
        if (drawerLayout.isDrawerOpen(navigationView)) { logDebug(TAG,"closeDrawer()")
            drawerLayout.closeDrawer(navigationView, true)
        } else { logDebug(TAG,"super.onBackPressed()")
            super.onBackPressed()
        }
    }
    //endregion
    //region Leave App Method
    protected fun exitApp() { logDebug(TAG,"exitApp()")
        ActivityCompat.finishAffinity(this@BaseActivity)
        System.exit(0)
    }
    //endregion
    override fun onDestroy() { logDebug(TAG,"onDestroy()")
        super.onDestroy()
    }
}