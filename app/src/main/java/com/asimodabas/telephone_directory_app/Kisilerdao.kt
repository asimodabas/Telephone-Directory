package com.asimodabas.telephone_directory_app

import android.annotation.SuppressLint
import android.content.ContentValues

class Kisilerdao() {

    fun kisiSil(vt: VeriTabaniYardimcisi, kisi_id: Int) {

        val db = vt.writableDatabase
        db.delete("kisiler", "kisi_id=?", arrayOf(kisi_id.toString()))
        db.close()

    }

    fun kisiEkle(vt: VeriTabaniYardimcisi, kisi_ad: String, kisi_tel: String) {

        val db = vt.writableDatabase
        val values = ContentValues()
        values.put("kisi ad", kisi_ad)
        values.put("kisi tel", kisi_tel)

        db.insertOrThrow("kisiler", null, values)
        db.close()

    }

    fun kisiGuncelle(vt: VeriTabaniYardimcisi, kisi_id: Int, kisi_ad: String, kisi_tel: String) {

        val db = vt.writableDatabase
        val values = ContentValues()

        values.put("kisi ad", kisi_ad)
        values.put("kisi tel", kisi_tel)

        db.update("kisiler", values, "kisi_id=?", arrayOf(kisi_id.toString()))
        db.close()

    }

    @SuppressLint("Range")
    fun tumKisiler(vt: VeriTabaniYardimcisi): ArrayList<Kisiler> {

        val db = vt.writableDatabase
        val kisilerListe = ArrayList<Kisiler>()
        val c = db.rawQuery("SELECT * FROM kisiler", null)

        while (c.moveToNext()) {
            val kisi = Kisiler(
                c.getInt(c.getColumnIndex("kisi_id")),
                c.getString(c.getColumnIndex("kisi_ad")),
                c.getString(c.getColumnIndex("kisi_tel")))

            kisilerListe.add(kisi)
        }
        return kisilerListe
    }

    fun kisiAra(vt: VeriTabaniYardimcisi,aramaKelime:String): ArrayList<Kisiler> {

        val db = vt.writableDatabase
        val kisilerListe = ArrayList<Kisiler>()
        val c = db.rawQuery("SELECT * FROM kisiler WHERE kisi_ad like '%$aramaKelime%'", null)

        while (c.moveToNext()) {
            val kisi = Kisiler(
                c.getInt(c.getColumnIndex("kisi_id")),
                c.getString(c.getColumnIndex("kisi_ad")),
                c.getString(c.getColumnIndex("kisi_tel")))

            kisilerListe.add(kisi)
        }
        return kisilerListe
    }

}
