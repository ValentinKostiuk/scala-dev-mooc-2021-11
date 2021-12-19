package module1
import scala.util.control.Breaks._
import module1.list._



object App {

  def main(args: Array[String]): Unit = {

    def doomyFunc(a: String) = {
      Thread.sleep(1000)
      println(a)
    }

    val doomyFuncWithLoggingTime: String => Unit = hof.logRunningTime(doomyFunc)
      
  }


}








