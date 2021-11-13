package com.asimodabas.telephone_directory_app

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var kisilerListe: ArrayList<Kisiler>
    private lateinit var adapter: KisilerAdapter
    private lateinit var vt: VeriTabaniYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Telephone Directory"
        setSupportActionBar(toolbar)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

        vt = VeriTabaniYardimcisi(this)

        tumKisilerAl()

        fab.setOnClickListener {
            alertGoster()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val item = menu?.findItem(R.id.action_ara)
        val searchView=item?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }


    fun alertGoster() {
        val tasarim = LayoutInflater.from(this).inflate(R.layout.alert_tasarim, null)
        val editTextAd = tasarim.findViewById(R.id.editTextAd) as EditText
        val editTextTel = tasarim.findViewById(R.id.editTextTel) as EditText

        val ad = AlertDialog.Builder(this)

        ad.setTitle("Add Person")
        ad.setView(tasarim)

        ad.setPositiveButton("Add") { DialogInterface, i ->

            val kisi_ad = editTextAd.text.toString().trim()
            val kisi_tel = editTextTel.text.toString().trim()

            Kisilerdao().kisiEkle(vt,kisi_ad,kisi_tel)
            tumKisilerAl()

            Toast.makeText(applicationContext, "$kisi_ad - $kisi_tel", Toast.LENGTH_SHORT).show()
        }

        ad.setNegativeButton("Cancel") { DialogInterface, i ->

        }

        ad.create().show()

    }

    override fun onQueryTextSubmit(query: String): Boolean {

        aramaYap(query)
        Log.e("Gönderilen arama", query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {

        aramaYap(newText)
        Log.e("Harf girdikçe", newText)
        return true
    }

    fun tumKisilerAl(){
        kisilerListe = Kisilerdao().tumKisiler(vt)

        adapter = KisilerAdapter(this, kisilerListe)
        rv.adapter = adapter

    }

    fun aramaYap(aramaKelime:String){
        kisilerListe = Kisilerdao().kisiAra(vt,aramaKelime)

        adapter = KisilerAdapter(this, kisilerListe)
        rv.adapter = adapter

    }

}




