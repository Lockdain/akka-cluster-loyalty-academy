package com.reactivebbq.loyalty

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings}
import akka.management.AkkaManagement
import org.slf4j.LoggerFactory

object Main extends App {
  val log = LoggerFactory.getLogger(this.getClass)

  val Opt = """-D(\S+)=(\S+)""".r
  args.toList.foreach {
    case Opt(key, value) =>
      log.info(s"Config Override: $key = $value")
      System.setProperty(key, value)
  }

  implicit val system: ActorSystem = ActorSystem("Loyalty")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  AkkaManagement(system).start()

  val rootPath = Paths.get("tmp")
  val loyaltyRepository: LoyaltyRepository = new FileBasedLoyaltyRepository(rootPath)(system.dispatcher)

//  val loyaltyActorSupervisor = system.actorOf(LoyaltyActorSupervisor.props(loyaltyRepository))

    val loyaltyActorSupervisor = ClusterSharding(system).start(
      "loyalty",
      LoyaltyActor.props(loyaltyRepository),
      ClusterShardingSettings(system),
      LoyaltyActorSupervisor.idExtractor,
      LoyaltyActorSupervisor.shardIdExtractor
    )

  val loyaltyRoutes = new LoyaltyRoutes(loyaltyActorSupervisor)(system.dispatcher)

  Http().bindAndHandle(loyaltyRoutes.routes, "localhost")
}
