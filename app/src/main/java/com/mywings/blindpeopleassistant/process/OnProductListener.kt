package com.mywings.blindpeopleassistant.process

import org.json.JSONObject

interface OnProductListener {
    fun onProductSuccess(result: JSONObject)
}