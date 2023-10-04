package com.jobgun.jobetl.config

import io.weaviate.client.Config

object WeaviateConfig {
    val config = 
        new Config("http", "jobgun-jquq82xe.weaviate.network")
}