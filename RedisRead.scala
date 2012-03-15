import com.redis._
import akka.actor.Actor

import java.util.concurrent.CountDownLatch

object RedisRead { 

  def run(max: Int): Unit = {
    val latch = new CountDownLatch(max)
    val a = Actor.actorOf(new RedisRead(latch))
    a.start()
    for (i <- 1 to max) {
      val num = scala.util.Random.nextInt(max -1) + 1
      a ! "user" + num
    }
    latch.await()
    a.stop
  } 

}

class RedisRead(latch: CountDownLatch) extends Actor {
  val r = new RedisClientPool("localhost",6379)
  def receive = {
     case id => {
       r.withClient {client => {
         //userのscoreを取得
          try {
         val score = client.zscore("rank",id)
         val rank = client.zcount("rank",score.get+1,java.lang.Double.POSITIVE_INFINITY).get + 1
         //println(id + " : " + rank + " score:" + score)
         } catch {
            case e : Exception => {
              println(id)
              println(e.getStackTraceString)
            }
          } finally {
            latch.countDown()
          }
         }
       }
     }
  }
}
