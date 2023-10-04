package com.jobgun.jobetl.services

import com.leobenkel.zparkio.Services.SparkModule

object SparkBuilder extends SparkModule.Factory[Arguments] {
  lazy final override protected val appName: String = "job-etl"
  // lazy final override protected val master: String = "local[*]"
}
