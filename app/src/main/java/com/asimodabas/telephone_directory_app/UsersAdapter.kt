package com.asimodabas.telephone_directory_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class UsersAdapter(
    private val mContext: Context,
    private var usersList: List<Users>,
    private val vt: DatabaseHelper
) : RecyclerView.Adapter<UsersAdapter.cardHolder>() {

    inner class cardHolder(cardLayout: View) : RecyclerView.ViewHolder(cardLayout) {
        var textViewUserInfo: TextView
        var imageViewMore: ImageView

        init {
            textViewUserInfo = cardLayout.findViewById(R.id.textViewUserInfo)
            imageViewMore = cardLayout.findViewById(R.id.imageViewMore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardHolder {
        val cardLayout =
            LayoutInflater.from(mContext).inflate(R.layout.user_card_layout, parent, false)
        return cardHolder(cardLayout)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: cardHolder, position: Int) {
        val kisi = usersList[position]
        val popupmenu = androidx.appcompat.widget.PopupMenu(mContext, holder.imageViewMore)

        holder.textViewUserInfo.text = "${kisi.user_name} - ${kisi.user_phone}"
        holder.imageViewMore.setOnClickListener {

            popupmenu.menuInflater.inflate(R.menu.popup_menu, popupmenu.menu)
            popupmenu.setOnMenuItemClickListener { menuItem ->

                when (menuItem.itemId) {
                    R.id.action_delete -> {
                        Snackbar.make(
                            holder.imageViewMore,
                            "Are you want to delete ${kisi.user_name} ?",
                            Snackbar.LENGTH_SHORT
                        ).setAction("YES") {

                            UsersDAO().deleteUser(vt, kisi.user_id)
                            usersList = UsersDAO().allUsers(vt)
                            notifyDataSetChanged()

                        }.show()
                        true
                    }
                    R.id.action_update -> {
                        showAlert(kisi)
                        true
                    }
                    else -> false
                }
            }
            popupmenu.show()
        }
    }

    fun showAlert(user: Users) {
        val alertLayout = LayoutInflater.from(mContext).inflate(R.layout.alert_layout, null)
        val editTextName = alertLayout.findViewById(R.id.editTextName) as EditText
        val editTextPhone = alertLayout.findViewById(R.id.editTextPhone) as EditText
        val ad = AlertDialog.Builder(mContext)

        editTextName.setText(user.user_name)
        editTextPhone.setText(user.user_phone)

        ad.setTitle("Update Person")
        ad.setView(alertLayout)

        ad.setPositiveButton("Update") { DialogInterface, i ->
            val userName = editTextName.text.toString().trim()
            val userPhone = editTextPhone.text.toString().trim()

            UsersDAO().updateUser(vt, user.user_id, userName, userPhone)
            usersList = UsersDAO().allUsers(vt)
            notifyDataSetChanged()

            Toast.makeText(mContext, "$userName - $userPhone", Toast.LENGTH_SHORT).show()
        }

        ad.setNegativeButton("Cancel") { DialogInterface, i ->

        }
        ad.create().show()
    }
}