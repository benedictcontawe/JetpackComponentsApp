package com.example.jetpackcomponentsapp.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseDialogFragment : DialogFragment(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    private fun getInputMethodManager(): InputMethodManager {
        return requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    protected fun showSoftKeyboard() {
        val inputMethodManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    protected fun showSoftKeyboard(view: View?) { Coroutines.main(this@BaseDialogFragment, work = {
        getInputMethodManager().showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    })
    }

    protected fun hideSoftKeyboard() { Coroutines.main(this@BaseDialogFragment, work = {
        getInputMethodManager().hideSoftInputFromWindow(requireView().windowToken, 0)
    }) }

    protected fun onBackPressed() {
        requireActivity().getSupportFragmentManager().popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}