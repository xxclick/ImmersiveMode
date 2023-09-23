package indi.xuxin.immersivemode

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

enum class BehaviorOption(
    val title: String,
    val value: Int
) {
    Default(
        "BY_TOUCH",
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH
    ),

    ShowBarsBySwipe(
        "BY_SWIPE",
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
    ),

    // "Sticky immersive mode"
    ShowTransientBarsBySwipe(
        "TRANSIENT_BY_SWIPE",
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    )
}


enum class TypeOption(
    val title: String,
    val value: Int
) {
    SystemBars(
        "systemBars()",
        WindowInsetsCompat.Type.systemBars()
    ),

    StatusBar(
        "statusBars()",
        WindowInsetsCompat.Type.statusBars()
    ),

    NavigationBar(
        "navigationBars()",
        WindowInsetsCompat.Type.navigationBars()
    )
}


class ModernizeImmersiveMode : AppCompatActivity() {
    private lateinit var behaviorSpinner: Spinner
    private lateinit var typeSpinner: Spinner
    private lateinit var lightSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_modern)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        behaviorSpinner = findViewById(R.id.behavior)
        behaviorSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BehaviorOption.values().map { it.title }
        )

        typeSpinner = findViewById(R.id.type)
        typeSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            TypeOption.values().map { it.title }
        )

        findViewById<View>(R.id.hide).setOnClickListener { controlWindowInsets(true) }
        findViewById<View>(R.id.show).setOnClickListener { controlWindowInsets(false) }

        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        findViewById<View>(R.id.lightStatus).setOnClickListener {
           insetsController?.isAppearanceLightStatusBars = !insetsController?.isAppearanceLightStatusBars!!
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        findViewById<View>(R.id.lightNavigation).setOnClickListener {
            insetsController?.isAppearanceLightNavigationBars = !insetsController?.isAppearanceLightNavigationBars!!
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.navigationBarColor = Color.TRANSPARENT
        }
    }

    private fun controlWindowInsets(hide: Boolean) {
        val behavior = BehaviorOption.values()[behaviorSpinner.selectedItemPosition].value
        val type = TypeOption.values()[typeSpinner.selectedItemPosition].value

        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController?.systemBarsBehavior = behavior
        if (hide) {
            insetsController?.hide(type)
        } else {
            insetsController?.show(type)
        }
    }
}
