package com.berker.sherlockofitunes.presentation

import com.berker.sherlockofitunes.R
import com.berker.sherlockofitunes.core.BaseActivity
import com.berker.sherlockofitunes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_main
}