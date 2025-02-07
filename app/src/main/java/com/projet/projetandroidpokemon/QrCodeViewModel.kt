package com.projet.projetandroidpokemon.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class QrCodeViewModel : ViewModel() {

    private val _qrBitmap = MutableLiveData<Bitmap?>()
    val qrBitmap: LiveData<Bitmap?> get() = _qrBitmap

    fun generateQRCode(userEmail: String) {
        val qrCodeData = "https://addfriend.com?email=$userEmail"
        val qrCodeWriter = QRCodeWriter()
        try {
            val bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }

            _qrBitmap.postValue(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
            _qrBitmap.postValue(null)
        }
    }

}
