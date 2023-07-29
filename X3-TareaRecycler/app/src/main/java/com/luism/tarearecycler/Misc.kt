package com.luism.tarearecycler

import android.text.format.DateUtils
import java.math.BigInteger
import java.security.MessageDigest

class Misc {
    companion object {
        fun secondsToDurationString(length:Long):String = DateUtils.formatElapsedTime(length)
        fun countToHumanReadable(count:Long):String{
            if(count<1_000) return "$count"

            var humanReadableCipher:Long = count
            arrayOf("K","M","B","T").forEach {
                unit->
                humanReadableCipher /= 100
                if(humanReadableCipher<100) return "${humanReadableCipher.toDouble()/10}$unit"
                humanReadableCipher /= 10
                if(humanReadableCipher<1000) return "$humanReadableCipher$unit"
            }

            return "${humanReadableCipher}Q"
        }

        fun secondsToReadableTime(seconds:Long):String{

            val unitsValues = arrayOf<Pair<String,Long>>(
                Pair("year",31_557_600),
                Pair("month",2_629_800),
                Pair("week",604_800),
                Pair("day",86_400),
                Pair("hour",3_600),
                Pair("minute",60),
                Pair("second",1)
            )

            print("$seconds ago")

            unitsValues.forEach {
                (unitName:String,factor:Long)->
                if(seconds>=factor){
                    val units:Long = seconds/factor
                    if(units>1) return "$units ${unitName}s"
                    return "$units $unitName"
                }
            }
            return "";
        }

        fun md5Hex(value:String):String{
            val digester = MessageDigest.getInstance("MD5")
            val hashValue = BigInteger(1,digester.digest(value.toByteArray())).toString(16)
            return hashValue.padStart(32,'0')
        }
    }
}