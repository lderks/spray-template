package com.civolution.api

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.civolution.core.actors.DiffActor.{DiffResult, Diff}
import com.civolution.model.BinaryData
import play.api.libs.json.Json
import spray.http.{HttpEntity, StatusCodes, ContentTypes, HttpResponse}
import scala.concurrent.duration._
import spray.httpx.PlayJsonSupport
import spray.routing.Directives
import spray.httpx.marshalling.MetaMarshallers.futureMarshaller

import scala.concurrent.{Future, ExecutionContext}

class DiffService(persistActor: ActorRef, diffActor:ActorRef)(implicit executionContext: ExecutionContext) extends Directives with PlayJsonSupport {

  val `version` = "v1"
  val `diff` = "diff"
  val `left` = "left"
  val `right` = "right"

  implicit val timeout: Timeout = 3 seconds
  implicit val binaryDataReads = Json.reads[BinaryData]

  val route = {
    pathPrefix(`version` / `diff`) {
      path(`left`) {
       post {
          entity(as[BinaryData]) { binaryData =>
            persistActor ! Left(binaryData)
            complete("OK")
          }
       }
      } ~
        path(`right`) {
          post {
            entity(as[BinaryData]) { binaryData =>
              persistActor ! Right(binaryData)
              complete("OK")
            }
          }
        } ~
        path(JavaUUID) {
          uuid =>
            complete {
              diffActor ? Diff(uuid) map {
                case DiffResult(diffResult) => {
                  HttpResponse(StatusCodes.OK, HttpEntity(ContentTypes.`text/plain`, diffResult))
                }
                case _ => HttpResponse(StatusCodes.BadRequest)
              }
            }
        }
    }

  }

}
