package com.model


import java.util.UUID

import com.civolution.model.BinaryData
import org.specs2.mutable.Specification
import BinaryData._


class BinaryDataSpec extends Specification {

  val base64EncodedData = "dGhpcyByZXByZXNlbnRzIGJpbmFyeSBkYXRhIHdoaWNoIGlzIGJhc2U2NCBlbmNvZGVk"
  val base64DecodedData:Seq[Byte] = "this represents binary data which is base64 encoded".getBytes()

  "BinaryData" should {
    "decode base64 encoded data" in {
      val binaryData = new BinaryData(UUID.randomUUID(), base64EncodedData)
      binaryData.decodedData mustEqual base64DecodedData
    }
  }

}
