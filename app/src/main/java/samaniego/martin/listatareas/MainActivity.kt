package samaniego.martin.listatareas

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var et_tarea : EditText
    lateinit var btn_agregar : Button
    lateinit var lv_tareas : ListView
    lateinit var lista_tareas : ArrayList<String>
    lateinit var adaptador : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_tarea = findViewById(R.id.et_tarea)
        btn_agregar = findViewById(R.id.btn_agregar)
        lv_tareas = findViewById(R.id.lv_tareas)

        lista_tareas = ArrayList()

        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista_tareas)
        lv_tareas.adapter = adaptador

        btn_agregar.setOnClickListener {
            var tarea = et_tarea.text.toString()

            if (!tarea.isNullOrEmpty())  {
                lista_tareas.add(tarea)
                adaptador.notifyDataSetChanged()
                et_tarea.setText("")
            } else {
                Toast.makeText(this, "Llenas campo", Toast.LENGTH_SHORT).show()
            }
        }

        lv_tareas.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            lista_tareas.removeAt(position)
            adaptador.notifyDataSetChanged()
        }
    }
}