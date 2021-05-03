package ubaidillah.qowwim.aplikasi_x0a_2c

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var mhsAdapter: AdapterDataMhs
    var daftarMhs = mutableListOf<HashMap<String, String>>()
    val root = "http://192.168.43.216:8000"
    val BASE_URL = "$root/api/mahasiswa"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mhsAdapter = AdapterDataMhs(daftarMhs)
        listMhs.layoutManager = LinearLayoutManager(this)
        listMhs.adapter = mhsAdapter
        showDataMhs()
    }

    fun showDataMhs(){
        val request = StringRequest(Request.Method.POST, BASE_URL,
                Response.Listener { response ->
                    val jsonobject = JSONObject(response)
                    val jsonArray = JSONArray(jsonobject.getString("data"))
                    Log.d("haaaaa JSON",jsonArray.toString())
                    for (x in 0..(jsonArray.length() - 1)) {
                        val jsonObject = jsonArray.getJSONObject(x)
                        var mhs = HashMap<String, String>()
                        mhs.put("nim", jsonObject.getString("nim"))
                        mhs.put("nama", jsonObject.getString("nama"))
                        mhs.put("nama_prodi", jsonObject.getString("nama_prodi"))
                        mhs.put("alamat", jsonObject.getString("alamat"))
                        mhs.put("url", jsonObject.getString("url"))
//                        mhs.put("alamat",jsonObject.getString("alamat"))
//                        mhs.put("kelamin",jsonObject.getString("kelamin"))
                        daftarMhs.add(mhs)
                    }
                    mhsAdapter.notifyDataSetChanged()
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Terjadi kesalahan koneksi ke server", Toast.LENGTH_SHORT).show()
                })
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }


}
