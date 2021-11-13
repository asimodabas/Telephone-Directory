package com.asimodabas.telephone_directory_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KisilerAdapter(private val mContext: Context, private val kisilerListe: List<Kisiler>) :
    RecyclerView.Adapter<KisilerAdapter.CardTasarimTutucu>() {

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

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val kisi = kisilerListe.get(position)

        holder.textViewKisiBilgi.text = "${kisi.kisi_ad} - ${kisi.kisi_tel}"

        holder.imageViewNokta.setOnClickListener {

            val popupmenu = androidx.appcompat.widget.PopupMenu(mContext, holder.imageViewNokta)
            popupmenu.menuInflater.inflate(R.menu.popup_menu, popupmenu.menu)

            popupmenu.setOnMenuItemClickListener { menuItem ->

                when (menuItem.itemId) {
                    R.id.action_sil -> {
                        true
                    }
                    R.id.action_guncelle -> {
                        true
                    }
                    else ->
                        false
                }

            }

            popupmenu.show()

        }
    }

    override fun getItemCount(): Int {
        return kisilerListe.size
    }


}