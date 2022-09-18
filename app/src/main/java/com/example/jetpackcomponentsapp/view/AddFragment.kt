package com.example.jetpackcomponentsapp.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.AddBinder
import com.example.jetpackcomponentsapp.model.CustomHolderModel
import com.example.jetpackcomponentsapp.util.Coroutines

class AddFragment : Fragment(), View.OnClickListener {

    companion object {
        private val TAG = AddFragment::class.java.getSimpleName()

        fun newInstance() : AddFragment = AddFragment()
    }

    private var binder : AddBinder? = null
    private val viewModel : MainViewModel by lazy { ViewModelProvider(requireActivity()).get(
        MainViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_add,container,false)
        return binder?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@AddFragment)
        binder?.editText?.requestFocus()
        showSoftKeyboard(binder?.editText)
        binder?.button?.setOnClickListener(this@AddFragment)
    }

    override fun onClick(view : View?) {
        if (view == binder?.button) {
            viewModel.insertItem(CustomHolderModel(binder?.editText?.text.toString()))
            hideSoftKeyboard()
            requireActivity().getSupportFragmentManager().popBackStack()
        }
    }

    private fun getInputMethodManager() : InputMethodManager {
        return requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun showSoftKeyboard(view : View?) { Coroutines.main(this@AddFragment, work = {
        getInputMethodManager().showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }) }

    private fun hideSoftKeyboard() { Coroutines.main(this@AddFragment, work = {
        getInputMethodManager().hideSoftInputFromWindow(requireView().windowToken, 0)
    }) }
}