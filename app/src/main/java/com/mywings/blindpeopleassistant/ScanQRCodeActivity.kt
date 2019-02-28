package com.mywings.blindpeopleassistant

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.speech.tts.TextToSpeech
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import de.klimek.scanner.OnDecodedCallback
import kotlinx.android.synthetic.main.activity_scan_qrcode.*
import java.util.*


class ScanQRCodeActivity : AppCompatActivity(), OnDecodedCallback {

    private lateinit var textToSpeech: TextToSpeech;

    private val EXTERNAL_REQUEST: Int = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qrcode)
        textToSpeech = TextToSpeech(this, textToSpeechListener)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                EXTERNAL_REQUEST
            )
        } else {
            startAnim()
        }
    }

    override fun onResume() {
        super.onResume()
        startPreview()
        scanner.setOnDecodedCallback(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            EXTERNAL_REQUEST -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startAnim()

            } else {
                finish()
            }
        }
    }


    private fun startAnim() {
        val mAnimation = TranslateAnimation(
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 1 / 4f,
            TranslateAnimation.RELATIVE_TO_PARENT, 2 / 3f
        )
        mAnimation.duration = 500
        mAnimation.repeatCount = -1
        mAnimation.repeatMode = Animation.REVERSE
        mAnimation.interpolator = LinearInterpolator() as Interpolator?
        imgLine.animation = mAnimation
    }

    private fun stopPreview() {
        scanner.stopScanning()
    }

    private fun startPreview() {
        scanner.startScanning()
    }

    override fun onPause() {
        super.onPause()
        stopPreview()
    }

    private var textToSpeechListener = TextToSpeech.OnInitListener { textToSpeech.language = Locale.US }

    override fun onDecoded(result: String?) {

        if (result!!.isNotEmpty()) {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                v.vibrate(500)
            }

            textToSpeech.speak(
                "Scanned successfully,         Please wait         retrieving     product info",
                TextToSpeech.QUEUE_FLUSH,
                null
            )
            val handler = Handler()
            handler.postAtTime(Runnable {
                val intent = Intent(this@ScanQRCodeActivity, ProductInfoActivity::class.java)
                intent.putExtra("id", result)
                startActivity(intent)
            }, 3000)
        } else {
            textToSpeech.speak(
                "Something     went wrong,     please try again",
                TextToSpeech.QUEUE_FLUSH,
                null
            )
        }


    }
}
