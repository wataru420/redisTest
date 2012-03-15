import com.redis._
import akka.actor.Actor

import java.util.concurrent.CountDownLatch

object RedisWrite { 

  def run(max: Int): Unit = {
    val latch = new CountDownLatch(max)
    val a = Actor.actorOf(new RedisWrite(latch))
    a.start()
    for (i <- 1 to max) {
      val point = scala.util.Random.nextInt(max)
      a ! Rank("user" + i,point)
    }
    latch.await()
    val res = a ! "Exit"
    a.stop
  } 
}


class RedisWrite(latch: CountDownLatch) extends Actor {
  val r = new RedisClientPool("localhost",6379)
  def receive = {
     case Rank(id, point) => {
       r.withClient {client => {
         client.zadd("rank",point.toDouble,id)
         }
       }
       latch.countDown()
     }
     case "Exit" => {
       self.exit
     }
  }
}
