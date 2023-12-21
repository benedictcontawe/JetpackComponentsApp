package com.example.jetpackcomponentsapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.AddBinder
import com.example.jetpackcomponentsapp.model.CustomModel

class AddFragment : Fragment() {

    companion object {
        public val TAG : String = AddFragment::class.java.getSimpleName()

        fun newInstance() : AddFragment = AddFragment()
    }

    private lateinit var binding : AddBinder
    private val viewModel : MainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add,container,false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setViewModel(viewModel)
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.editText.requestFocus()
        showSoftKeyboard()

        binding.button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                viewModel.insertItem(CustomModel(binding.editText.text.toString()))
                hideSoftKeyboard()
                requireActivity().supportFragmentManager.popBackStack()
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