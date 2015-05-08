package com.civolution.core.actors

import java.util.UUID

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.civolution.core.{Core, CoreActors}
import com.civolution.model.BinaryData
import org.specs2.mutable.SpecificationLike


class PersistActorSpec extends TestKit(ActorSystem()) with SpecificationLike with CoreActors with Core with ImplicitSender {
  import PersistActor._

  sequential

  "PersistActor should" >> {

    "persists Left data" in {
      persistActor ! Left(BinaryData(UUID.randomUUID(), "asdfafafasf"))
      expectMsg(Persisted)
      success
    }

    "persists Right data" in {
      persistActor ! Right(BinaryData(UUID.randomUUID(), "asdfafafasf"))
      expectMsg(Persisted)
      success
    }
  }

}
