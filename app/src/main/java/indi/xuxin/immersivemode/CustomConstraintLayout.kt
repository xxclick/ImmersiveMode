package indi.xuxin.immersivemode

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CustomConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
       doOnApplyWindowInsets { view, windowInsets, initialPadding ->

       }
    }

    override fun fitSystemWindows(insets: Rect?): Boolean {

        // return false // 向下分发
        return super.fitSystemWindows(insets)

    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        return super.onApplyWindowInsets(insets)
    }
}