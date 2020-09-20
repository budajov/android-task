package com.budajovanovic.android_task.framework.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.budajovanovic.android_task.framework.ui.UIController
import timber.log.Timber

abstract class BaseFragment<viewDataBinding : ViewDataBinding> : Fragment() {

    /**
     * Fragment should return their layout id through this method. (ex. R.layout.this_fragment_layout)
     */
    protected abstract fun getLayoutId(): Int

    protected lateinit var binding: viewDataBinding

    protected lateinit var uiController: UIController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("onAttach")
        setUIController(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        Timber.d("onCreateView")

        binding =
            DataBindingUtil.inflate(layoutInflater, getLayoutId(), container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
    }

    private fun setUIController(mockUiController: UIController?) {
        // TEST: Set interface from mock
        if (mockUiController != null) {
            this.uiController = mockUiController
        } else {
            activity?.let {
                if (it is BaseActivity<*>) {
                    try {
                        this.uiController = activity as UIController
                    } catch (e: ClassCastException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }


}