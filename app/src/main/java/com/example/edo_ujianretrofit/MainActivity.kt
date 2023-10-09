    package com.example.edo_ujianretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.edo_ujianretrofit.adapter.UserAdapter
import com.example.edo_ujianretrofit.apiservice.APIConfig
import com.example.edo_ujianretrofit.model.ResponseGetAllData
import com.example.edo_ujianretrofit.model.TrxpinjamanItem
import com.example.edo_ujianretrofit.uifragment.AddTrx
import com.example.edo_ujianretrofit.uifragment.Trx
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

    class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.frmFragmentRoot, Trx.newInstance("",""))
                .commit()
        }
    }
}