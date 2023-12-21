package com.example.jetpackcomponentsapp.view.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.jetpackcomponentsapp.MainViewModel
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.UpdateBinder
import com.example.jetpackcomponentsapp.model.CustomModel

class UpdateFragment : DialogFragment() {

    companion object {
        public val TAG : String = UpdateFragment::class.java.getSimpleName()
        public val ID : String = "id"
        public val NAME : String = "name"
        public val ICON : String = "icon"

        fun newInstance(model : CustomModel) : UpdateFragment {
            val arguments : Bundle = Bundle()
            arguments.putInt(ID, model.id ?: -1)
            arguments.putString(NAME, model.name)
            arguments.putInt(ICON, model.icon)
            val updateFragment : UpdateFragment = UpdateFragment()
            updateFragment.setArguments(arguments)
            return updateFragment
        }
    }

    private lateinit var binding : UpdateBinder
    private val viewModel : MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment)
        viewModel.setUpdate (
            arguments?.getInt(ID, -1),
            arguments?.getString(NAME, ""),
            arguments?.getInt(ICON, -1),
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_update,container,false)
        return binding.getRoot()
    }

    override fun onCreateDialog(savedInstanceState : Bundle?) : Dialog {
        val dialog : Dialog = super.onCreateDialog(savedInstanceState)
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setViewModel(viewModel)
        binding.setLifecycleOwner(getViewLifecycleOwner())


        viewModel.getUpdate().observe(getViewLifecycleOwner(), object : Observer<CustomModel> {
            override fun onChanged(value : CustomModel) {
                Log.d(TAG, "getUpdate $value")
                binding.editText.setText(value.name)
                binding.editText.requestFocus()
                binding.editText.selectAll()
                showSoftKeyboard(binding.editText)
            }
        })

        binding.button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                viewModel.updateItem(binding.editText.getText().toString())
                hideSoftKeyboard()
                dismiss()
            }
        })
    }

    private fun showSoftKeyboard() {
        val inputMethodManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun showSoftKeyboard(view : View?) {
        val inputMethodManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}