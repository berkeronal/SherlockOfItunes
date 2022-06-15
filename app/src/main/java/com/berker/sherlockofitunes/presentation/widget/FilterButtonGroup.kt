package com.berker.sherlockofitunes.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.children
import com.berker.sherlockofitunes.databinding.ViewFilterGroupButtonBinding
import com.berker.sherlockofitunes.domain.model.ContentType
import com.google.android.material.button.MaterialButton

class FilterButtonGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) : LinearLayout(context, attrs) {

    private val binding: ViewFilterGroupButtonBinding

    private val layoutParamsWrap = LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT,
        0.5f
    )

    var changeListener: ((String) -> Unit)? = null

    fun setChecked(value: String) {
        binding.toggleGroup.children.forEach {
            if ((it as MaterialButton).text != value) {
                return@forEach
            }
            it.isChecked = true
        }
    }

    init {
        binding = ViewFilterGroupButtonBinding.inflate(LayoutInflater.from(context), this, false)
        addView(binding.root)
        createButtons()
    }

    private fun createButtons() {
        ContentType.values().forEach { content ->
            MaterialButton(
                context,
                null,
                com.google.android.material.R.attr.materialButtonOutlinedStyle
            ).apply {
                layoutParams = layoutParamsWrap
                text = content.value
                setOnClickListener {
                    setClickListenerWithNullCheck(content.value)
                }
            }.also {
                binding.toggleGroup.addView(it)
            }
        }
    }

    private fun setClickListenerWithNullCheck(value: String) {
        changeListener?.invoke(value)
    }
}