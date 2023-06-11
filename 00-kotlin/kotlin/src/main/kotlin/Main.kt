@file:JvmName("Main")
import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    // Not mutable
    val nonMutable: String = "Luis"
    // nonMutable = "LUIS";

    // Mutable
    var mutable: String = "Mateo"
    mutable = "MATEO";

    // Type Inference / Duck Typing
    var simpleString = "Bonus";
    var simpleDuck = "DUCKS!";
    simpleDuck = simpleDuck.lowercase();

    // Variables primitivas

    val nombre: String = "Luis M"
    val sueldo: Double = 123.45
    val estadoCivil: Char = 'S'
    val mayoriaEdad: Boolean = true

    // Objetos
    val fechaNac: Date = Date()

    // Switch/When
    when(estadoCivil){
        ('C')->{
            println("Casado")
        }
        ('S')->{
            println("Soltero")
        }
        else -> {

        }
    }

    val esCoqueto: String = if (estadoCivil.equals("S")) "Si" else "No";

    fun imprimirNombre(nombre: String){
        println("Nombre: ${nombre}")
    }

    fun calcularSueldo(
        sueldo: Double,
        tasa: Double = 0.12,
        bonoEspecial: Double ?= null
    ): Double{
        return (bonoEspecial ?: 0.0) + sueldo*tasa;
    }

    calcularSueldo(1000.0);
    calcularSueldo(1000.0,0.13);
    calcularSueldo(1000.0, bonoEspecial = 500.0);

    val sumaUno = Sum(1,1)
    val sumaDos = Sum(1,null)
    val sumaTre = Sum(null,1)
    val sumaCua = Sum(null,null)

    sumaUno.sum();
    sumaDos.sum();
    sumaTre.sum();
    sumaCua.sum();

    println(Sum.pi)
    println(Sum.square(4))
    println(Sum.resultHistory)

    // Arrays
    val staticArrays: Array<Int> = arrayOf<Int>(1,2,3)
    println(staticArrays)

    val dynamicArrays: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7)
    println(dynamicArrays)

    val unitResult = dynamicArrays.forEach{
        currentVal:Int ->
            println("Current value: "+currentVal)
    }
    println(unitResult)

    dynamicArrays.forEach{println(it)}

    staticArrays.forEachIndexed {
            index, i ->
        println("At index $index there is $i")
    }

    val mappedValues: List<Double> = dynamicArrays.map { it.toDouble() + 100.00 }
    println(mappedValues)

    val filteredValues1: List<Int> = dynamicArrays.filter { it>5 };
    val filteredValues2: List<Int> = dynamicArrays.filter {
        value -> return@filter value>5
    };

    println(filteredValues1)
    println(filteredValues2)

    // Aggregations?
    val anyResult: Boolean = dynamicArrays.any { it>5 }
    val allResult: Boolean = dynamicArrays.all { it>5 }
    println("Any:$anyResult , All:$allResult")

    val sumResult: Int = dynamicArrays.reduce { acc, i -> acc+i }
    println(sumResult)
}

abstract class NumerosJava{
    protected val numA:Int; // Se necesita val, o var para que sea un atributo
    private val numB:Int;

    constructor(
        a:Int,
        b:Int
    ){
        numA = a;
        numB = b;
        println("Initialized");
    }
}

abstract class NumerosKotlin(
    protected val numA:Int,
    protected val numB:Int
    )
    {
        init{
            this.numA; numA;
            this.numB; numB;
            println("Initialized");
    }
}

class Sum(
    a: Int, b: Int
) : NumerosKotlin(a,b){
    init {
        this.numA;
        this.numB;
    }

    constructor(a: Int?, b: Int):
            this((a?:0),b)

    constructor(a: Int, b: Int?):
            this(b,a)

    constructor(a: Int?, b: Int?):
            this(b,(a?:0))

    public fun sum(): Int{
        val result = numA+numB;
        appendToHistory(result)

        return  result;
    }

    companion object{ // Técnicamente un singleton
        val pi = Math.PI;
        val resultHistory = ArrayList<Int>();

        fun square(x: Int):Int{
            return x*x;
        }

        fun appendToHistory(latestResult: Int){
            resultHistory.add(latestResult)
        }
    }
}