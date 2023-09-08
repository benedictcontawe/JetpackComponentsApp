package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.ButtonBinder
import kotlinx.coroutines.CoroutineScope

class ButtonFragment : BaseFragment, View.OnClickListener{

    companion object {
        private val TAG : String = ButtonFragment::class.java.getSimpleName()

        public fun newInstance(title : String?) : ButtonFragment = ButtonFragment(title)
    }

    private var binder : ButtonBinder? = null
    private val title : String?

    constructor() {
        title = null
    }

    constructor(title : String?) {
        this.title = title
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_button,container,false)
        binder?.setLifecycleOwner(getViewLifecycleOwner())
        return binder?.root ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder?.titleButton?.setText(title)
        binder?.titleButton?.setOnClickListener(this@ButtonFragment)
    }

    override suspend fun onSetObservers(scope: CoroutineScope) {

    }

    override fun onClick(view : View?) {
        showToast(binder?.titleButton?.getText().toString(), Toast.LENGTH_SHORT)
    }
}