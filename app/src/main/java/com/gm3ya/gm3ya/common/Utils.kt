package com.buildingmaterials.buildingmaterials.common

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


fun TextInputEditText.getString(): String {
    return this.text.toString().trim()
}


fun EditText.getString(): String {
    return this.text.toString().trim()
}


fun EditText.getInt(): Int = try {
    this.text.toString().toInt()
} catch (e: Exception) {
   0
}

fun String.getIntFromString(): Int = try {
    this.toInt()
} catch (e: Exception) {
    0
}

fun EditText.getDouble(): Double = try {
    this.text.toString().toDouble()
} catch (e: Exception) {
    0.0
}

fun String.getDouble(): Double = try {
    this.toDouble()
} catch (e: Exception) {
    0.0
}


fun TextInputEditText.isStringEmpty(): Boolean {
    return this.text.toString().trim().isEmpty()
}

fun EditText.isStringEmpty(): Boolean {
    return this.text.toString().trim().isEmpty()
}


fun isValidEmail(email: String): Boolean {
    return Pattern.compile(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    ).matcher(email).matches()
}



fun Uri.getPath(context: Context): String {
    var result: String? = null
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val cursor: Cursor = context.contentResolver.query(
        this, proj, null, null, null)!!
    if (cursor.moveToFirst()) {
        val column_index: Int = cursor.getColumnIndexOrThrow(proj[0])
        result = cursor.getString(column_index)
    }
    cursor.close()
    if (result == null) {
        result = "Not found"
    }
    return result
}


fun getRotated(path: String):Float{
    return when(ExifInterface(path).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)){
        ExifInterface.ORIENTATION_ROTATE_90 -> 90F
        ExifInterface.ORIENTATION_ROTATE_180 -> 180F
        ExifInterface.ORIENTATION_ROTATE_270 ->  270F
        ExifInterface.ORIENTATION_NORMAL-> 0F
        else ->0F
    }
}


fun ImageView.setImageFromUri(uri: Uri, context: Context){
    uri.getPath(context).let{ path ->
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            .let { bitmap ->
                this.setImageBitmap(bitmap.rotateImage(getRotated(path)))
            }
    }
}


fun Bitmap.rotateImage(angle: Float): Bitmap? {
    Matrix().let { matrix ->
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            this, 0, 0, this.width,this.height,
            matrix, true
        )
    }
}

fun Double.roundOffDecimal(): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble()
}

fun Context.showMessage(message:String){
    Toast.makeText(this,message, Toast.LENGTH_LONG).show()
}


fun String.getMonth(): String {
    val inputFormat = SimpleDateFormat("EEE MMM d HH:mm:ss 'GMT+02:00' yyyy", Locale.US)
    val outputFormat = SimpleDateFormat("MMM", Locale.US)
    return outputFormat.format(inputFormat.parse(this))
}


fun String.getMonthAndYear(): String {
    val inputFormat = SimpleDateFormat("EEE MMM d HH:mm:ss 'GMT+02:00' yyyy", Locale.US)
    val outputFormat = SimpleDateFormat("MMM yyyy", Locale.US)
    return outputFormat.format(inputFormat.parse(this))
}

fun String.getDayMonthAndYear(): String {
    try {
        val inputFormat = SimpleDateFormat("EEE MMM d HH:mm:ss 'GMT+02:00' yyyy", Locale.US)
        val outputFormat = SimpleDateFormat("d MMM yyyy", Locale.US)
        return outputFormat.format(inputFormat.parse(this))
    }catch (e:Exception){
        return ""
    }

}
