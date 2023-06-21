package com.example.jetpackcomponentsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.jetpackcomponentsapp.R
import com.example.jetpackcomponentsapp.databinding.TitleBinder
import kotlinx.coroutines.CoroutineScope

class TitleFragment : BaseFragment {

    companion object {
        private val TAG : String = TitleFragment::class.java.getSimpleName()

        public fun newInstance(title : String?) : TitleFragment = TitleFragment(title)
    }

    private var binder : TitleBinder? = null
    private var title : String? = null

    constructor(title : String?) {
        this.title = title
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_title,container,false)
        binder?.setLifecycleOwner(getViewLifecycleOwner())
        return binder?.root ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder?.titleTextView?.setText(title)
    }

    override suspend fun onSetObservers(scope: CoroutineScope) {

    }
}