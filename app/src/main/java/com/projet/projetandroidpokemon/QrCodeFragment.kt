package com.projet.projetandroidpokemon

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


/**
 * A simple [Fragment] subclass.
 * Use the [QrCodeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QrCodeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_qr_code, container, false)

        val returnButton: ImageButton = root.findViewById(R.id.backButton)
        returnButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val qrCodeImageView: ImageView = root.findViewById(R.id.imageView7)

        val userEmail = UserSessionManager(requireContext()).getUserEmail()
        if (userEmail != null) {
            val qrBitmap = generateQRCode(userEmail)
            qrBitmap?.let {
                qrCodeImageView.setImageBitmap(it)
            }
        } else {
            Toast.makeText(requireContext(), "Utilisateur non connecté.", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    private fun generateQRCode(data: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        return try {
            val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}
