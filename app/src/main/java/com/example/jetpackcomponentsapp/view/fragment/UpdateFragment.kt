package com.example.jetpackcomponentsapp.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.UpdateBinder
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.util.Coroutines
import com.example.jetpackcomponentsapp.view.fragments.BaseDialogFragment
import kotlinx.coroutines.flow.FlowCollector

class UpdateFragment : BaseDialogFragment(), View.OnClickListener {

    companion object {
        private val TAG = UpdateFragment::class.java.getSimpleName()

        fun newInstance() : UpdateFragment = UpdateFragment()
    }

    private var binder : UpdateBinder? = null
    private val viewModel : MainViewModel by activityViewModels<MainViewModel>()
    //private val viewModel : MainViewModel by lazy { ViewModelProvider(requireActivity()).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment)
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        //return if (getShowsDialog() == true) super.onCreateView(inflater, container, savedInstanceState);
        //else {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)
        getDialog()?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        return binder?.root
        //}
    }

    override fun onCreateDialog(savedInstanceState : Bundle?) : Dialog {
        val dialog : Dialog = super.onCreateDialog(savedInstanceState);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder?.setViewModel(viewModel)
        binder?.setLifecycleOwner(this@UpdateFragment)
        Coroutines.main(this@UpdateFragment, {
            viewModel.observeUpdate().collect(object : FlowCollector<CustomModel?> {
                override suspend fun emit(value : CustomModel?) {
                    binder?.editText?.setText(value?.name)
                    binder?.editText?.requestFocus()
                    showSoftKeyboard(binder?.editText)
                    binder?.editText?.selectAll()
                }
            })
        })
        binder?.editText?.requestFocus()
        binder?.editText?.selectAll()
        showSoftKeyboard(binder?.editText)
        binder?.button?.setOnClickListener(this@UpdateFragment)
    }

    override fun onClick(view : View?) {
        if (view == binder?.button) {
            binder?.getViewModel()?.updateItem(binder?.editText?.getText().toString())
            hideSoftKeyboard()
            dismiss()
        }
    }
}