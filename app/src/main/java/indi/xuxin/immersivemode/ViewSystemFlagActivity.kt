package indi.xuxin.immersivemode

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class ViewSystemFlagActivity : AppCompatActivity() {
    private lateinit var mHideNavCheckbox: CheckBox
    private lateinit var mHideStatusBarCheckBox: CheckBox
    private lateinit var mImmersiveModeCheckBox: CheckBox
    private lateinit var mImmersiveModeStickyCheckBox: CheckBox
    private lateinit var mLowProfileCheckBox: CheckBox
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_flags)

        mLowProfileCheckBox = findViewById(R.id.flag_enable_lowprof)
        mHideNavCheckbox = findViewById(R.id.flag_hide_navbar)
        mHideStatusBarCheckBox = findViewById(R.id.flag_hide_statbar)
        mImmersiveModeCheckBox = findViewById(R.id.flag_enable_immersive)
        mImmersiveModeStickyCheckBox = findViewById(R.id.flag_enable_immersive_sticky)
        findViewById<Button>(R.id.btn_changeFlags).setOnClickListener { toggleUiFlags() }

        findViewById<Button>(R.id.btn_immersive).setOnClickListener {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility.let {
                it and View.SYSTEM_UI_FLAG_LOW_PROFILE.inv() or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE and
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
            }
            dumpFlagStateToLog(window.decorView.systemUiVisibility)

            mLowProfileCheckBox.setChecked(false)
            mHideNavCheckbox.setChecked(true)
            mHideStatusBarCheckBox.setChecked(true)
            mImmersiveModeCheckBox.setChecked(true)
            mImmersiveModeStickyCheckBox.setChecked(false)
        }
        findViewById<View>(R.id.btn_leanback).setOnClickListener {

            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility.let {
                it and View.SYSTEM_UI_FLAG_LOW_PROFILE.inv() or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION and
                        View.SYSTEM_UI_FLAG_IMMERSIVE.inv() and
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
            }
            dumpFlagStateToLog(window.decorView.systemUiVisibility)

            mLowProfileCheckBox.setChecked(false)
            mHideNavCheckbox.setChecked(true)
            mHideStatusBarCheckBox.setChecked(true)
            mImmersiveModeCheckBox.setChecked(false)
            mImmersiveModeStickyCheckBox.setChecked(false)
        }

        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            // Note that system bars will only be "visible" if none of the
            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
            if (visibility and View.SYSTEM_UI_FLAG_LOW_PROFILE != 0) {
                Log.i(TAG, "SYSTEM_UI_FLAG_LOW_PROFILE is visible")
            } else {
                Log.i(TAG, "SYSTEM_UI_FLAG_LOW_PROFILE is invisible")
            }
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN != 0) {
                Log.i(TAG, "SYSTEM_UI_FLAG_FULLSCREEN is visible")
            } else {
                Log.i(TAG, "SYSTEM_UI_FLAG_FULLSCREEN is invisible")
            }
            if (visibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION != 0) {
                Log.i(TAG, "SYSTEM_UI_FLAG_HIDE_NAVIGATION is visible")
            } else {
                Log.i(TAG, "SYSTEM_UI_FLAG_HIDE_NAVIGATION is invisible")
            }
        }


        // layout属性保证内容全屏
        var uiOptions = window.decorView.systemUiVisibility
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.decorView.systemUiVisibility = uiOptions

        // 刘海屏有单独的规则
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }


    }

    private fun dumpFlagStateToLog(uiFlags: Int) {
        if (uiFlags and View.SYSTEM_UI_FLAG_LOW_PROFILE != 0) {
            Log.i(TAG, "SYSTEM_UI_FLAG_LOW_PROFILE is set")
        } else {
            Log.i(TAG, "SYSTEM_UI_FLAG_LOW_PROFILE is unset")
        }
        if (uiFlags and View.SYSTEM_UI_FLAG_FULLSCREEN != 0) {
            Log.i(TAG, "SYSTEM_UI_FLAG_FULLSCREEN is set")
        } else {
            Log.i(TAG, "SYSTEM_UI_FLAG_FULLSCREEN is unset")
        }
        if (uiFlags and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION != 0) {
            Log.i(TAG, "SYSTEM_UI_FLAG_HIDE_NAVIGATION is set")
        } else {
            Log.i(TAG, "SYSTEM_UI_FLAG_HIDE_NAVIGATION is unset")
        }
        if (uiFlags and View.SYSTEM_UI_FLAG_IMMERSIVE != 0) {
            Log.i(TAG, "SYSTEM_UI_FLAG_IMMERSIVE is set")
        } else {
            Log.i(TAG, "SYSTEM_UI_FLAG_IMMERSIVE is unset")
        }
        if (uiFlags and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY != 0) {
            Log.i(TAG, "SYSTEM_UI_FLAG_IMMERSIVE_STICKY is set")
        } else {
            Log.i(TAG, "SYSTEM_UI_FLAG_IMMERSIVE_STICKY is unset")
        }
    }

    // 其实下面几个flag受刘海屏layoutInDisplayCutoutMode的影响
    private fun toggleUiFlags() {
        val decorView: View = window.decorView
        val uiOptions = decorView.systemUiVisibility
        var newUiOptions = uiOptions

        // 使system bars 变“暗”，这个FLAG基本没用现在
        newUiOptions = if (mLowProfileCheckBox.isChecked) {
            newUiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
        } else {
            newUiOptions and View.SYSTEM_UI_FLAG_LOW_PROFILE.inv()
        }

        // 显示状态栏
        newUiOptions = if (mHideStatusBarCheckBox.isChecked) {
            newUiOptions or View.SYSTEM_UI_FLAG_FULLSCREEN
        } else {
            newUiOptions and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
        }

        // 显示导航栏
        newUiOptions = if (mHideNavCheckbox.isChecked) {
            newUiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        } else {
            newUiOptions and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
        }

        // 限制滑动system bars， window改变，排除用户的交互引起的flag的改变
        newUiOptions = if (mImmersiveModeCheckBox.isChecked) {
            newUiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE
        } else {
            newUiOptions and View.SYSTEM_UI_FLAG_IMMERSIVE.inv()
        }

        // 限制window改变，排除用户的交互引起的flag的改变，滑动system bars的改变
        newUiOptions = if (mImmersiveModeStickyCheckBox.isChecked) {
            newUiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        } else {
            newUiOptions and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
        }
        decorView.systemUiVisibility = newUiOptions
        dumpFlagStateToLog(uiOptions)
    }


    companion object {
        const val TAG = "AdvancedImmersiveModeFragment"
    }
}
