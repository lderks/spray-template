package com.civolution.api

import com.civolution.core.{Core, CoreActors}
import spray.routing.RouteConcatenation

trait Api extends RouteConcatenation {
  this: CoreActors with Core =>

  private implicit val _ = system.dispatcher

  val routes = new DiffService(persistActor, diffActor).route

 }
