package com.tuann.mvvm.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tuann.mvvm.R
import com.tuann.mvvm.presentation.images.ImagesFragment
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