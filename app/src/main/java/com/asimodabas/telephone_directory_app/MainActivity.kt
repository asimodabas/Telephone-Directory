package com.asimodabas.telephone_directory_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.activity_main.rv
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var usersList: ArrayList<Users>
    private lateinit var adapter: UsersAdapter
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Telephone Directory"
        setSupportActionBar(toolbar)
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)
        dbHelper = DatabaseHelper(this)
        selectAllUsers()
        fab.setOnClickListener {
            showAlert()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val item = menu?.findItem(R.id.action_ara)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    fun showAlert() {
        val alertLayout = LayoutInflater.from(this).inflate(R.layout.alert_layout, null)
        val editTextAd = alertLayout.findViewById(R.id.editTextName) as EditText
        val editTextTel = alertLayout.findViewById(R.id.editTextPhone) as EditText
        val ad = AlertDialog.Builder(this)

        ad.setTitle("Add Person")
        ad.setView(alertLayout)
        ad.setPositiveButton("Add") { DialogInterface, i ->
            val userName = editTextAd.text.toString().trim()
            val userPhone = editTextTel.text.toString().trim()

            UsersDAO().addUser(dbHelper, userName, userPhone)
            selectAllUsers()
            Toast.makeText(applicationContext, "$userName - $userPhone", Toast.LENGTH_SHORT).show()
            DialogInterface.dismiss()
        }

        ad.setNegativeButton("Cancel") { DialogInterface, i ->
            DialogInterface.dismiss()
        }
        ad.create().show()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchUsers(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        searchUsers(newText)
        return true
    }

    fun selectAllUsers() {
        usersList = UsersDAO().allUsers(dbHelper)
        adapter = UsersAdapter(this, usersList, dbHelper)
        rv.adapter = adapter
    }

    fun searchUsers(searchText: String) {
        usersList = UsersDAO().searchUser(dbHelper, searchText)
        adapter = UsersAdapter(this, usersList, dbHelper)
        rv.adapter = adapter
    }
}




