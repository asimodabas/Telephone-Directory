package com.asimodabas.telephone_directory_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class KisilerAdapter(
    private val mContext: Context,
    private var kisilerListe: List<Kisiler>,
    private val vt: VeriTabaniYardimcisi) : RecyclerView.Adapter<KisilerAdapter.CardTasarimTutucu>() {

    inner class CardTasarimTutucu(tasarim: View) : RecyclerView.ViewHolder(tasarim) {
        var textViewKisiBilgi: TextView
        var imageViewNokta: ImageView

        init {
            textViewKisiBilgi = tasarim.findViewById(R.id.textViewKisiBilgi)
            imageViewNokta = tasarim.findViewById(R.id.imageViewNokta)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim =
            LayoutInflater.from(mContext).inflate(R.layout.kisi_card_tasarim, parent, false)
        return CardTasarimTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return kisilerListe.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val kisi = kisilerListe.get(position)

        holder.textViewKisiBilgi.text = "${kisi.kisi_ad} - ${kisi.kisi_tel}"

        holder.imageViewNokta.setOnClickListener {

            val popupmenu = androidx.appcompat.widget.PopupMenu(mContext, holder.imageViewNokta)
            popupmenu.menuInflater.inflate(R.menu.popup_menu, popupmenu.menu)

            popupmenu.setOnMenuItemClickListener { menuItem ->

                when (menuItem.itemId) {
                    R.id.action_sil -> {

                        Snackbar.make(
                            holder.imageViewNokta,
                            "Are you want to delete ${kisi.kisi_ad} ?",
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction("YES") {

                                Kisilerdao().kisiSil(vt,kisi.kisi_id)
                                kisilerListe = Kisilerdao().tumKisiler(vt)
                                notifyDataSetChanged()

                            }.show()
                        true
                    }
                    R.id.action_guncelle -> {

                        alertGoster(kisi)

                        true
                    }
                    else ->
                        false
                }
            }

            popupmenu.show()

        }
    }

    fun alertGoster(kisi: Kisiler) {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.alert_tasarim, null)
        val editTextAd = tasarim.findViewById(R.id.editTextAd) as EditText
        val editTextTel = tasarim.findViewById(R.id.editTextTel) as EditText

        editTextAd.setText(kisi.kisi_ad)
        editTextTel.setText(kisi.kisi_tel)


        val ad = AlertDialog.Builder(mContext)

        ad.setTitle("Update Person")
        ad.setView(tasarim)

        ad.setPositiveButton("Update") { DialogInterface, i ->

            val kisi_ad = editTextAd.text.toString().trim()
            val kisi_tel = editTextTel.text.toString().trim()

            Kisilerdao().kisiGuncelle(vt,kisi.kisi_id,kisi_ad,kisi_tel)
            kisilerListe = Kisilerdao().tumKisiler(vt)
            notifyDataSetChanged()

            Toast.makeText(mContext, "$kisi_ad - $kisi_tel", Toast.LENGTH_SHORT).show()
        }

        ad.setNegativeButton("Cancel") { DialogInterface, i ->

        }

        ad.create().show()

    }

}