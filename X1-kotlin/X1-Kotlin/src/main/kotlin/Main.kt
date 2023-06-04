import epn.mov.bakery.model.Bakery
import src.epn.mov.bakery.model.Bread
import java.time.Instant
import java.util.*

class Main {
    fun main(){
        var bakery: Bakery = Bakery("Name");
        var bread:Bread = Bread("Croissant",0.3, Date.from(Instant.now()),false,100);
    }
}