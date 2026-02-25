package com.example.viewmodel_estudio1.data

class PersonaRepositoryImp: PersonaRepository {
    override fun obtenerPersona(): Persona {
        return Persona(
            nombre = "Juan Pérez",
            edad = 30,
            correo = "hector@test.com",
            telefono = "123456789")
    }

    override fun obtenerPersonas(): List<Persona> {
        return listOf(
            Persona(
                nombre = "Juan Pérez",
                edad = 30,
                correo = "juan@test.com",
                telefono = "123456789"),
            Persona(
                nombre = "Ana Gómez",
                edad = 25,
                correo = "ana@test.com",
                telefono = "987654321"))
    }
}