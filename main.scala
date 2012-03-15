object Main { 
  
  def main(args: Array[String]): Unit = {
    val max = 10000
    require(0 < args.length, "select job!!")
    for (a <- args) {
      val start = System.currentTimeMillis
      println(a + " start")
      a match {
        case "MysqlWrite" => MysqlWrite.run(max)
        case "RedisWrite" => RedisWrite.run(max)
        case "RedisLoadFile" => RedisLoadFile.run
        case "MysqlRead" => MysqlRead.run(max)
        case "RedisRead" => RedisRead.run(max)
        case x => println(x + " is unknown job")
      }
      println(a + " " + max + " records. takes:" + (System.currentTimeMillis - start).toString + "ms")
    }
  } 

}
