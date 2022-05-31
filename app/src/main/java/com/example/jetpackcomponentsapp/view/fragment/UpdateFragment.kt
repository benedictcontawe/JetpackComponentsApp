package com.example.jetpackcomponentsapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.UpdateBinder
import com.example.jetpackcomponentsapp.model.CustomModel


class UpdateFragment : DialogFragment() {

    companion object {
        fun newInstance() : UpdateFragment = UpdateFragment()

        fun getTag() : String = UpdateFragment::class.java.getSimpleName()
    }

    private lateinit var binding : UpdateBinder
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        binding = DataBindingUtil.inflate(inflater,R.layout.update_fragment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getUpdate().observe(viewLifecycleOwner, object : Observer<CustomModel> {
            override fun onChanged(item : CustomModel) {
                binding.editText.setText(item.name)
                binding.editText.requestFocus()
                binding.editText.selectAll()
                showSoftKeyboard()
            }
        })

        binding.button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                viewModel.updateItem()
                hideSoftKeyboard()
                dismiss()
            }
        })
    }

    private fun showSoftKeyboard() {
        val inputMethodManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
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