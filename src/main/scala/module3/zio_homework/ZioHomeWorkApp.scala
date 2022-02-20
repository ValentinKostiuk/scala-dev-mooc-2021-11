package module3.zio_homework
import zio.clock.Clock
import zio.console._
import zio.random.Random
import zio.{ExitCode, URIO, ZIO}
import java.util.Calendar

object ZioHomeWorkApp extends zio.App {
  override def run(args: List[String]): URIO[Clock with Random with Console, ExitCode] = {
    def result: ZIO[Clock with Random with Console, Throwable, Unit] = for {
      _ <- guessProgram
      _ <- doWhile(ZIO.succeed({
        val now = Calendar.getInstance()
        val curMill = now.get(Calendar.MILLISECOND)
        println(s"$curMill")
        curMill % 2 == 0
      }))
      _ <- loadConfigOrDefault
      _ <- app
      _ <- appSpeedUp
      _ <- putStrLn("All done")
      _ <- getStrLn
    } yield()

    result.exitCode
  }
} 
