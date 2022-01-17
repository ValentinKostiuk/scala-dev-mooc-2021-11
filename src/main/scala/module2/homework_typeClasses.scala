package module2

object homework_typeClasses {

  trait Bindable[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  }

  object Bindable {
    implicit def optionBindable[F[_]]: Bindable[Option] =
      new Bindable[Option] {
        def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
        
        def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)
        
      }

    implicit def listBindable[F[_]]: Bindable[List] =
      new Bindable[List] {
        def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
        
        def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
        
      }

    def apply[F[_]](implicit ev: Bindable[F]): Bindable[F] = ev
  }

  def tupleF[F[_], A, B](fa: F[A], fb: F[B])(implicit bind: Bindable[F]): F[(A, B)] = bind.flatMap(fa)(a => bind.map(fb)(b => (a, b)))

  implicit class BindableOps[F[_], A](fa: F[A])(implicit bind: Bindable[F]) {
    def tupleF2[B](fb: F[B]): F[(A, B)] = bind.flatMap(fa)(a => bind.map(fb)(b => (a, b)))
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
