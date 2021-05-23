package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}

operator fun Pair<Rational, Rational>.contains(half: Rational): Boolean {
    return (this.first <= half) && (half <= this.second)
}

operator fun Rational.rangeTo(max: Rational): Pair<Rational, Rational> {
    return Pair(this, max)
}


fun String.toRational(): Rational {
    val elements = this.split("/")

    val numerator = BigInteger(elements[0])
    val denominator = if (elements.size==2) BigInteger(elements[1]) else BigInteger.ONE

    return Rational(numerator, denominator)
}

operator fun Rational.unaryMinus(): Rational {
    return Rational(this.numerator.negate(), this.denominator)
}

operator fun Rational.plus(second: Rational): Rational {
    val d = this.denominator * second.denominator
    val n = (this.numerator*second.denominator) + (second.numerator*this.denominator)

    return Rational(n, d)
}

operator fun Rational.minus(second: Rational): Rational {
    val d = this.denominator * second.denominator
    val n = (this.numerator*second.denominator) - (second.numerator*this.denominator)

    return Rational(n, d)
}

operator fun Rational.times(second: Rational): Rational {
    val d = this.denominator * second.denominator
    val n = (this.numerator*second.numerator)

    return Rational(n, d)
}

operator fun Rational.div(second: Rational): Rational {
    val n = (this.numerator*second.denominator)
    val d = this.denominator * second.numerator

    return Rational(n, d)
}

operator fun Rational.compareTo(second: Rational): Int {
    return this.computeValue().compareTo(second.computeValue())
}

class Rational(val numerator:BigInteger, val denominator: BigInteger) {

    override fun toString(): String {
        val gcd = numerator.gcd(denominator)
        var n = numerator/gcd
        var d = denominator/gcd

        if (d== BigInteger.ONE)
            return "$n"

        if(d<BigInteger.ZERO) {
            d = d.abs()
            n = n.negate()
        }

        return "$n/$d"
    }

    override fun equals(other: Any?): Boolean {
        if (other==null)
            return false

        if (other is Rational) {
            val first = this.reduce()
            val second = other.reduce()
            return first.numerator == second.numerator && first.denominator == second.denominator
        }
        else
            return false
    }

    fun reduce(): Rational {
        val gcd = numerator.gcd(denominator)

        var n = numerator/gcd
        var d = denominator/gcd

        if(d<BigInteger.ZERO) {
            d = d.abs()
            n = n.negate()
        }

        return Rational(n, d)
    }

    fun computeValue(): Double {
        return numerator.toDouble()/denominator.toDouble()
    }
}

infix fun Number.divBy(denominator: Number): Rational {
    if (denominator == 0)
        throw IllegalArgumentException()

    return Rational(BigInteger(this.toString()), BigInteger(denominator.toString()))
}
