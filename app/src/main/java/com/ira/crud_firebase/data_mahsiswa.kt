package com.ira.crud_firebase

class data_mahsiswa {
    var nim: String? = null
    var nama: String? = null
    var jurusan: String? = null
    var key: String? = null

    constructor(){}

    constructor(nim: String?, nama: String?, jurusan: String?) {
        this.nim = nim
        this.nama = nama
        this.jurusan = jurusan
    }
}