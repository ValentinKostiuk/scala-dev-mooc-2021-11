package module3.zio_homework
import zio.clock.Clock
import zio.console._
import zio.random.Random
import zio.{ExitCode, URIO, ZIO}
import java.util.Calendar
import zio.ZEnv

object ZioHomeWorkApp extends zio.App {
  def run(args: List[String]): URIO[ZEnv,ExitCode] = {
  def result: ZIO[ZEnv, Throwable, Unit] = for {
      _ <- guessProgram
      _ <- putStrLn("Guess game finished")
      _ <- doWhile(ZIO.succeed({
        val now = Calendar.getInstance()
        val curMill = now.get(Calendar.MILLISECOND)
        println(s"$curMill")
        curMill % 2 == 0
      }))
      _ <- putStrLn("DoWhile finished")
      _ <- loadConfigOrDefault
      _ <- putStrLn("Load Config finished")
      _ <- app
      _ <- putStrLn("Not parallelized finished")
      _ <- runApp
      _ <- putStrLn("Parallelized finished")
      _ <- putStrLn("All done")
      _ <- getStrLn
    } yield()

    result.exitCode
  }
} 
