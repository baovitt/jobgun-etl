package com.jobgun.jobetl.services

import com.leobenkel.zparkio.Services.CommandLineArguments
import com.leobenkel.zparkio.Services.CommandLineArguments.CommandLineArguments
import zio.ZIO

case class Arguments(input: List[String])
    extends CommandLineArguments.Service[Arguments] {
    // check validity of arguments
    override def checkValidity(): ZIO[Any, Throwable, Arguments] = ZIO.attempt(this)

    // display arguments status
    override final lazy val commandsDebug: Seq[String] = Seq.empty
}

object Arguments {
  def apply[A](f: Arguments => A): ZIO[CommandLineArguments[Arguments], Throwable, A] =
    CommandLineArguments.get[Arguments].apply(f)
}
