package ipca.example.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    var contacts : List<Contact> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewContacts = findViewById<ListView>(R.id.listViewContacts)
        val contactsAdapter = ContactsAdapter()
        listViewContacts.adapter = contactsAdapter

        findViewById<FloatingActionButton>(R.id.floatingActionButton)
            .setOnClickListener {
                val intent = Intent(this@MainActivity, ContactDetailActivity::class.java)
                startActivity(intent)
        }

        AppDatabase
            .getDatabase(this@MainActivity)
            ?.contactDao()
            ?.getAllLive()?.observe(this){
                this@MainActivity.contacts = it
                contactsAdapter.notifyDataSetChanged()
            }



        
    }

    inner class ContactsAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return contacts.size
        }

        override fun getItem(position: Int): Any {
            return contacts[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_contact,viewGroup,false)

            val textViewName = rootView.findViewById<TextView>(R.id.textViewName)
            val textViewContact = rootView.findViewById<TextView>(R.id.textViewContact)
            textViewName.text = contacts[position].name
            textViewContact.text = contacts[position].phone

            rootView.setOnClickListener {
                val intent = Intent(this@MainActivity, ContactDetailActivity::class.java)
                intent.putExtra(ContactDetailActivity.CONTACT_ID, contacts[position].uid)
                startActivity(intent)
            }

            return rootView
        }
    }

}