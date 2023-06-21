package com.example.jetpackcomponentsapp.view

public data class FragmentStateModel (
    val title : String?,
    val fragment : BaseFragment,
) {
    companion object {
        private val TAG = FragmentStateModel::class.java.getSimpleName()
    }
    override fun toString() : String {
        return "$TAG(${title ?: fragment::class.java.getSimpleName()}, $fragment)" ?: super.toString()
    }
}