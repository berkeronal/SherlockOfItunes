package com.berker.sherlockofitunes.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB : ViewDataBinding, out VM : BaseViewModel>(
    private val inflate: Inflate<VB>
) : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract val viewModel: VM

    abstract fun initUi()

    open fun initReceivers() {}

    open fun initOnCreateView(savedInstanceState: Bundle?) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        initOnCreateView(savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initUi()
        initReceivers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

