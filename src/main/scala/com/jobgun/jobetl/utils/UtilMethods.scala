package com.jobgun.jobetl

import zio.Chunk

object UtilMethods {
    def cosineSimilarity(a: Chunk[Double], b: Chunk[Double]): Double = {
        val dotProduct = a.zip(b).map { case (x, y) => x * y }.sum
        val aMagnitude = math.sqrt(a.map(x => x * x).sum)
        val bMagnitude = math.sqrt(b.map(x => x * x).sum)
        dotProduct / (aMagnitude * bMagnitude)
    }
}