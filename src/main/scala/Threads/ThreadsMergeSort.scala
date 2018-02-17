package Threads

class ThreadsMergeSort(
    SEQUENTIAL_THRESHOLD: Int,
    numbers: List[Int]
) extends Runnable {

  private var sorted: List[Int] = _

  def getSortedList: List[Int] = sorted

  override def run(): Unit = {
    println(Thread.currentThread().toString)
    if (numbers.lengthCompare(SEQUENTIAL_THRESHOLD) < 0) {
      sorted = numbers.sorted
    } else {
      val midpoint = numbers.size / 2

      val mergeSortL = new ThreadsMergeSort(SEQUENTIAL_THRESHOLD, numbers.take(midpoint))
      val left       = new Thread(mergeSortL)
      left.start()

      val mergeSortR = new ThreadsMergeSort(SEQUENTIAL_THRESHOLD, numbers.drop(midpoint))
      val right      = new Thread(mergeSortR)
      right.start()

      left.join()
      right.join()

      sorted = MergeSort.MergeSort.merge(mergeSortL.getSortedList, mergeSortR.getSortedList)
    }
  }
}

object ThreadsMergeSort extends App {
  private val mergeSort = new ThreadsMergeSort(2, List(12, 45, 1, 67, 90, 55, 8, 43, 19))
  private val t: Thread = new Thread(mergeSort)
  t.start()
  t.join()
  println(mergeSort.getSortedList)
}
