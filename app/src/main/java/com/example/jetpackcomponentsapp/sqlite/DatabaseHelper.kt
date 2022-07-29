package com.example.jetpackcomponentsapp.sqlite

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.jetpackcomponentsapp.model.CustomEntity

class DatabaseHelper : SQLiteOpenHelper {

    //private val database : SQLiteDatabase
    private var writable : SQLiteDatabase
    private var readable : SQLiteDatabase

    constructor(context : Context, database : String, factory : SQLiteDatabase.CursorFactory?, version : Int) : super(context, database, factory, version) {
        writable = this.getWritableDatabase()
        readable = this.getReadableDatabase()
    }

    override fun onCreate(db : SQLiteDatabase?) {
        db?.execSQL(
            SQLQueryHelper.createTable(
                "custom_table",
                "Name STRING, Icon INTEGER, Id INTEGER PRIMARY KEY AUTOINCREMENT"
            )
        )
    }

    override fun onUpgrade(db : SQLiteDatabase?, oldVersion : Int, newVersion : Int) {
        if (newVersion == oldVersion + 1) {
            db?.execSQL(SQLQueryHelper.dropTableIfExist("custom_table"))
            onCreate(db)
        }
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    @Throws(SQLException::class)
    public suspend fun insert(table : String, customEntity : CustomEntity) {
        writable.execSQL(
            SQLQueryHelper.insertInto(
                table,
                "Name, Icon", "'${customEntity.name}', ${customEntity.icon}"
            )
        )
    }

    @Throws(SQLException::class)
    public suspend fun update(table : String, customEntity : CustomEntity) {
        writable.execSQL(
            SQLQueryHelper.updateWhere(
                table,
                "Name = '${customEntity.name}', Icon = ${customEntity.icon}",
                "Id",
                "=",
                customEntity.id.toString()
            )
        )

    }

    @Throws(SQLException::class)
    public suspend fun delete(table : String, customEntity : CustomEntity) {
        writable.execSQL(
            SQLQueryHelper.deleteWhere(table, "Id", "=", customEntity.id.toString())
        )
    }

    @Throws(SQLException::class)
    public suspend fun deleteAll(table : String) {
        writable.execSQL(
            SQLQueryHelper.delete(table)
        )
    }

    public suspend fun getAll() : List<CustomEntity> {
        val list : MutableList<CustomEntity> = mutableListOf<CustomEntity>()
        val cursor : Cursor = readable.rawQuery(SQLQueryHelper.selectAll("custom_table"), null)
        if (cursor.moveToFirst()) do {
            list.add(
                CustomEntity(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getString(cursor.getColumnIndex("Name")),
                    cursor.getInt(cursor.getColumnIndex("Icon"))
                )
            )
        } while (cursor.moveToNext())
        return list
    }

    public fun onClose() {
        writable.close()
        readable.close()
        this.close()
    }
}