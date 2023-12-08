import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/main/kotlin/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
fun gcd(nums: List<Long>): Long = nums.reduce(::gcd)
fun lcm(a: Long, b: Long): Long = if (a == 0L || b == 0L) 0 else a * b / gcd(a, b)

fun lcm(nums: List<Long>): Long = nums.reduce(::lcm)