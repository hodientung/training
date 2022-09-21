package com.example.voicelockscreen.view

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner


class CustomSpinner : AppCompatSpinner {
    interface OnSpinnerEventsListener {
        fun onPopupWindowOpened(spinner: Spinner?)
        fun onPopupWindowClosed(spinner: Spinner?)
    }

    private var mListener: OnSpinnerEventsListener? = null
    private var mOpenInitiated = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        mode: Int
    ) : super(context, attrs, defStyleAttr, mode) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        mode: Int,
        popupTheme: Resources.Theme?
    ) : super(context, attrs, defStyleAttr, mode, popupTheme) {
    }

    override fun performClick(): Boolean {
        mOpenInitiated = true
        mListener?.onPopupWindowOpened(this)
        return super.performClick()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasBeenOpened() && hasFocus) {
            performClosedEvent()
        }
    }

    /**
     * Register the listener which will listen for events.
     */
    fun setSpinnerEventsListener(
        onSpinnerEventsListener: OnSpinnerEventsListener?
    ) {
        mListener = onSpinnerEventsListener
    }

    /**
     * Propagate the closed Spinner event to the listener from outside if needed.
     */
    fun performClosedEvent() {
        mOpenInitiated = false
        mListener?.onPopupWindowClosed(this)
    }

    /**
     * A boolean flag indicating that the Spinner triggered an open event.
     *
     * @return true for opened Spinner
     */
    fun hasBeenOpened(): Boolean {
        return mOpenInitiated
    }
}