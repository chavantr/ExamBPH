package com.mywings.blindpeopleassistant

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProjectDatabaseHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :

    SQLiteOpenHelper(context, name, factory, version) {


    val TABLE_DATA = "CREATE TABLE IF NOT EXISTS SCANDATA(ID INTEGER PRIMARY KEY AUTOINCREMENT,NUMBER TEXT, DATA TEXT)"


    override fun onCreate(db: SQLiteDatabase?) {

        db!!.execSQL(TABLE_DATA)



    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}