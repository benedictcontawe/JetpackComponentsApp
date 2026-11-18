package com.example.jetpackcomponentsapp.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

// Readable "is this a usable number?" checks for values that come from toIntOrNull(), toDoubleOrNull()
// and toLongOrNull() (they return null when the text isn't a valid number).
// The contract tells the compiler that a true result means the value is not null, so after
// `if (value.isValidNumber())` you can use `value` as a non-null Int / Double / Long.

@OptIn(ExperimentalContracts::class)
fun Int?.isValidNumber() : Boolean {
    contract { returns(true) implies (this@isValidNumber != null) }
    return this != null
}

@OptIn(ExperimentalContracts::class)
fun Double?.isValidNumber() : Boolean {
    contract { returns(true) implies (this@isValidNumber != null) }
    return this != null && this.isFinite() // also rejects NaN, Infinity and -Infinity
}

@OptIn(ExperimentalContracts::class)
fun Long?.isValidNumber() : Boolean {
    contract { returns(true) implies (this@isValidNumber != null) }
    return this != null
}
