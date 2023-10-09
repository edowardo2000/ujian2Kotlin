package com.example.edo_ujianretrofit.adapter

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.edo_ujianretrofit.model.TrxpinjamanItem
import com.example.edo_ujianretrofit.R

class UserAdapter (var data: List<TrxpinjamanItem?>, private val clickListener: (TrxpinjamanItem) -> Unit, private val onLongclick : (TrxpinjamanItem)-> Unit): RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    fun setTrx(trx: List<TrxpinjamanItem?>?){
        if (trx != null){
            data = trx
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listtrx, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNama.text = data[holder.adapterPosition]?.namaLengkap
        holder.txtAlamat.text = data[holder.adapterPosition]?.alamat
        holder.txtOutstanding.text = data[holder.adapterPosition]?.jumlahOutstanding

        holder.itemView.setOnClickListener {
            clickListener(data[holder.adapterPosition]!!)
        }

        holder.imgremove.setOnClickListener {
            val alertDialog = AlertDialog.Builder(holder.itemView.context)
                .setTitle("Hapus Data")
                .setMessage("Apakah anda yakin ingin menghapus data ini ?")
                .setPositiveButton("Ya"){dialog, which ->
                    onLongclick(data.get(position)!!)
                    Log.e("INFO", "onFailure: ${data.get(position)!!}")
                }
                .setNegativeButton("Tidak",null)
                .create()
            alertDialog.show()
        }
        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                val alertDialog = AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Hapus Data")
                    .setMessage("Apakah anda yakin ingin menghapus data ini ?")
                    .setPositiveButton("Ya"){dialog, which ->
                        onLongclick(data.get(position)!!)
                        Log.e("INFO", "onFailure: ${data.get(position)!!}")
                    }
                    .setNegativeButton("Tidak",null)
                    .create()
                alertDialog.show()
                return true
            }
        })

//        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
//            override fun onLongClick(v: View?): Boolean {
//                val alertDialog = AlertDialog.Builder(holder.itemView.context)
//                    .setTitle("Hapus Data")
//                    .setMessage("Apakah kamu ingin menghapus data ini ?")
//                    .setPositiveButton("Delete"){dialog, which ->
//
//                        onLongclick(data[holder.adapterPosition]!!)
//                    }
//                    .setNegativeButton("Cancel",null)
//                    .create()
//                alertDialog.show()
//                return true
//            }
//        })
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNama = itemView.findViewById<TextView>(R.id.txtNama)
        val txtAlamat = itemView.findViewById<TextView>(R.id.txtAlamat)
        val txtOutstanding = itemView.findViewById<TextView>(R.id.txtOutstanding)
        val imgremove = itemView.findViewById<ImageView>(R.id.imgremove)

    }
}