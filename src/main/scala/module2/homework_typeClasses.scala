package module2

object homework_typeClasses {

  trait Bindable[F[_], A] {
      def map[B](f: A => B): F[B]
      def flatMap[B](f: A => F[B]): F[B]
    }

  object Bindable {
    implicit def from[A](opt: Option[A]): Bindable[Option, A] =
        new Bindable[Option, A] {
          override def flatMap[B](f: A => Option[B]): Option[B] = opt.flatMap(f)
          override def map[B](f: A => B): Option[B] = opt.map(f)
        }
  }

  def tupleF[F[_], A](fa: Bindable[F, A], fb: Bindable[F, A])(implicit bindable: Bindable[F, A]): F[(A, A)] =
      fa.flatMap(a => fb.map(b => (a, b)))
  
  val optA: Option[Int] = Some(1)
  val optB: Option[Int] = Some(2)

  val list1 = List(1, 2, 3)
  val list2 = List(4, 5, 6)

  //val r1 = println(optA tupleF optB)
  //val r2 = println(list1 tupleF list2)
}