import com.shahid.TFLBuses
import kotlinx.coroutines.runBlocking

fun main() {
    val tflBuses = TFLBuses(System.getenv("APIKEY"))

    runBlocking {
        val arrivals = tflBuses.getArrivals(58)
        println(arrivals)
    }
}