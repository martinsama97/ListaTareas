package samaniego.martin.listatareas

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    lateinit var et_tarea : EditText
    lateinit var btn_agregar : Button
    lateinit var lv_tareas : ListView
    lateinit var lista_tareas : ArrayList<String>
    lateinit var adaptador : ArrayAdapter<String>
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_tarea = findViewById(R.id.et_tarea)
        btn_agregar = findViewById(R.id.btn_agregar)
        lv_tareas = findViewById(R.id.lv_tareas)

        lista_tareas = ArrayList()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "tareas-db"
        ).allowMainThreadQueries().build()

        cargar_Tareas()

        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista_tareas)
        lv_tareas.adapter = adaptador

        btn_agregar.setOnClickListener {
            val tarea_str = et_tarea.text.toString()

            if (!tarea_str.isNullOrEmpty()) {
                var tarea = Tarea(desc = tarea_str)
                agregar_Tarea(tarea)
            } else {
                Toast.makeText(this, "Tarea vacia", Toast.LENGTH_SHORT).show()
            }
        }


        lv_tareas.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val tarea_desc = lista_tareas[position]

            val tarea = db.tareaDao().getTarea(tarea_desc)

            eliminar_Tarea(tarea, position)

        }

        lv_tareas.setOnItemLongClickListener { parent, view, position, id ->
            val tarea_desc = lista_tareas[position]

            val tarea = db.tareaDao().getTarea(tarea_desc)

            editar_Tarea(tarea, position)

            return@setOnItemLongClickListener true

        }


    }

    private fun cargar_Tareas()
    {
        var tareas = db.tareaDao().obtenerTareas()
        for (tarea in tareas)
        {
            lista_tareas.add(tarea.desc)
        }
    }

    private fun agregar_Tarea(tarea: Tarea)
    {
        db.tareaDao().agregarTarea(tarea)
        lista_tareas.add(tarea.desc)
        adaptador.notifyDataSetChanged()
        et_tarea.setText("")
    }

    private fun eliminar_Tarea(tarea: Tarea, position: Int)
    {
        db.tareaDao().eliminarTarea(tarea)
        lista_tareas.removeAt(position)
        adaptador.notifyDataSetChanged()
    }

    private fun editar_Tarea(tarea: Tarea, position: Int)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar tarea")

        val input = EditText(this)
        input.setText(tarea.desc)
        builder.setView(input)

        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevaDesc = input.text.toString()

            if (nuevaDesc.isNotEmpty()) {
                tarea.desc = nuevaDesc
                db.tareaDao().editarTarea(tarea)

                // Actualizar la lista y el adaptador
                lista_tareas[position] = nuevaDesc
                adaptador.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "La tarea no puede estar vacía", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

}