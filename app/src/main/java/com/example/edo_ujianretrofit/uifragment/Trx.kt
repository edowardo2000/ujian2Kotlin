package com.example.edo_ujianretrofit.uifragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edo_ujianretrofit.R
import com.example.edo_ujianretrofit.adapter.UserAdapter
import com.example.edo_ujianretrofit.apiservice.APIConfig
import com.example.edo_ujianretrofit.model.ResponseGetAllData
import com.example.edo_ujianretrofit.model.ResponseSuccess
import com.example.edo_ujianretrofit.model.TrxpinjamanItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
 * Use the [Trx.newInstance] factory method to
 * create an instance of this fragment.
 */
class Trx : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerView: RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var fabAddData: FloatingActionButton
    lateinit var btnSearch: Button
    lateinit var txtSearch : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trx, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.lstTrx)


        fabAddData = view.findViewById(R.id.fabAdd)
        btnSearch = view.findViewById(R.id.btnSearch)
        txtSearch = view.findViewById(R.id.txtSearch)
        fabAddData.setOnClickListener(View.OnClickListener {

            parentFragmentManager.beginTransaction()
                .addToBackStack("add form")
                .replace(R.id.frmFragmentRoot, AddTrx.newInstance("add", TrxpinjamanItem()))
                .commit()

        })

        btnSearch.setOnClickListener(View.OnClickListener {
            getAllDataByFilter()
        })
        getAllTrx()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Trx.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Trx().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getAllTrx(){

        val client = APIConfig.getApiService().getAllData()


        client.enqueue(object : Callback<ResponseGetAllData> {
            override fun onResponse(
                call: Call<ResponseGetAllData>,
                response: Response<ResponseGetAllData>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {

                    userAdapter = UserAdapter(responseBody.data?.trxpinjaman!!, {item ->

                        parentFragmentManager.beginTransaction()
                            .addToBackStack("add form")
                            .replace(R.id.frmFragmentRoot, AddTrx.newInstance("update",item))
                            .commit()
                    } , { item ->
                        deleteDataTodoList(item)

                    })

                    recyclerView.layoutManager = LinearLayoutManager(context)

                    recyclerView.adapter = userAdapter



                }
            }

            override fun onFailure(call: Call<ResponseGetAllData>, t: Throwable) {
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun deleteDataTodoList(data : TrxpinjamanItem){
        val client = APIConfig.getApiService()
            .deleteDataPinjaman(toRequestBody(data.id.toString()))

        client.enqueue(object : Callback<ResponseSuccess> {
            override fun onResponse(
                call: Call<ResponseSuccess>,
                response: Response<ResponseSuccess>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("INFO", "onSuccess: ${responseBody.message}")
                    getAllTrx()
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })


    }

    fun getAllDataByFilter(){
        val client = APIConfig.getApiService().getAllDataByFilter("NamaLengkap", "like", txtSearch.text.toString())

        client.enqueue(object : javax.security.auth.callback.Callback,
            Callback<ResponseGetAllData> {
            override fun onResponse(
                call: Call<ResponseGetAllData>,
                response: Response<ResponseGetAllData>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    userAdapter = UserAdapter(responseBody.data?.trxpinjaman!!, {item ->
                        parentFragmentManager.beginTransaction()
                            .addToBackStack("add form")
                            .replace(R.id.frmFragmentRoot, AddTrx.newInstance("update",item))
                            .commit()
                    } , { item ->
                        deleteDataTodoList(item)
                    })
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = userAdapter
                }
            }

            override fun onFailure(call: Call<ResponseGetAllData>, t: Throwable) {
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun toRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}