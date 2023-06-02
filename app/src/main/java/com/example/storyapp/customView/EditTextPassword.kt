package com.example.storyapp.customView

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.R

class EditTextPassword : AppCompatEditText {

    private lateinit var closeImage: Drawable
    private lateinit var checkImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init(){
        checkImage = ContextCompat.getDrawable(context, R.drawable.baseline_check_24) as Drawable

        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                Log.d("CustomView", s.toString())

                if (text.count() < 8) error = "Inputan tidak boleh kurang dari 8" else showCheckButton()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun setButtonDrawable(
        startOfTheText: Drawable? = null,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    private fun showCheckButton(){
        setButtonDrawable(endOfTheText = checkImage)
    }
}