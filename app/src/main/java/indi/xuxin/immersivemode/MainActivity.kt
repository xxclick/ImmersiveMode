package indi.xuxin.immersivemode

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        findViewById<View>(R.id.id1).setOnClickListener {
            startActivity(Intent(this, ViewSystemFlagActivity::class.java))
        }
        findViewById<View>(R.id.id2).setOnClickListener {
            startActivity(Intent(this, WindowManagerActivity::class.java))
        }
        findViewById<View>(R.id.id3).setOnClickListener {
            startActivity(Intent(this, ModernizeImmersiveMode::class.java))
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
