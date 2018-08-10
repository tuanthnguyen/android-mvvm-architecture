package com.tuann.mvvm.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.tuann.mvvm.R
import com.tuann.mvvm.ui.images.ImagesFragment
import javax.inject.Inject

class Navigator @Inject constructor(private val activity: AppCompatActivity) {
    private val containerId: Int = R.id.container
    private val fragmentManager: FragmentManager = activity.supportFragmentManager

    fun navigateToImages() {
        replaceFragment(ImagesFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = fragmentManager
                .beginTransaction()
                .replace(containerId, fragment, null)

        if (fragmentManager.isStateSaved) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }
}