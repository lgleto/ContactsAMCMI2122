package ipca.example.contacts

import androidx.annotation.NonNull
import androidx.room.*
import java.util.*

@Entity
class Contact {

    @NonNull
    @PrimaryKey var uid: String
    var name: String? =  null
    var phone : String? =  null

    constructor(name: String?, phone: String?) {
        this.uid = UUID.randomUUID().toString()
        this.name = name
        this.phone = phone
    }
}

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM contact WHERE uid = :uid")
    fun getById(uid:String ): Contact

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)
}

