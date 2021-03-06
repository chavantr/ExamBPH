package com.mywings.blindpeopleassistant.process

import android.os.AsyncTask
import org.json.JSONObject

class GetProductInfo : AsyncTask<String, Void, JSONObject>() {

    private var httpConnectionUtil = HttpConnectionUtil()

    private lateinit var onProductListener: OnProductListener

    override fun doInBackground(vararg params: String?): JSONObject? {
        var response =
            httpConnectionUtil.requestGet(ConstantsHelper.URL + ConstantsHelper.GET_PRODUCT + "?id=" + params[0])
        try {
            if (null != response && response.isNotEmpty()) {
                return JSONObject(response)
            }
        } catch (e: NullPointerException) {
            return null
        }
        return null
    }


    override fun onPostExecute(result: JSONObject?) {
        super.onPostExecute(result)
        onProductListener.onProductSuccess(result!!)
    }


    fun setOnProductListener(onProductListener: OnProductListener, request: String) {

        this.onProductListener = onProductListener

        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
    }

}