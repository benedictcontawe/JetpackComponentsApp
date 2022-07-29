package com.example.jetpackcomponentsapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.AddBinder
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.view.MainActivity

class AddFragment : BaseFragment() {

    companion object {
        fun newInstance() : AddFragment = AddFragment()

        fun getTag() : String {
            return AddFragment::class.java.getSimpleName()
        }
    }

    private val activity by lazy { (getActivity() as MainActivity) }
    private lateinit var binding : AddBinder
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(AddFragment.getTag(),"onCreate()")
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(AddFragment.getTag(),"onCreateView()")
        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(AddFragment.getTag(),"onViewCreated()")
        binding.setViewModel(viewModel)
        binding.setLifecycleOwner(getViewLifecycleOwner())
        binding.editText.requestFocus()
        showSoftKeyboard()
        binding.button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                viewModel.insertItem(CustomModel(binding.editText.text.toString()))
                hideSoftKeyboard()
                activity.supportFragmentManager.popBackStack()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkIfFragmentLoaded(this)
    }
}