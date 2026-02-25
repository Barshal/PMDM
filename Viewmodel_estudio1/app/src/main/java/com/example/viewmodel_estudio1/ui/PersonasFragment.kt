package com.example.viewmodel_estudio1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels // ¡Importante! Usar el de fragment
import com.example.viewmodel_estudio1.R
import com.example.viewmodel_estudio1.data.PersonaRepositoryImp
import com.example.viewmodel_estudio1.viewmodel.PersonaViewModel
import com.example.viewmodel_estudio1.viewmodel.PersonaViewModelFactory

class PersonasFragment : Fragment() {

    // 1. La creación de la fábrica y el repositorio es igual
    private val repository = PersonaRepositoryImp()
    private val viewModelFactory = PersonaViewModelFactory(repository)

    // 2. OBTENER EL VIEWMODEL: La forma recomendada en Fragments
    //    Usa 'by viewModels' de la librería 'fragment-ktx'.
    private val viewModel: PersonaViewModel by viewModels { viewModelFactory }

    // 3. INFLAR LA VISTA: Aquí se conecta el XML con la clase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        return inflater.inflate(R.layout.fragment_personas, container, false)
    }

    // 4. LÓGICA DE LA VISTA: Se ejecuta después de que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referenciar las vistas del layout del fragmento
        val textViewPersonas = view.findViewById<TextView>(R.id.textView_personas)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        // 5. OBSERVAR LOS DATOS: ¡Exactamente igual que en la Activity!
        //    Usamos 'viewLifecycleOwner' en lugar de 'this'
        viewModel.personas.observe(viewLifecycleOwner) { listaPersonas ->
            val textoFormateado = listaPersonas.joinToString(separator = "\n\n") { persona ->
                "Nombre: ${persona.nombre}\nEdad: ${persona.edad}\nCorreo: ${persona.correo}"
            }
            textViewPersonas.text = textoFormateado
        }

        viewModel.cargando.observe(viewLifecycleOwner) { estaCargando ->
            progressBar.visibility = if (estaCargando) View.VISIBLE else View.GONE
        }

        // 6. LLAMAR A LA ACCIÓN: Si no se han cargado aún los datos
        //    (Esto es opcional, podrías tener un ViewModel compartido que ya los tenga)
        viewModel.cargarPersonas()
    }
}
