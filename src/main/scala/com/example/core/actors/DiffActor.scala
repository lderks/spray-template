package com.civolution.core.actors

import java.util.UUID

import akka.actor.Actor
import com.civolution.db.MongoDb
import com.civolution.model.BinaryData

object DiffActor {


  case class Diff(uuid: UUID)
  case class DiffResult(result:String)
  case object UnExpectedResult


  val `equal` = "equal"
  val `size not equal` = "size not equal"
  val `same size` = "same size"

  def diff(leftOption:Option[BinaryData],rightOption:Option[BinaryData]):String = {
    val left = leftOption match {
      case Some(data) => data.decodedData
      case None => Nil
    }
    val right = rightOption match {
      case Some(data) => data.decodedData
      case None => Nil
    }
    diff(left, right)
  }


  def diff(left:Seq[Byte], right:Seq[Byte]):String = {

    if(left == right) {
      `equal`
    }
    else if(left.length != right.length) {
      `size not equal`
    }
    else {
      val differences = for (i <- 0 to left.length-1 if left(i) != right(i) ) yield i
      s"difference found at ${differences.head}, total size: ${left.length}"
    }

  }

}

class DiffActor extends Actor {
  import DiffActor._

  def receive: Receive = {

    case Diff(uuid) => {
      val currentSender = sender
      val diffResult:String = MongoDb.findBinaryDataFor(uuid) match {
        case (left, right) => DiffActor.diff(left, right)
      }
      currentSender ! DiffResult(diffResult)
    }
    case _ => sender ! UnExpectedResult
  }

}