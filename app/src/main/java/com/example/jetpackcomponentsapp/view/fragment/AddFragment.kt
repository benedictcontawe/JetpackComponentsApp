package com.example.jetpackcomponentsapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.AddBinder
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.view.fragments.BaseFragment

class AddFragment : BaseFragment(), View.OnClickListener {

    companion object {
        public val TAG : String = AddFragment::class.java.getSimpleName()

        fun newInstance() : AddFragment = AddFragment()
    }

    private var binder : AddBinder? = null
    private val viewModel : MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, getHandleOnBackPressed())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_add,container,false)
        return binder?.getRoot()
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@AddFragment)
        binder?.editText?.requestFocus()
        showSoftKeyboard(binder?.editText)
        binder?.button?.setOnClickListener(this@AddFragment)
    }

    override fun onClick(view : View?) {
        if (view == binder?.button) {
            viewModel.insertItem(CustomModel(binder?.editText?.text.toString()))
            hideSoftKeyboard()
            requireActivity().getSupportFragmentManager().popBackStack()
        }
    }

    private fun getHandleOnBackPressed() : OnBackPressedCallback {
        return object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { Log.d(TAG,"handleOnBackPressed() ")
                if (binder?.editText?.getText()?.isNotBlank() == true)
                    binder?.editText?.getText()?.clear()
                else onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        getHandleOnBackPressed().remove()
        super.onDestroy()
    }
}