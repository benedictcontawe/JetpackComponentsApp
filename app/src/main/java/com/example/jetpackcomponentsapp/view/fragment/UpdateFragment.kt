package com.example.jetpackcomponentsapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.UpdateBinder
import com.example.jetpackcomponentsapp.model.CustomModel


class UpdateFragment : DialogFragment() {

    companion object {
        fun newInstance() : UpdateFragment = UpdateFragment()
    }

    private lateinit var binding : UpdateBinder
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.update_fragment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getUpdate().observe(viewLifecycleOwner, object : Observer<CustomModel> {
            override fun onChanged(item : CustomModel) {
                binding.editText.setText(item.name)
                binding.editText.requestFocus()
            }
        })

        binding.button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                viewModel.updateItem()
                dismiss()
            }
        })
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