package com.budajovanovic.android_task.framework.ui

import com.budajovanovic.android_task.R
import com.budajovanovic.android_task.databinding.ActivityMainBinding
import com.budajovanovic.android_task.framework.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}