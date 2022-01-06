package futures

import HomeworksUtils.TaskSyntax

import scala.concurrent.{ExecutionContext, Future}
import scala.tools.nsc.interpreter.Results
import scala.util.Success
import scala.util.Failure
import scala.util.Try
import scala.util.control.NonFatal

object task_futures_sequence {

  /** В данном задании Вам предлагается реализовать функцию fullSequence,
    * похожую на Future.sequence, но в отличии от нее, возвращающую все успешные
    * и не успешные результаты. Возвращаемое тип функции - кортеж из двух
    * списков, в левом хранятся результаты успешных выполнений, в правово
    * результаты неуспешных выполнений. Не допускается использование методов
    * объекта Await и мутабельных переменных var
    */
  /** @param futures список асинхронных задач
    * @return асинхронную задачу с кортежом из двух списков
    */

  def fullSequence[A](futures: List[Future[A]])(implicit ex: ExecutionContext): Future[(List[A], List[Throwable])] = {
    futures.foldLeft[Future[(List[A], List[Throwable])]](Future.successful(List.empty[A], List.empty[Throwable])) { case (aggregator, future) =>
      aggregator.flatMap { case (sucesses, failures) =>
        future
          .map(value => ((sucesses.appended(value)), failures))
          .recoverWith { case ex =>
            Future.successful((sucesses, (failures.appended(ex))))
          }
      }
    }
  }


  /** ниже идут итерации развития. Из задания не очень понятно можно ли использовать Future.sequence **/

  def fullSequence2[A](futures: List[Future[A]])(implicit ex: ExecutionContext): Future[(List[A], List[Throwable])] = {

    val futuresSorted = futures.map(f =>
      f
        .map(Right(_))
        .recoverWith { case NonFatal(ex) =>
          Future.successful(Left(ex))
        }
    )

    val futureList = Future.sequence(futuresSorted)

    val futureOfCort = futureList.map(rs =>
      rs.foldLeft((List.empty[A], List.empty[Throwable])) {
        case (acc, future) =>
          future match {
            case Left(value) =>
              acc match {
                case (success, failures) => (success, failures.appended(value))
              }
            case Right(value) =>
              acc match {
                case (success, failures) => (success.appended(value), failures)
              }
          }
      }
    )

    futureOfCort
  }

  def fullSequence3[A](futures: List[Future[A]])(implicit ex: ExecutionContext): Future[(List[A], List[Throwable])] = {

    val futuresSorted = futures.map(f =>
      f
        .map(Right(_))
        .recoverWith { case NonFatal(ex) =>
          Future.successful(Left(ex))
        }
    )

    val futureList = Future.sequence(futuresSorted)

    val successes = futureList.map(r => r.collect { case Right(value) => value })
    val fails = futureList.map(r => r.collect { case Left(value) => value })

    successes.zip(fails)
  }
}
