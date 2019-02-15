package waldrapps.plannit.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View

fun Context.getLayoutInflater(): LayoutInflater {
    return getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}

fun View.getLayoutInflater(): LayoutInflater {
    return context.getLayoutInflater()
}