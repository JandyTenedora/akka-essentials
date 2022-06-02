package part2actors

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object ActorsIntro {

  //part 1 behavior
  //behaviour
  val simpleActorBehavior: Behavior[String] = Behaviors.receiveMessage{
    (message: String) =>
      println(s"[simple actor] I have received $message")

      //new behavior for the NEXT message
      Behaviors.same
      //will keep same logic for next message
  }

  def demoSimpleActor = {
    //part 2, instantiate it, we did that as root of actor system
    val actorSystem = ActorSystem(SimpleActor_V2(), "FirstActorSystem")// root of entire hierarchy of actors

    //part 3: communicate

    actorSystem ! "I am learning Akka" //asynchronously send a message
    // ! = the "tell" method

    //part 4: gracefully shut down
    Thread.sleep(1000)
    actorSystem.terminate()
  }

  // "refactor"
  object SimpleActor{
    def apply(): Behavior[String] =
      Behaviors.receiveMessage{
      (message: String) =>
        println(s"[simple actor] I have received $message")
        //new behavior for the NEXT message
        Behaviors.same
      //will keep same logic for next message
    }
  }

  object SimpleActor_V2 {
    def apply(): Behavior[String] = Behaviors.receive{(context, message) =>
      //context is a data structure ActorContext with a variety of APIs
      context.log.info(s"[simple actor] I have received $message")
      Behaviors.same
    }

  }

  def main(args: Array[String]) = {
    demoSimpleActor

  }

}
