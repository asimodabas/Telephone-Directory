package com.asimodabas.telephone_directory_app

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var kisilerListe: ArrayList<Kisiler>
    private lateinit var adapter: KisilerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Telephone Directory"
        setSupportActionBar(toolbar)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

        kisilerListe = ArrayList()
        val k1 = Kisiler(1, "Ahmet", "6545568")
        val k2 = Kisiler(2, "Zeynep", "534534545568")
        val k3 = Kisiler(3, "Asım", "47656756")
        val k4 = Kisiler(4, "Mert", "44723448")

        kisilerListe.add(k1)
        kisilerListe.add(k2)
        kisilerListe.add(k3)
        kisilerListe.add(k4)

        adapter = KisilerAdapter(this, kisilerListe)
        rv.adapter = adapter

        fab.setOnClickListener {
            alertGoster()
        }
    }

    fun alertGoster() {
        val tasarim = LayoutInflater.from(this).inflate(R.layout.alert_tasarim, null)
        val editTextAd = tasarim.findViewById(R.id.editTextAd) as EditText
        val editTextTel = tasarim.findViewById(R.id.editTextTel) as EditText

        val ad = AlertDialog.Builder(this)

        ad.setTitle("Add Person")
        ad.setView(tasarim)

        ad.setPositiveButton("Add") {
                DialogInterface, i ->

            val kisi_ad = editTextAd.text.toString().trim()
            val kisi_tel = editTextTel.text.toString().trim()

            Toast.makeText(applicationContext,"$kisi_ad - $kisi_tel",Toast.LENGTH_SHORT).show()
        }

        ad.setNegativeButton("Cancel") {
                DialogInterface, i ->

        }

        ad.create().show()

    }

}




