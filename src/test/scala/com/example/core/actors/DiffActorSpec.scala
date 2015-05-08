package com.civolution.core.actors

import java.util.UUID

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.civolution.core.{Core, CoreActors}
import com.civolution.db.MongoDb
import com.civolution.model.BinaryData
import org.specs2.mutable.SpecificationLike


class DiffActorSpec extends TestKit(ActorSystem()) with SpecificationLike with CoreActors with Core with ImplicitSender {
  import DiffActor._

  "DiffActor" should {

    "return equality check for " in {
      "equal data" in {
        DiffActor.diff("sameData".getBytes(), "sameData".getBytes) mustEqual `equal`
      }
      "different size" in {
        DiffActor.diff("one".getBytes(), "four".getBytes) mustEqual `size not equal`
      }
      "same size, but different content" in {
        DiffActor.diff("four".getBytes(), "five".getBytes) must contain("1")
      }
      "Nil input" in {
        DiffActor.diff(Nil, "five".getBytes)  mustEqual `size not equal`
      }
    }

    "handle Diff message" in {

      val binaryData = BinaryData(UUID.randomUUID(), "asdfafafasf")

      MongoDb.addLeft(binaryData)
      MongoDb.addRight(binaryData)

      diffActor ! Diff(binaryData.uuid)
      expectMsg(DiffResult(equal))
      success
    }

  }

}



