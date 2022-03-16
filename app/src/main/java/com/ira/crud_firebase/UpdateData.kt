package com.ira.crud_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {

    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNIM: String? = null
    private var cekNama: String? = null
    private var cekJurusan: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        supportActionBar!!.title = "Update Data"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        btn_update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                cekNIM = ed_new_nim.getText().toString()
                cekNama = ed_new_nama.getText().toString()
                cekJurusan = ed_new_jurusan.getText().toString()

                if (isEmpty(cekNIM!!) || isEmpty(cekNama!!) || isEmpty(cekJurusan!!)) {
                    Toast.makeText(this@UpdateData, "Data tidak boleh kosong!!",
                        Toast.LENGTH_SHORT).show()
                } else {
                    val setMahasiswa = data_mahsiswa()
                    setMahasiswa.nim = ed_new_nim.getText().toString()
                    setMahasiswa.nama = ed_new_nama.getText().toString()
                    setMahasiswa.jurusan = ed_new_jurusan.getText().toString()
                    updateMahasiswa(setMahasiswa)
                }
            }
        })
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)

    }

    private val data: Unit
        private get() {
            val getNIM = intent.extras!!.getString("dataNIM")
            val getNama = intent.extras!!.getString("dataNama")
            val getJurusan = intent.extras!!.getString("dataJurusan")
            ed_new_nim!!.setText(getNIM)
            ed_new_nama!!.setText(getNama)
            ed_new_jurusan!!.setText(getJurusan)

        }

    private fun updateMahasiswa(mahasiswa: data_mahsiswa) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("Mahasiswa")
            .child(getKey!!)
            .setValue(mahasiswa)
            .addOnSuccessListener {
                ed_new_nim!!.setText("")
                ed_new_nama!!.setText("")
                ed_new_jurusan!!.setText("")
                Toast.makeText(this@UpdateData, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}