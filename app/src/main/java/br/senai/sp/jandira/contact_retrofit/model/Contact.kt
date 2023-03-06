package br.senai.sp.jandira.contact_retrofit.model

data class Contact(
    var id: Long = 0,
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var active: Boolean = true
) {
    override fun toString(): String {
        return "Contact(id=$id, name='$name', email='$email', phone='$phone', active=$active)"
    }
}

//  {
//      "id":1,
//      "name":"Pedro da silva",
//      "email":"pedro@terra.com.br",
//      "phone":"(11)96325-9854",
//      "active":true
//  }
