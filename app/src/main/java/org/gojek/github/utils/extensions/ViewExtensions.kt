package org.gojek.github.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Shahbaz Hashmi on 2020-03-07.
 */

/**
 * Inflate the layout specified by [layoutRes].
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}