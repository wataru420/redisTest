import scala.actors.Exit
import akka.actor.Actor

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

import java.util.concurrent.CountDownLatch

case class Rank(id: String,point: Int)

object MysqlWrite { 

  def run(max: Int): Unit = {
    val latch = new CountDownLatch(max)
      val a = Actor.actorOf(new MysqlWrite(latch))
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

class MysqlWrite(latch: CountDownLatch) extends Actor {
  Class.forName("com.mysql.jdbc.Driver").newInstance
  val db = DriverManager.getConnection("jdbc:mysql://localhost/sorttest","root","")

    def receive = {
    case Rank(id, point) => {
      val sql = "INSERT INTO ranking (id,point) VALUES (?,?)"
      val st = db.prepareStatement(sql)
        try {
        st.setString(1,id)
        st.setInt(2,point)
        st.executeUpdate
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
    case "Exit" => {
      db.close
      self.exit
    }
  }
}
