package com.example.jetpackcomponentsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jetpackcomponentsapp.databinding.AddBinder

class AddFragment : Fragment() {

    companion object {
        fun newInstance() : AddFragment = AddFragment()
    }

    private lateinit var binding: AddBinder
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View) {
                Toast.makeText(context,"onAdd(${binding.editText.text})", Toast.LENGTH_SHORT).show()
            }
        })
    }
}