import module1.opt
import module1.list._

val seq = Seq(1, 2, 3) // seq: Seq
import scala.util.matching.Regex
val isCyrillic = "['\\p{IsCyrillic}]*".r

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

val l1 = List(1, 2, 3, 4, 6, 9, 7, 9)
val l2 = Nil
val l3 = 3 :: Nil
val l4 = 8 :: l1

l1.mkString(":")
l2.mkString("!")
l3.mkString("\\")

l1.map(x => x + 3)
l1.map(x => x + 3).map(x => x.toString)
l4.map(x => x.toString + "auf").mkString("!! ")

val l1r = l1.reverse()

val l1f = l1.filter(x => x%2 != 0)
val l1f2 = l1.filter(x => x%2 == 0)
l1f.mkString("; ")
l1f2.mkString("; ")

incList(l1).mkString("; ")
shoutString(l1.map(x => x.toString())).mkString(" ")

isCyrillic.matches("Hello")