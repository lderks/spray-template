package com.civolution.db

import java.util.UUID
import scala.collection.mutable.Map

import com.civolution.model.BinaryData

object MongoDb {

  var lefts:Map[UUID, BinaryData] = Map[UUID,BinaryData]()
  var rights:Map[UUID, BinaryData] = Map[UUID,BinaryData]()


  def addLeft(data:BinaryData) =  lefts = lefts += data.uuid -> data

  def addRight(data:BinaryData) = rights = rights += data.uuid -> data

  def findBinaryDataFor(uuid: UUID) = {
    val left = lefts.get(uuid)
    val right = rights.get(uuid)
    (left -> right)
  }
}
