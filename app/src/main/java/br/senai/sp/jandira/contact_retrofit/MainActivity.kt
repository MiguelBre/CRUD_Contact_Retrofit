package br.senai.sp.jandira.contact_retrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.contact_retrofit.api.ContactCall
import br.senai.sp.jandira.contact_retrofit.api.RetrofitApi
import br.senai.sp.jandira.contact_retrofit.model.Contact
import br.senai.sp.jandira.contact_retrofit.ui.theme.Contact_RetrofitTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Contact_RetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var nameState by remember {
        mutableStateOf("")
    }
    var emailState by remember {
        mutableStateOf("")
    }
    var phoneState by remember {
        mutableStateOf("")
    }
    var activeState by remember {
        mutableStateOf(false)
    }

    val retrofit = RetrofitApi.getRetrofit()
    val contactsCall = retrofit.create(ContactCall::class.java)
    val call = contactsCall.getAll()

    var contacts by remember {
        mutableStateOf(listOf<Contact>())
    }

    call.enqueue(object : Callback<List<Contact>> {
        override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
            contacts = response.body()!!
        }

        override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
            Log.i("ds3m", t.message.toString())

        }
    })



    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = nameState,
            onValueChange = {
                nameState = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Name")
            }
        )
        OutlinedTextField(
            value = emailState,
            onValueChange = {
                emailState = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Email")
            }
        )
        OutlinedTextField(
            value = phoneState,
            onValueChange = {
                phoneState = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Phone")
            }
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = activeState, onCheckedChange = { activeState = it })
            Text(text = "Enabled")
        }
        Button(onClick = {
            val contact = Contact(
                name = nameState,
                email = emailState,
                phone = phoneState,
                active = activeState
            )

            val callContactPost = contactsCall.save(contact)

            callContactPost.enqueue(object : Callback<Contact> {
                override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                    Log.i("ds3m", response.body()!!.toString())
                }

                override fun onFailure(call: Call<Contact>, t: Throwable) {
                    Log.i("ds3m", t.message.toString())
                }
            })

        }) {
            Text(text = "Salvar")
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(contacts) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable {
                            nameState = it.name
                            emailState = it.email
                            phoneState = it.phone
                            activeState = it.active
                                   },
                    backgroundColor = Color(0, 188, 212, 255)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = it.name)
                        Text(text = it.email)
                        Text(text = it.phone)
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
            }
    }


    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    Contact_RetrofitTheme {
        Greeting("Android")
    }
}