package com.asimodabas.telephone_directory_app

import android.annotation.SuppressLint
import android.content.ContentValues

class UsersDAO() {

    fun deleteUser(vt: DatabaseHelper, kisi_id: Int) {
        val db = vt.writableDatabase

        db.delete("kisiler", "kisi_id=?", arrayOf(kisi_id.toString()))
        db.close()
    }

    fun addUser(vt: DatabaseHelper, kisi_ad: String, kisi_tel: String) {
        val db = vt.writableDatabase
        val values = ContentValues()

        values.put("kisi_ad", kisi_ad)
        values.put("kisi_tel", kisi_tel)

        db.insertOrThrow("kisiler", null, values)
        db.close()
    }

    fun updateUser(vt: DatabaseHelper, kisi_id: Int, kisi_ad: String, kisi_tel: String) {
        val db = vt.writableDatabase
        val values = ContentValues()

        values.put("kisi_ad", kisi_ad)
        values.put("kisi_tel", kisi_tel)

        db.update("kisiler", values, "kisi_id=?", arrayOf(kisi_id.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun allUsers(vt: DatabaseHelper): ArrayList<Users> {
        val db = vt.writableDatabase
        val usersListe = ArrayList<Users>()
        val c = db.rawQuery("SELECT * FROM kisiler", null)

        while (c.moveToNext()) {
            val kisi = Users(
                c.getInt(c.getColumnIndex("kisi_id")),
                c.getString(c.getColumnIndex("kisi_ad")),
                c.getString(c.getColumnIndex("kisi_tel"))
            )

            usersListe.add(kisi)
        }
        return usersListe
    }

    @SuppressLint("Range")
    fun searchUser(vt: DatabaseHelper, aramaKelime: String): ArrayList<Users> {
        val db = vt.writableDatabase
        val usersListe = ArrayList<Users>()
        val c = db.rawQuery("SELECT * FROM kisiler WHERE kisi_ad like '%$aramaKelime%'", null)

        while (c.moveToNext()) {
            val kisi = Users(
                c.getInt(c.getColumnIndex("kisi_id")),
                c.getString(c.getColumnIndex("kisi_ad")),
                c.getString(c.getColumnIndex("kisi_tel"))
            )

            usersListe.add(kisi)
        }
        return usersListe
    }
}
