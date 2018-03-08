package ThreadPool

import java.util.concurrent.{Callable, ExecutorService, Executors, Future}

import MergeSort.MergeSort

class ThreadPoolMergeSort(
    SEQUENTIAL_THRESHOLD: Int,
    numbers: List[Int]
)(implicit val executorService: ExecutorService)
    extends Callable[List[Int]] {

  def call(): List[Int] = {
    println(Thread.currentThread().toString)
    if (numbers.lengthCompare(SEQUENTIAL_THRESHOLD) < 0) {
      numbers.sorted
    } else {
      val midpoint                 = numbers.size / 2
      val left: Future[List[Int]]  = executorService.submit(new ThreadPoolMergeSort(SEQUENTIAL_THRESHOLD, numbers.take(midpoint)))
      val right: Future[List[Int]] = executorService.submit(new ThreadPoolMergeSort(SEQUENTIAL_THRESHOLD, numbers.drop(midpoint)))
      MergeSort.merge(left.get, right.get)
    }
  }
}

object ThreadPoolMergeSort extends App {

  implicit val executorService: ExecutorService = Executors.newCachedThreadPool()

  private val mergeSort = new ThreadPoolMergeSort(2, List(12, 45, 1, 67, 90, 55, 8, 43, 19))

  private val value: Future[List[Int]] = executorService.submit(mergeSort)

  println(value.get)

  executorService.shutdown()
}
