package module2Tests

import module2.homework_typeClasses._
import org.scalatest.flatspec.AnyFlatSpec

class implicitsTests extends AnyFlatSpec {

  // "check two lists" should "create list of all combinations tuple" in {
  //   assert(tupleF(List(1, 2, 3), List(4, 5, 6)) == List((1,4), (1,5), (1,6), (2,4), (2,5), (2,6), (3,4), (3,5), (3,6)))
  // }

  "check two options" should "return option of tuple" in {
     assert(tupleF(Option("here"), Option("there")) == Option(("here", "there")))
     assert(tupleF(Option(1), Option("something")) == Option((1, "something")))
   }
}
