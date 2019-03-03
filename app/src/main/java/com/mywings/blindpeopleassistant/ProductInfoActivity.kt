package com.mywings.blindpeopleassistant

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import com.mywings.blindpeopleassistant.models.Product
import com.mywings.blindpeopleassistant.process.GetProductInfo
import com.mywings.blindpeopleassistant.process.OnProductListener
import com.mywings.blindpeopleassistant.process.ProgressDialogUtil
import kotlinx.android.synthetic.main.activity_product_info.*
import org.json.JSONObject
import java.util.*


class ProductInfoActivity : AppCompatActivity(), OnProductListener {


    private lateinit var progressDialogUtil: ProgressDialogUtil
    private lateinit var textToSpeech: TextToSpeech;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)
        textToSpeech = TextToSpeech(this, textToSpeechListener)
        progressDialogUtil = ProgressDialogUtil(this);
        initGetProduct(intent!!.extras!!.getString("id"))
        clContainer.setOnClickListener { finish() }
    }

    private fun initGetProduct(restult: String) {
        progressDialogUtil.show()
        var getProductInfo = GetProductInfo()
        getProductInfo.setOnProductListener(this, restult)
    }

    override fun onProductSuccess(result: JSONObject) {
        progressDialogUtil.hide()
        if (null != result && result.toString().isNotEmpty()) {

            var product = Product()

            product.id = result.getInt("Id")

            product.measurement = result.getString("Measurement")

            product.name = result.getString("Name")

            product.productInfo = result.getString("ProductInfo")

            product.quantity = result.getString("Qty")

            product.price = result.getString("Price")

            lblName.text = "Name : " + product.name

            lblProductInfo.text = "Product Info : " + product.productInfo

            lblQty.text = "Quantity : " + product.quantity

            lblPrice.text = "Price : " + product.price

            lblEx.text = "Expiry date : " + product.expiryDate

            lblManu.text = "Manufacture date : " + product.manufactureDate




            generate(product)
        } else {
            textToSpeech.speak(
                "Product details not found of product",
                TextToSpeech.QUEUE_FLUSH,
                null
            )
        }
    }

    private fun generate(product: Product) {
        val myHash = HashMap<String, String>()
        myHash[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "done"
        textToSpeech.speak(
            "Product Name ${product.name}",
            TextToSpeech.QUEUE_FLUSH,
            myHash
        )
        delayIn()
        textToSpeech.speak(
            "Number of item ${product.quantity}",
            TextToSpeech.QUEUE_ADD,
            myHash
        )
        delayIn();
        textToSpeech.speak(
            "Price ${product.price}",
            TextToSpeech.QUEUE_ADD,
            myHash
        )
        delayIn();
        textToSpeech.speak(
            "About the product ${product.productInfo}",
            TextToSpeech.QUEUE_ADD,
            myHash
        )
        delayIn();
        textToSpeech.speak(
            "Tap on screen tap to scan again another product, Thank You",
            TextToSpeech.QUEUE_ADD,
            myHash
        )

        delayIn()

        textToSpeech.speak(
            "Unit ${product.measurement}",
            TextToSpeech.QUEUE_ADD,
            myHash
        )

        delayIn()

        textToSpeech.speak(
            "Manufactured date ${product.manufactureDate}",
            TextToSpeech.QUEUE_ADD,
            myHash
        )

        delayIn()

        textToSpeech.speak(
            "Expiry date ${product.expiryDate}",
            TextToSpeech.QUEUE_ADD,
            myHash
        )
    }

    private fun delayIn() {
        textToSpeech.playSilence(DELAY, TextToSpeech.QUEUE_ADD, null)
    }

    private var textToSpeechListener = TextToSpeech.OnInitListener { textToSpeech.language = Locale.US }

    companion object {
        const val DELAY = 750L
    }
}
