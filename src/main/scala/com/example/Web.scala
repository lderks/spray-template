package com.civolution

import akka.actor.Props
import akka.io.IO
import com.civolution.api.Api
import com.civolution.core.{CoreActors, BootedCore}

import spray.can.Http

import scala.util.Properties

/**
 * Provides the web server (spray-can) for the REST api in ``Api``, using the actor system
 * defined in ``Core``.
 *
 * You may sometimes wish to construct separate ``ActorSystem`` for the web server machinery.
 * However, for this simple application, we shall use the same ``ActorSystem`` for the
 * entire application.
 *
 * Benefits of separate ``ActorSystem`` include the ability to use completely different
 * configuration, especially when it comes to the threading model.
 */
object Web extends App with BootedCore with CoreActors with Api {

  val rootService = system.actorOf(Props(new RoutedHttpService(routes)))

  val port = Properties.envOrElse("PORT", "8080").toInt

  IO(Http)(system) ! Http.Bind(rootService, "0.0.0.0", port)

}