import module1.opt
import module1.list._

val seq = Seq(1, 2, 3) // seq: Seq

seq.isInstanceOf[Seq[Int]]
seq.isInstanceOf[Seq[String]]


// Option validations
val opt1 = opt.Option(3)

opt1.filter(x => x !=3)
opt1.filter(_ == 3)

val opt2 = opt.Option(null)

opt1 zip opt2

val opt3 = opt.Option("Fantastic")

opt1 zip opt3

opt1.printIfAny(println)
opt2.printIfAny(println)
opt3.printIfAny(println)

// List validations

val l1 = List(1, 2, 3, 4)
val l2 = Nil
val l3 = 3 :: Nil
val l4 = 7 :: l1