package com.anwesh.uiprojects.triincircleview

/**
 * Created by anweshmishra on 12/03/19.
 */

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.MotionEvent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Color

val nodes : Int = 5
val tri : Int = 2
val scGap : Float = 0.05f
val scDiv : Double = 0.51
val sizeFactor : Float = 2.9f
val triColor : Int = Color.parseColor("#673AB7")
val circleColor : Int = Color.parseColor("#BDBDBD")
val fillColor : Int = Color.parseColor("#212121")
val rFactor : Int = 3

fun Int.inverse() : Float = 1F / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.scaleFactor() : Float = Math.floor(this / scDiv).toFloat()
fun Float.mirrorValue(a : Int, b : Int) : Float = (1 - scaleFactor()) * a.inverse() + scaleFactor() * b.inverse()
fun Float.updateValue(dir : Float, a : Int, b : Int) : Float = mirrorValue(a, b) * dir * scGap
fun Int.isf() : Float = 1f - 2 * (this % 2)
fun Int.jsf() : Float = 1f - 2 * this

fun Canvas.drawTICNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = h / (nodes + 1)
    val sc1 : Float = scale.divideScale(0, 2)
    val sc2 : Float = scale.divideScale(1, 2)
    val size : Float = gap / sizeFactor
    val pSize : Float = size / rFactor
    paint.strokeCap = Paint.Cap.ROUND
    save()
    translate(w / 2, gap * (i + 1))
    rotate(90f * i.isf() * sc2)
    paint.color = circleColor
    drawCircle(0f, 0f, size, paint)
    for (j in 0..(tri - 1)) {
        paint.color = fillColor
        val path : Path = Path()
        path.moveTo(0f, -pSize)
        path.lineTo(0f, pSize)
        path.lineTo(pSize * sc1.divideScale(j, tri) * j.jsf(), pSize)
        drawPath(path, paint)
    }
    restore()
}