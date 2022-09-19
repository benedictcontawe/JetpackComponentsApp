package com.example.jetpackcomponentsapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.UpdateBinder
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.flow.FlowCollector

public class UpdateFragment : DialogFragment(), View.OnClickListener{

    companion object {
        private val TAG = UpdateFragment::class.java.getSimpleName()

        fun newInstance() : UpdateFragment = UpdateFragment()
    }

    private var binder : UpdateBinder? = null
    private val viewModel : MainViewModel by lazy { ViewModelProvider(requireActivity()).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_update,container,false)
        getDialog()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        return binder?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@UpdateFragment)
        Coroutines.main(this@UpdateFragment, {
            viewModel.observeUpdate().collect(object : FlowCollector<CustomModel> {
                override suspend fun emit(holder : CustomModel) {
                    binder?.editText?.setText(holder.name)
                    binder?.editText?.requestFocus()
                    showSoftKeyboard(binder?.editText)
                    binder?.editText?.selectAll()
                }
            })
        })
        binder?.button?.setOnClickListener(this@UpdateFragment)
    }

    override fun onClick(view : View?) {
        if (view == binder?.button) {
            binder?.getViewModel()?.updateItem(binder?.editText?.getText().toString())
            hideSoftKeyboard()
            dismiss()
        }
    }

    private fun getInputMethodManager() : InputMethodManager {
        return requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun showSoftKeyboard(view : View?) { Coroutines.main(this@UpdateFragment, work = {
        getInputMethodManager().showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }) }

    private fun hideSoftKeyboard() { Coroutines.main(this@UpdateFragment, work = {
        getInputMethodManager().hideSoftInputFromWindow(requireView().windowToken, 0)
    }) }
}
/*
AlertDialog.Builder(activity)
.setTitle("Update Name")
.setMessage("Please update your name")
.setView(getEditText())
.setIcon(R.drawable.ic_update_white)
.setPositiveButton("OK", object : DialogInterface.OnClickListener {
    override fun onClick(dialog : DialogInterface, which : Int) {
        dismiss()
    }
})
.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
    override fun onClick(dialog : DialogInterface?, which : Int) {
        dismiss()
    }
})
.create()
*/