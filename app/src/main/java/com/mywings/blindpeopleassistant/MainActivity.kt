package com.mywings.blindpeopleassistant

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import com.mywings.blindpeopleassistant.process.ConstantsHelper
import com.mywings.blindpeopleassistant.process.OnConnectionListener
import com.mywings.blindpeopleassistant.process.UtilityConnection
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), OnConnectionListener {

    private lateinit var textToSpeech: TextToSpeech;
    private var exist: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initConnect()
        textToSpeech = TextToSpeech(this, textToSpeechListener);
        btnClick.setOnClickListener {
            if (exist) {
                val intent = Intent(this@MainActivity, ScanQRCodeActivity::class.java)
                intent.putExtra("exist", exist)
                startActivity(intent)
            } else {
                textToSpeech.speak(
                    "Please wait initializing feature",
                    TextToSpeech.QUEUE_FLUSH,
                    null
                )
            }
        }
    }

    private fun initConnect() {
        var utilityConnection = UtilityConnection()
        utilityConnection.setOnConnectionListener(this, ConstantsHelper.ID)
    }

    override fun onPause() {
        super.onPause()
        textToSpeech.stop();
        textToSpeech.shutdown();
    }


    override fun onConnectionSuccess(result: Boolean) {
        exist = result
    }

    private var textToSpeechListener = TextToSpeech.OnInitListener { textToSpeech.language = Locale.US }
}
