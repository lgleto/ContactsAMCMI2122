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

    var contact : Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        val uid = intent.getStringExtra(CONTACT_ID)

        val editTextDetailName = findViewById<TextView>(R.id.editTextDetailName)
        val editTextDetailContact = findViewById<TextView>(R.id.editTextDetailContact)
        val buttonCall = findViewById<Button>(R.id.buttonCall)
        val fabSave = findViewById<FloatingActionButton>(R.id.fabSave)
        val fabDelete = findViewById<FloatingActionButton>(R.id.fabDelete)


        uid?.let {
            GlobalScope.launch (Dispatchers.IO) {
                contact = AppDatabase.getDatabase(this@ContactDetailActivity)?.contactDao()?.getById(it)
                GlobalScope.launch (Dispatchers.Main) {
                    editTextDetailName.text = contact?.name
                    editTextDetailContact.text =  contact?.phone
                }
            }
        }


        buttonCall.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:${contact?.phone}")
            startActivity(callIntent)
        }

        fabSave.setOnClickListener {
            contact?.let {
                it.name =  editTextDetailName.text.toString()
                it.phone = editTextDetailContact.text.toString()
                GlobalScope.launch (Dispatchers.IO){
                    AppDatabase.getDatabase(this@ContactDetailActivity)?.contactDao()?.update(it)
                    GlobalScope.launch (Dispatchers.Main) {
                        finish()
                    }
                }
            }?: run {
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

        fabDelete.setOnClickListener {
            contact?.let {
                GlobalScope.launch(Dispatchers.IO) {
                    AppDatabase.getDatabase(this@ContactDetailActivity)?.contactDao()
                        ?.delete(it)
                    GlobalScope.launch(Dispatchers.Main) {
                        finish()
                    }
                }
            }?:run{
                finish()
            }
        }

    }

    companion object{
        const val CONTACT_ID = "contact_id"
    }
}