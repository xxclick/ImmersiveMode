package indi.xuxin.immersivemode

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class WindowManagerActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_windowmanager)

        findViewById<CheckBox>(R.id.flag_1).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                window.addFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }
        findViewById<CheckBox>(R.id.flag_2).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                window.addFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }
        }
        findViewById<CheckBox>(R.id.flag_3).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                window.addFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                )
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            }
        }
    }
}
