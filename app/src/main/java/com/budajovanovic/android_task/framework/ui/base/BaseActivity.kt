package com.budajovanovic.android_task.framework.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.budajovanovic.android_task.databinding.ActivityMainBinding
import com.budajovanovic.android_task.framework.ui.UIController
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), UIController {

    lateinit var binding: T

    /**
     * Activity should return their layout id through this method. (ex. R.layout.this_fragment_layout)
     */
    protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<T>(this, getLayoutId())
        Timber.d("onCreate")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("onActivityResult[$requestCode, $resultCode]")
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showProgressBar() {
        progressBar?.visible()
    }

    override fun hideProgressBar() {
        progressBar?.gone()
    }

    override fun closeApp() {
        finish()
    }

}