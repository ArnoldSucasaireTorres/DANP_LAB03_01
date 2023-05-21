package com.example.registrocongreso

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.registrocongreso.ui.theme.RegistroCongresoTheme
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroCongresoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EntradaDatos()
                }

            }
        }
    }
}
@Composable
fun listarAsistentes(asistentes: ArrayList<Asistente>){
    LazyColumn{
        items(asistentes){
            asistente -> mostrarAsistente(asistente,asistentes)
        }
    }
}
@Composable
fun mostrarAsistente(asistente: Asistente,asistentes:ArrayList<Asistente>) {
    Surface{

        Row{
            var isExpanded by remember { mutableStateOf(false) }

            Row(modifier = Modifier
                .padding(all = 10.dp)
                .clickable { isExpanded = !isExpanded }){

                Text(text = "Nombre: ${asistente.nombre}\n" +
                        "Fecha de Inscripción: ${asistente.finscripcion}\n"+
                        "Tipo de Sangre: ${asistente.tsangre}\n"+
                        "Telefono: ${asistente.telefono}\n"+
                        "Correo: ${asistente.correo}\n"+
                        "Monto pagado: ${asistente.montop}",
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1)
                //Icon(imageVector = Icons.Sharp.Delete, contentDescription = "Person Icon")
                val contextForToast = LocalContext.current.applicationContext
                IconButton(
                    onClick = {

                        Toast.makeText(contextForToast, "Click!"+encuentraRegistro(asistentes,asistente), Toast.LENGTH_SHORT).show()
                        eliminarRegistro(asistentes,asistente)

                    }
                ) {
                    Icon(imageVector = Icons.Sharp.Delete, contentDescription = "")
                }
                IconButton(
                    onClick = {
                        Toast.makeText(contextForToast, "edito!"+ encuentraRegistro(asistentes,asistente), Toast.LENGTH_SHORT).show()
                        nombre = asistente.nombre
                        finscripcion = asistente.finscripcion
                        tsangre = asistente.tsangre
                        telefono = asistente.telefono
                        correo = asistente.correo
                        montop = asistente.montop
                        indiceModificacion=encuentraRegistro(asistentes,asistente)
                    }
                ) {
                    Icon(imageVector = Icons.Sharp.Edit, contentDescription = "")
                }
                Spacer(modifier = Modifier.height(5.dp))

            }
        }
    }

}
//Asistente del congreso
data class Asistente(
    var nombre: String, //Nombre del asistente
    var finscripcion: String, //Fecha de inscripcion
    var tsangre: String, //Tipo de sangre
    var telefono: String, //Nro telefono
    var correo: String, //Correo electronico
    var montop: String //Monto pagado
)

@Preview()
@Composable
fun DefaultPreview() {
    RegistroCongresoTheme {

    }
}
var asistentes = ArrayList<Asistente>()
var nombre = ""
var finscripcion = ""
var tsangre = ""
var telefono = ""
var correo = ""
var montop = ""
var indiceModificacion=-1
@Composable
fun EntradaDatos(){

    var txtnombre by remember{mutableStateOf("")}
    var txtfincripcion by remember{mutableStateOf("")}
    var txttsangre by remember{mutableStateOf("")}
    var txttelefono by remember{mutableStateOf("")}
    var txtcorreo by remember{mutableStateOf("")}
    var txtmontop by remember{mutableStateOf("")}
    //val asistentes = remember { mutableListOf<Asistente>()}

    if (nombre.compareTo("")!=0)
        txtnombre=nombre
    if (finscripcion.compareTo("")!=0)
        txtfincripcion=finscripcion
    if (tsangre.compareTo("")!=0)
        txttsangre=tsangre
    if (telefono.compareTo("")!=0)
        txttelefono=telefono
    if (correo.compareTo("")!=0)
        txtcorreo=correo
    if (montop.compareTo("")!=0)
        txtmontop=montop

    nombre=""
    finscripcion = ""
    tsangre = ""
    telefono = ""
    correo = ""
    montop = ""

    //var asistentes by remember{ mutableStateListOf<Asistente()}
    val contextForToast = LocalContext.current.applicationContext
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment =  Alignment.CenterHorizontally){
        OutlinedTextField(
            value = txtnombre,//txtnombre
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = {Text("Nombre")},
            onValueChange = {txtnombre = it}
        )

        OutlinedTextField(
            value = txtfincripcion,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = {Text("Fecha de Inscripción")},
            onValueChange = {txtfincripcion = it}
        )

        OutlinedTextField(
            value = txttsangre,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = {Text("Tipo de Sangre")},
            onValueChange = {txttsangre = it}
        )

        OutlinedTextField(
            value = txttelefono,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {Text("Nro. Teléfono")},
            onValueChange = {
                if(it.length<10)
                    txttelefono = it
            }
        )

        OutlinedTextField(
            value = txtcorreo,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = {Text("Correo")},
            onValueChange = {txtcorreo = it}
        )

        OutlinedTextField(
            value = txtmontop,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {Text("Monto Pagado")},
            onValueChange = {txtmontop = it}
        )

        Row{
            Button(
                onClick = {
                    var asistente = Asistente(txtnombre,txtfincripcion,txttsangre,txttelefono,txtcorreo,txtmontop)
                    if(encuentraRegistro(asistentes,asistente)!=-1)
                        Toast.makeText(contextForToast, "Ya existe una persona con el mismo nombre! "+encuentraRegistro(asistentes,asistente), Toast.LENGTH_SHORT).show()
                    else
                        asistentes.add(asistente)

                    //Se reinician las variables
                    txtnombre=""
                    txtfincripcion=""
                    txttsangre=""
                    txttelefono=""
                    txtcorreo=""
                    txtmontop=""
                }
            ) {
                Text("Registrar Asistente")
            }
            Button(
                    onClick = {
                        var asistente = Asistente(txtnombre,txtfincripcion,txttsangre,txttelefono,txtcorreo,txtmontop)
                        if (indiceModificacion==-1)
                            Toast.makeText(contextForToast, "No existe el registro indicado "+encuentraRegistro(asistentes,asistente), Toast.LENGTH_SHORT).show()
                        else
                            actualizarRegistro(asistentes,asistente,indiceModificacion)

                        //Se reinician las variables
                        txtnombre=""
                        txtfincripcion=""
                        txttsangre=""
                        txttelefono=""
                        txtcorreo=""
                        txtmontop=""
                        indiceModificacion=-1
                    }
                    ) {
                Text("Actualizar Asistente")
            }

        }
        listarAsistentes(asistentes = asistentes)
    }
}

fun encuentraRegistro(asistentes:ArrayList<Asistente>,asistente: Asistente): Int{
    for(tempAsistente in asistentes){
        if ((tempAsistente.nombre).compareTo(asistente.nombre) == 0)
            return asistentes.indexOf(tempAsistente)
    }
    return -1
}

fun eliminarRegistro(asistentes:ArrayList<Asistente>,asistente: Asistente): Boolean{
    asistentes.remove(asistente)
    return true
}

fun actualizarRegistro(asistentes:ArrayList<Asistente>,asistente:Asistente,indice: Int): Boolean{
    var temporal=asistentes.get(indice)
    temporal.nombre=asistente.nombre
    temporal.finscripcion=  asistente.finscripcion
    temporal.tsangre = asistente.tsangre
    temporal.telefono = asistente.telefono
    temporal.correo = asistente.correo
    temporal.montop =asistente.montop


    return true

}