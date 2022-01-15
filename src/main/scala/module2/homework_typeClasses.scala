package module2

object homework_typeClasses {

  trait Bindable[F[_], A] {
    def map[B](f: A => B): F[B]
    def flatMap[B](f: A => F[B]): F[B]
  }

  object Bindable {
    implicit def optionBindable[F[_], A](fa: Option[A]): Bindable[Option, A] = new Bindable[Option, A] {
      def map[B](f: A => B): Option[B] = fa.map(f)
      
      def flatMap[B](f: A => Option[B]): Option[B] = fa.flatMap(f)
      
    }

    def apply[F[_], A](implicit ev: Bindable[F, A]): Bindable[F, A] = ev
  }

  def tupleF[F[_], A, B](fa: F[A], fb: F[B])(implicit bindable: F[A] => Bindable[F, A], bindable2: F[B] => Bindable[F, B]): F[(A, B)] =
        fa.flatMap(a => fb.map(b => (a, b)))

  implicit class BindableOps[F[_], A](fa: F[A]) {
    def tupleF2[B](fb: F[B])(implicit bindable: F[A] => Bindable[F, A], bindable2: F[B] => Bindable[F, B]): F[(A, B)] = tupleF(fa, fb)
  }

  val optA: Option[Int] = Some(1)
  val optB: Option[Int] = Some(2)
  val optS: Option[String] = Some("str")

  val list1 = List(1, 2, 3)
  val list2 = List(4, 5, 6)


  optA.tupleF2(optB)
  optA tupleF2 optB

  tupleF(optA, optB)
  tupleF(optA, optS)
  //val r1 = println(tupleF(optA, optB))
  //val r2 = println(tupleF(list1, list2))
}