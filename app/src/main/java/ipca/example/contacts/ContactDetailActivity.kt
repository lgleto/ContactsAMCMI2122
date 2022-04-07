package ipca.example.contacts

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ContactDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        val name = intent.getStringExtra(CONTACT_NAME)
        val phone = intent.getStringExtra(CONTACT_PHONE)

        val textViewName = findViewById<TextView>(R.id.textViewDetailName)
        val textViewPhone = findViewById<TextView>(R.id.textViewDetailContact)
        val buttonCall = findViewById<Button>(R.id.buttonCall)

        textViewName.text = name
        textViewPhone.text =  phone

        buttonCall.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$phone")
            startActivity(callIntent)
        }

    }

    companion object{
        const val CONTACT_NAME = "contact_name"
        const val CONTACT_PHONE = "contact_phone"
    }
}