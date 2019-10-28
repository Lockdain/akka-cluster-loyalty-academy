package com.reactivebbq.loyalty

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import akka.util.Timeout
import org.scalatest.{BeforeAndAfterAll, Suite}

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

trait AkkaSpec extends BeforeAndAfterAll { this: Suite =>
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: Materializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher
  implicit val timeout: Timeout = Timeout(5.seconds)

  override protected def afterAll(): Unit = {
    super.afterAll()
    Await.result(system.terminate(), 5.seconds)
  }
}
