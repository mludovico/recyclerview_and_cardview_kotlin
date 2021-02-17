package com.example.recyclerviewandcardview

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewandcardview.ContactDetail.Companion.EXTRA_CONTACT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_menu.*

class MainActivity : AppCompatActivity(), IClickContactItemListener {

    private val recyclerView: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerView)
    }
    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        initDrawer()
        fetchContactList()
        bindViews()
    }

    private fun fetchContactList() {
        val list = arrayListOf(
            Contact(
                "Marcelo Ludovico",
                "+55 19 996856275",
                "img.png"
            ),
            Contact(
                "José Alexandre",
                "+55 19 981324568",
                "img1.jpg"
            )
        )
        getSharedPreferencesInstance().edit {
            val json = Gson().toJson(list)
            putString("contacts", json)
            commit()
        }
    }

    private fun getSharedPreferencesInstance(): SharedPreferences {
        return getSharedPreferences("com.example.recyclerviewandcardview.PREFERENCES", Context.MODE_PRIVATE)
    }

    private fun bindViews() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    private fun getListContacts(): List<Contact> {
        val list = getSharedPreferencesInstance().getString("contacts", "[]")
        val turnType = object: TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(list, turnType)
    }

    private fun updateList() {
        val list = getListContacts()
        adapter.updateList(list as ArrayList<Contact>)
    }

    private fun initDrawer() {
        setSupportActionBar(toolbar)
        var toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.item_menu_1 -> {
                showToast("Exibindo menu 1")
                return true
            }
            R.id.item_menu_2 -> {
                showToast("Exibindo menu 2")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun clickContactItem(contact: Contact) {
        val intent = Intent(this, ContactDetail::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)
    }
}