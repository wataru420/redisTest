import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import akka.actor.Actor

import java.util.concurrent.CountDownLatch

object MysqlRead { 

  def run(max: Int): Unit = {
    val latch = new CountDownLatch(max)
    val a = Actor.actorOf(new MysqlRead(latch))
    a.start()
    for (i <- 1 to 10000) {
      val num = scala.util.Random.nextInt(max -1) + 1
      a ! "user" + num
    }
    latch.await()
    a.stop
  } 
}

class MysqlRead(latch: CountDownLatch) extends Actor {
  Class.forName("com.mysql.jdbc.Driver").newInstance
  val db = DriverManager.getConnection("jdbc:mysql://localhost/sorttest","root","")

  def receive = {
        case id : String => {
          val sql = "SELECT count(id)+1 FROM ranking WHERE point < (SELECT point FROM ranking WHERE id=?)"
          val st = db.prepareStatement(sql)
          try {
            st.setString(1,id)
            st.executeQuery
          } catch {
            case e : Exception => {
              println(st.toString)
              println(e.getStackTraceString)
            }
          } finally {
            st.close
            latch.countDown()
          }
        }
     }
}
