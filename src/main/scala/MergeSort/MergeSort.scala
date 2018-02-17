package MergeSort

object MergeSort extends App {

  def merge(left: List[Int], right: List[Int]): List[Int] = {
    def tailRecMerge(left: List[Int], right: List[Int], acc: List[Int]): List[Int] = {
      (left, right) match {
        case (Nil, _) ⇒ acc ++ right
        case (_, Nil) ⇒ acc ++ left
        case (headL :: tailL, headR :: tailR) ⇒
          if (headL <= headR) tailRecMerge(tailL, `right`, acc :+ headL)
          else tailRecMerge(`left`, tailR, acc :+ headR)

      }
    }
    tailRecMerge(left, right, Nil)
  }

}
