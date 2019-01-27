package com.mywings.blindpeopleassistant.process

import android.os.AsyncTask

class UtilityConnection : AsyncTask<String, Void, Boolean>() {
    private val httpConnectionUtil = HttpConnectionUtil()
    private lateinit var onConnectionListener: OnConnectionListener
    override fun doInBackground(vararg param: String?): Boolean {
        var response =
            httpConnectionUtil.requestGet(
                ConstantsHelper.URL +
                        ConstantsHelper.ESTABLISH_CONNECTION + "?id=${param[0]}"
            )
        if (response.isNotEmpty()) {
            return response.toBoolean()
        }
        return false;
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        onConnectionListener.onConnectionSuccess(result!!)
    }

    fun setOnConnectionListener(onConnectionListener: OnConnectionListener, id: String) {
        this.onConnectionListener = onConnectionListener
        super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id)
    }
}