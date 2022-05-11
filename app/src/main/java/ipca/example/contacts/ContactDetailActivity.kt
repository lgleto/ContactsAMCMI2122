package ipca.example.contacts

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        val name = intent.getStringExtra(CONTACT_NAME)
        val phone = intent.getStringExtra(CONTACT_PHONE)

        val editTextDetailName = findViewById<TextView>(R.id.editTextDetailName)
        val editTextDetailContact = findViewById<TextView>(R.id.editTextDetailContact)
        val buttonCall = findViewById<Button>(R.id.buttonCall)
        val fabSave = findViewById<FloatingActionButton>(R.id.fabSave)
        val fabDelete = findViewById<FloatingActionButton>(R.id.fabDelete)

        editTextDetailName.text = name
        editTextDetailContact.text =  phone

        buttonCall.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$phone")
            startActivity(callIntent)
        }

        fabSave.setOnClickListener {
            val contact = Contact(
                editTextDetailName.text.toString(),
                editTextDetailContact.text.toString()
            )
            GlobalScope.launch (Dispatchers.IO){
                AppDatabase.getDatabase(this@ContactDetailActivity)?.contactDao()?.insert(contact)
                GlobalScope.launch (Dispatchers.Main) {
                    finish()
                }
            }


        }




    }

    companion object{
        const val CONTACT_NAME = "contact_name"
        const val CONTACT_PHONE = "contact_phone"
    }
}