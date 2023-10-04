package com.jobgun.jobetl

import com.leobenkel.zparkio.Services._
import com.leobenkel.zparkio.Services.Logger.Logger
import com.leobenkel.zparkio.ZparkioApp
import com.jobgun.jobetl.Application._
import com.jobgun.jobetl.services._
import com.jobgun.jobetl.domain._
import com.jobgun.jobetl.config._
import com.leobenkel.zparkio.config.scallop.CommandLineArgumentScallop
import izumi.reflect.Tag
import zio.{ZIO, ZLayer, Chunk}
import zio.json._
import zio.openai._
import Application.APP_ENV

trait Application extends ZparkioApp[Arguments, APP_ENV, Unit] {
  implicit lazy final override val tagC:   zio.Tag[Arguments] = zio.Tag(Tag.tagFromTagMacro)
  implicit lazy final override val tagEnv: zio.Tag[APP_ENV]   = zio.Tag(Tag.tagFromTagMacro)

  override protected def env: ZLayer[ZPARKIO_ENV, Throwable, APP_ENV] = 
    CompletionsParserService.default ++ EmbeddingService.default ++ WeaviateClientService.default

  override protected def sparkFactory:  FACTORY_SPARK = SparkBuilder
  override protected def loggerFactory: FACTORY_LOG = Logger.Factory(Log)
  override protected def makeCli(args: List[String]): Arguments = Arguments(args)
  lazy final override protected val cliFactory:            FACTORY_CLI   = CommandLineArgumentScallop.Factory()
  lazy final override protected val makeConfigErrorParser: ERROR_HANDLER =
    CommandLineArgumentScallop.ErrorParser

  override def runApp(): ZIO[COMPLETE_ENV, Throwable, Unit] = {
    for {
      listings <- ZIO
        .readFile("samples.json")
        .map(_.fromJson[Chunk[JobListing]] getOrElse Chunk.empty)
        .map(_.init.take(2))
      parsedListings <- CompletionsParserService.parseListings(listings)
      embeddedListings <- 
        EmbeddingService.embedTexts(parsedListings.map(_._2.toJson): _*)
          .map(_.zipWithIndex.map { case (e, i) => (parsedListings(i)._1, e) })
      vals <- ZIO.foreach(embeddedListings) { case (listing, embedding) => 
        WeaviateClientService.addVector(embedding, listing)
      }
      _ <- zio.Console.printLine(vals)
    } yield ()
  }.mapError(e => new Exception(e.toString))
}

object Application {
  // Define the environment and the output types
  type APP_ENV = CompletionsParserService with EmbeddingService with WeaviateClientService
}
