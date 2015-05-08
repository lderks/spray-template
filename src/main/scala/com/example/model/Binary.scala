package com.civolution.model

import java.util.UUID

import org.parboiled.common.Base64

case class BinaryData(uuid:UUID, encodedData:String) {
  val decodedData : Seq[Byte] = Base64.rfc2045().decode(encodedData)
}

object BinaryData {

  val `equal` = "equal"

  val `size not equal` = "size not equal"

  val `same size` = "same size"


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