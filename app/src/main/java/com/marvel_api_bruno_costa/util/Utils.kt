package com.marvel_api_bruno_costa.util

import com.marvel_api_bruno_costa.model.Result
import java.math.BigInteger
import java.security.MessageDigest

object Utils {

    object BundleKey {
        const val CHARACTER_ID = "characterId"
    }

    object ApiKeys {
        private const val privateKey = "12e3d78c426250f1340efee98e8af2346b19ffc1"
        const val LIMIT = 20
        const val DEFAULT_OFFSET = 0
        const val TS = "9999999"
        const val PUBLIC_KEY = "f265907a419a6f1ade6d4fc045407307"
        val HASH = md5("$TS$privateKey$PUBLIC_KEY")
    }


    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun getMostExpensiveHq(result: List<Result>?): Result? {
        var auxPrice: Float = 0.0f
        var auxResult: Result? = null
        result?.forEach {
            it.prices.forEach { actualPrice ->
                if (actualPrice.price >= auxPrice) {
                    auxPrice = actualPrice.price
                    auxResult = it
                }
            }
        }
        return auxResult
    }
}