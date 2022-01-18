package com.hometech.testref.helper

import io.kotlintest.matchers.shouldBeInRange
import io.kotlintest.shouldBe
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import kotlin.math.exp
import kotlin.random.Random
import kotlin.streams.asSequence

fun randomString(size: Int = 20, lowerCase: Boolean = false): String {
    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val str = java.util.Random().ints(size.toLong(), 0, source.length)
        .asSequence()
        .map(source::get)
        .joinToString("")
    return if (lowerCase) str.lowercase() else str
}

fun randomInt(from: Int = Int.MIN_VALUE, to: Int = Int.MAX_VALUE): Int = Random.nextInt(from, to)
fun randomLong(from: Long = Long.MIN_VALUE, to: Long = Long.MAX_VALUE): Long = Random.nextLong(from, to)

fun randomBigDecimal(
    from: BigDecimal = BigDecimal(-100000000),
    to: BigDecimal = BigDecimal(100000000)
) = randomInt(from = from.toInt(), to = to.toInt()).toBigDecimal()

infix fun LocalDateTime?.shouldBeIgnoreMillis(expected: LocalDateTime?) {
    if (this === null && this === expected) return
    val originalSeconds = this?.truncatedTo(ChronoUnit.SECONDS)?.toEpochSecond(ZoneOffset.UTC) ?: 0L
    val expectedSeconds = expected?.truncatedTo(ChronoUnit.SECONDS)?.toEpochSecond(ZoneOffset.UTC) ?: 0L
    val range = LongRange(start = originalSeconds - INNACURACY_SECONDS, endInclusive = expectedSeconds + INNACURACY_SECONDS)
    return expectedSeconds shouldBeInRange range
}

infix fun BigDecimal.shouldBeEqualsComparing(expected: BigDecimal?) {
    return this.compareTo(expected) shouldBe 0
}

private const val INNACURACY_SECONDS = 1
