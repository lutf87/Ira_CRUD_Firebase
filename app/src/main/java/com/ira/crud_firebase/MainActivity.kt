package com.ira.crud_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_save.setOnClickListener(this)
        btn_showdata.setOnClickListener(this)
        btn_logout.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.btn_save -> {

                val getUserID = auth!!.currentUser!!.uid
                val database = FirebaseDatabase.getInstance()

                val getNim: String = ed_nim.getText().toString()
                val getNama: String = ed_nama.getText().toString()
                val getJurusan: String = ed_jurusan.getText().toString()

                val getReference: DatabaseReference
                getReference = database.reference

                if (isEmpty(getNim) || isEmpty(getNama) || isEmpty(getJurusan)) {
                    Toast.makeText(this@MainActivity, "Data tidak boleh kosong!!",
                        Toast.LENGTH_SHORT).show()
                } else {
                    getReference.child("Admin").child(getUserID)
                        .child("Mahasiswa").push()
                        .setValue(data_mahsiswa(getNim, getNama, getJurusan))
                        .addOnCompleteListener(this) {
                            ed_nim.setText("")
                            ed_nama.setText("")
                            ed_jurusan.setText("")
                            Toast.makeText(this@MainActivity, "Data telah tersimpan",
                                Toast.LENGTH_SHORT).show()
                        }
                }
            }
            R.id.btn_logout -> {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(object : OnCompleteListener<Void> {
                        override fun onComplete(p0: Task<Void>) {
                            Toast.makeText(this@MainActivity, "Logout berhasil",
                                Toast.LENGTH_SHORT).show()
                            intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })
            }
            R.id.btn_showdata -> {
                startActivity(Intent(this@MainActivity, MyListData::class.java))
            }
        }
    }
}