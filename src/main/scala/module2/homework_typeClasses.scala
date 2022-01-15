package module2

object homework_typeClasses {

  trait Bindable[F[_], A] {
    def map[B](f: A => B): F[B]
    def flatMap[B](f: A => F[B]): F[B]
  }

  object Bindable {
    implicit def optionBindable[F[_], A](fa: Option[A]): Bindable[Option, A] =
      new Bindable[Option, A] {
        def map[B](f: A => B): Option[B] = fa.map(f)

        def flatMap[B](f: A => Option[B]): Option[B] = fa.flatMap(f)

      }

    implicit def listBindable[F[_], A](fa: List[A]): Bindable[List, A] =
      new Bindable[List, A] {
        def map[B](f: A => B): List[B] = fa.map(f)

        def flatMap[B](f: A => List[B]): List[B] = fa.flatMap(f)

      }

    def apply[F[_], A](implicit ev: Bindable[F, A]): Bindable[F, A] = ev
  }

  def tupleF[F[_], A, B](fa: F[A], fb: F[B])(implicit bindA: F[A] => Bindable[F, A], bindB: F[B] => Bindable[F, B]): F[(A, B)] =
    fa.flatMap(a => fb.map(b => (a, b)))

  implicit class BindableOps[F[_], A, B](fa: F[A])(implicit implA: F[A] => Bindable[F, A], implB: F[B] => Bindable[F, B]) {
    def tupleF2(fb: F[B]): F[(A, B)] = fa.flatMap(a => fb.map( b=> (a, b)))
  }

  val optA: Option[Int] = Some(1)
  val optB: Option[Int] = Some(2)
  val optS: Option[String] = Some("str")

  val list1 = List(1, 2, 3)
  val list2 = List(4, 5, 6)

  optA.tupleF2(optB)
  optA tupleF2 optB
  optA tupleF2 optS

  tupleF(optA, optB)
  tupleF(optA, optS)
  //val r1 = println(tupleF(optA, optB))
  //val r2 = println(tupleF(list1, list2))
}
