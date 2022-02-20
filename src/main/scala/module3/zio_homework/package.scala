package module3

import zio.{Has, Task, ULayer, ZIO, ZLayer}
import zio.clock.{Clock, sleep}
import zio.console._
import zio.duration.durationInt
import zio.macros.accessible
import zio.random._

import java.io.IOException
import java.util.concurrent.TimeUnit
import scala.io.StdIn
import scala.language.postfixOps

package object zio_homework {

  /** 1. Используя сервисы Random и Console, напишите консольную ZIO программу
    * которая будет предлагать пользователю угадать число от 1 до 3 и печатать в
    * когнсоль угадал или нет. Подумайте, на какие наиболее простые эффекты ее
    * можно декомпозировать.
    */

  def result(genNumber: Int, enterNumber: Int): ZIO[Console, Throwable, Unit] =
    ZIO.effect(
      if (genNumber == enterNumber) putStr("Congrats! you are winner!")
      else
        putStr(s"You are wrong, righ number is $genNumber. Please, try again")
    )

  lazy val readInt: ZIO[Console, Throwable, Int] =
    getStrLn.flatMap(str => ZIO.effect(str.toInt))

  lazy val readIntOrRetry: ZIO[Console, Throwable, Int] = readInt.orElse(putStrLn("Incorrect input! Only Numbers allowed. Try again.") *> readIntOrRetry)

  lazy val guessProgram = for {
    _ <- putStr("Hello! Lets Play game! Guess number between one and three and put it into consle.")
    num <- nextIntBetween(1, 3)
    guessedNumber <- readIntOrRetry
    _ <- putStr(s"$guessedNumber")
    _ <- ZIO.ifM(ZIO.effect(num == guessedNumber))(
      putStrLn(s"You guessed! My number was $num."),
      putStrLn(s"You failed! My number was $num.")
    )
  } yield ()

  /** 2. реализовать функцию doWhile (общего назначения), которая будет
    * выполнять эффект до тех пор, пока его значение в условии не даст true
    */

  def doWhile(condition: Task[Boolean]): ZIO[Any, Throwable, Unit] =
    ZIO.ifM(condition)(ZIO.succeed(()), ZIO.succeed(()) *> doWhile(condition))

  /** 3. Реализовать метод, который безопасно прочитает конфиг из файла, а в
    * случае ошибки вернет дефолтный конфиг и выведет его в консоль Используйте
    * эффект "load" из пакета config
    */

  import module3.zio_homework.config._
  def loadConfigOrDefault = for {
    cfg <- load.orElse(ZIO.effect(AppConfig))
    _ <- putStrLn(cfg.toString())
  } yield ()

  /** 4. Следуйте инструкциям ниже для написания 2-х ZIO программ, обратите
    * внимание на сигнатуры эффектов, которые будут у вас получаться, на
    * изменение этих сигнатур
    */

  /** 4.1 Создайте эффект, который будет возвращать случайеым образом выбранное
    * число от 0 до 10 спустя 1 секунду Используйте сервис zio Random
    */
  lazy val eff = ZIO.sleep(1.seconds) *> nextIntBetween(0, 10)

  /** 4.2 Создайте коллукцию из 10 выше описанных эффектов (eff)
    */

  lazy val effects = List.fill(10)(eff)

  /** 4.3 Напишите программу которая вычислит сумму элементов коллекци
    * "effects", напечатает ее в консоль и вернет результат, а также залогирует
    * затраченное время на выполнение, можно использовать ф-цию
    * printEffectRunningTime, которую мы разработали на занятиях
    */
import module3.zioConcurrency._
  lazy val app = for {
    sum <- printEffectRunningTime(ZIO.collectAll(effects).map(l => l.sum))
    _ <- putStrLn(sum.toString())
  } yield sum

  /** 4.4 Усовершенствуйте программу 4.3 так, чтобы минимизировать время ее
    * выполнения
    */

  lazy val appSpeedUp = for {
    sum <- printEffectRunningTime(ZIO.collectAllPar(effects).map(l => l.sum))
    _ <- putStrLn(sum.toString())
  } yield sum

  /** 5. Оформите ф-цию printEffectRunningTime разработанную на занятиях в
    * отдельный сервис, так чтобы ее молжно было использовать аналогично
    * zio.console.putStrLn например
    */

  /** 6. Воспользуйтесь написанным сервисом, чтобы созадть эффект, который будет
    * логировать время выполнения прогаммы из пункта 4.3
    */

  lazy val appWithTimeLogg = ???

  /** Подготовьте его к запуску и затем запустите воспользовавшись
    * ZioHomeWorkApp
    */

  lazy val runApp = ???

}
