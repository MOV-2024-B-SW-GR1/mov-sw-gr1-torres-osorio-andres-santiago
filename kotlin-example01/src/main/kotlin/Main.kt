package ec.epn.edu

import java.util.*

fun main() {
    println("Hello World!")
    val inmutable:String = "Adrian";
    var mutable: String = "Vicente";
    mutable = "Adrian"

    //Duck typing
    val ejemploVariable = "Adrian Eguez"
    ejemploVariable.trim()


    val edadEjemplo: Int = 12
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    val fechaNacimiento: Date = Date()


    //when switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    //  if - else
    val esSoltero = (estadoCivilWhen == "S")
    val coqueto = if (esSoltero) "Si" else "No"

    imprimirNombre("AdRiaAnNa VicenTeTEet")
    calcularSueldo(10.00) //solo parametro requerido
    calcularSueldo(10.00, 15.00, 20.00) //parametro requerido y sobreescribir parametros opcionales
    //Named parameters
    calcularSueldo(10.00, bonoEspecial = 20.00) // usando el parametro bonoEspecial en la segunda posicion
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)
    // usando el parametro bonoEspecial en primera posicion
    // usando el parametro sueldo en la segunda posicion
    // usando el parametro tasa es tercera posicion
    // gracias a los parametros nombrados

   //Clases de uso
    val sumaA = Suma(1,1)
    val sumaB = Suma(null,1)
    val sumaC = Suma(1,null)
    val sumaD = Suma(null,null)

    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()

    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)


//ARREGLOS

    //Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico);

    // Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )

    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)


    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("valorActual: $valorActual");
        }

    arregloDinamico.forEach { println( "Valor Actual (it): ${it}")}
    // Map -> Muta (Modifica cambio) el arreglo
    // 1. enviamos el nuevo valor a la iteracion
    // 2. nos devuelve un nuevo Arreglo con valores
    // de las iteracionnes
    val respuestaMap:List<Double> = arregloDinamico
        .map {valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println("Map 1 " + respuestaMap)

    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println("Map 2: " + respuestaMapDos)



    // Filter -> filtara el arreglo
    // 1. Devolver una expresion true o false
    // 2. nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            //expresion o condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter{ it <= 5}
    println("FILTER: "+respuestaFilter)
    println(respuestaFilterDos)

    // OP AND
    // AND → &&? (¿MISMO NOMBRE?)

    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5)
        }

    println("ANY: " + respuestaAny)

    // AND → ALL (TODOS JUNTOS?)
    println(respuestaAny) // true
    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println("ALL: "+respuestaAll)

    // REDUCE → Valor acumulado
    // Valor acumulado = 0 (Siempre empiezo en 0 en Kotlin)
    // [1,2,3,4,5] → Acumular "SUMAR" estos valores del arreglo
    // valorIteracion1 = valorEmpieza  + 1 = 0 + 1 = 1 → Iteracion1
    // valorIteracion2 = valorAcumuladoIteracion1 + 2 = 1 + 2 = 3 → Iteracion2
    // valorIteracion3 = valorAcumuladoIteracion2 + 3 = 3 + 3 = 6 → Iteracion3
    // valorIteracion4 = valorAcumuladoIteracion3 + 4 = 6 + 4 = 10 → Iteracion4
    // valorIteracion5 = valorAcumuladoIteracion4 + 5 = 10 + 5 = 15 → Iteracion4
    val respuestaReduce: Int = arregloDinamico
        .reduce{ acumulado:Int, valorActual:Int ->
            return@reduce (acumulado + valorActual) // → Cambiar o usar la logica de negocio
        }
    println(respuestaReduce);
    // return@reduce acumulado + (itemCarrito.cantidad * itemCarrito.precio)

}
fun imprimirNombre(nombre:String):Unit{
    fun otraFuncionAdentro(){
        print("Otra funcion adentro")
    }
    println("Nombre: $nombre")
    println("Nombre: ${nombre}")
    println("Nombre: ${nombre + nombre}")
    println("Nombre: ${nombre.uppercase()}")
    println("Nombre: $nombre.uppercase()")

    otraFuncionAdentro()
}
fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null //Opcional (nullable)
    // Variable? - "?" Es Nullable (quiere decir que algun momento puede se nulo)
): Double{
    // Into -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    return if(bonoEspecial == null){
        sueldo * (100/tasa)
    }else{
        sueldo * (100/tasa) * bonoEspecial
    }
}

//CLASES
abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno:Int,
        dos:Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
    }
}

//-------Kotlin Classes
//Clase Padre
abstract class Numeros( //Constructor Primario
    //Caso 1) Parametro normal
    //uno:Int, (parametro (sin modificador acceso))
    //Caso 2) Parámetro y propiedad (atributo) (protected)
    // private var uno: Int (propiedad "instancia.uno")
    protected val numeroUno: Int,
    protected val numeroDos: Int,
    parámetroNoUsadoNoPropiedadDeLaClase: Int? = null
){
    init {
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}


class Suma( //Constructor Primario
    unoParametro: Int,
    dosParametro: Int,
): Numeros( //Clase padre, Numeros (extendiendo)     ---> Pasamos los atributos de Suma al padre Números
    unoParametro,
    dosParametro
){
    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicito: String = "Publico implicito"
    init { //Bloque constructor primario
        this.numeroUno //Heredamos del Padre
        this.numeroDos
        numeroUno //this. OPCIONAL (propiedades, metodos)
        numeroDos //this. OPCIONAL (propiedades, metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito
    }
    constructor(
        uno: Int?, //Entero nullable
        dos: Int,
    ):this(
        if(uno == null) 0 else uno,
        dos
    ){
        //OPCIONAL
        //Bloque de código de constructor secundario
    }
    constructor(
        uno: Int,
        dos: Int?, //Entero nullable
    ):this(
        uno,
        if(dos==null) 0 else dos,
    )
    constructor(
        uno: Int?,//Entero nullable
        dos: Int?,//Entero nullable
    ):this(
        if(uno==null) 0 else uno,
        if(dos==null) 0 else dos
    )

    fun sumar():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object{ //Comparte entre todas las instancias, similar al STATIC
        //funciones, variables
        //NombreClase.metodo, NombreClase.funcion =>
        //Suma.pi
        val pi = 3.14
        //Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num:Int):Int{ return num*num}
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}