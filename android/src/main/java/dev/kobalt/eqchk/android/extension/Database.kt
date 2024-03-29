package dev.kobalt.eqchk.android.extension

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction

fun <T> Database.transaction(transaction: Transaction.() -> T): T {
    return org.jetbrains.exposed.sql.transactions.transaction(this) { transaction(this) }
}