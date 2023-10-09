package com.example.edo_ujianretrofit.uifragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.edo_ujianretrofit.R
import com.example.edo_ujianretrofit.apiservice.APIConfig
import com.example.edo_ujianretrofit.model.ResponseSuccess
import com.example.edo_ujianretrofit.model.TrxpinjamanItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTrx.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTrx : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: TrxpinjamanItem? = null
    lateinit var editNama: EditText
    lateinit var editAlamat: EditText
    lateinit var editOutstanding: EditText
    lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getParcelable(ARG_PARAM2,TrxpinjamanItem::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_trx, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editNama = view.findViewById(R.id.editNama)
        editAlamat = view.findViewById(R.id.editAlamat)
        editOutstanding = view.findViewById(R.id.editOutstanding)
        btnSend = view.findViewById(R.id.btnSend)

        if(param1 == "add"){

            btnSend.setOnClickListener(View.OnClickListener {
                addDataPinjaman(TrxpinjamanItem(editAlamat.text.toString(),
                    editNama.text.toString(),
                    null,
                    editOutstanding.text.toString()))
            })

        }else{
            editNama.setText( param2?.namaLengkap)
            editAlamat.setText(param2?.alamat)
            editOutstanding.setText(param2?.jumlahOutstanding)

            btnSend.setOnClickListener {

                updateData(TrxpinjamanItem(editAlamat.text.toString(),editNama.text.toString(),param2?.id,editOutstanding.text.toString()))
            }
        }



    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddTrx.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: TrxpinjamanItem) =
            AddTrx().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putParcelable(ARG_PARAM2, param2)
                }
            }
    }

    fun addDataPinjaman (data : TrxpinjamanItem){


        val client = APIConfig.getApiService()
            .addDataPinjaman(toRequestBody(data.namaLengkap.toString()),
                toRequestBody(data.alamat.toString()),
                toRequestBody(data.jumlahOutstanding.toString())
            )

        client.enqueue(object : Callback<ResponseSuccess> {
            override fun onResponse(
                call: Call<ResponseSuccess>,
                response: Response<ResponseSuccess>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("INFO", "onSuccess: ${responseBody.message}")
                    parentFragmentManager.popBackStackImmediate()
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })
    }


    fun updateData(data : TrxpinjamanItem){
        val client = APIConfig.getApiService()
            .updateDataPinjaman(toRequestBody(data.id.toString()),toRequestBody(data.namaLengkap.toString()),
                toRequestBody(data.alamat.toString()),
                toRequestBody(data.jumlahOutstanding.toString())
            )
//        progressBar.visibility=View.VISIBLE
        client.enqueue(object : Callback<ResponseSuccess> {
            override fun onResponse(
                call: Call<ResponseSuccess>,
                response: Response<ResponseSuccess>
            ) {
                Log.e("masukkkk", response.toString())

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
//                    showProgresBar(false)
                    Log.e("INFO", "onSuccess: ${responseBody.message}")
                    parentFragmentManager.popBackStackImmediate()
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
//                showProgresBar(false)
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun toRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}