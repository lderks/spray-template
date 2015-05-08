package com.civolution.core.actors

import akka.actor.Actor
import com.civolution.model.BinaryData

object PersistActor {

  case class Left(data: BinaryData)
  case class Right(data: BinaryData)
  case object Persisted
  case object Unknown

}

class PersistActor extends Actor {
  import PersistActor._

  def receive: Receive = {
    case Left(data) => sender ! Persisted
    case Right(data) => sender ! Persisted
    case _ => sender ! Unknown
  }

}
