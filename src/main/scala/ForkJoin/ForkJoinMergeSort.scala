package ForkJoin

import java.util.concurrent._

class ForkJoinMergeSort(
    SEQUENTIAL_THRESHOLD: Int,
    numbers: List[Int]
) extends RecursiveTask[List[Int]] {

  override protected def compute(): List[Int] = {
    println(Thread.currentThread().toString)
    if (numbers.lengthCompare(SEQUENTIAL_THRESHOLD) < 0) {
      numbers.sorted
    } else {
      val midpoint                        = numbers.size / 2
      val left: RecursiveTask[List[Int]]  = new ForkJoinMergeSort(SEQUENTIAL_THRESHOLD, numbers.take(midpoint))
      val right: RecursiveTask[List[Int]] = new ForkJoinMergeSort(SEQUENTIAL_THRESHOLD, numbers.drop(midpoint))
      ForkJoinTask.invokeAll(left, right)
      MergeSort.MergeSort.merge(left.getRawResult, right.getRawResult)
    }
  }
}
object ForkJoinMergeSort extends App {

  val forkJoinPool              = new ForkJoinPool(4)
  val mergeSort                 = new ForkJoinMergeSort(2, List(12, 45, 1, 67, 90, 55, 8, 43, 19))
  private val sorted: List[Int] = forkJoinPool.invoke(mergeSort)

  println(sorted)
}
