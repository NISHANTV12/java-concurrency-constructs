package SimpleMergeSort

class SimpleMergeSort(SEQUENTIAL_THRESHOLD: Int, numbers: List[Int]) {

  def mergeSort(): List[Int] = {
    if (numbers.lengthCompare(SEQUENTIAL_THRESHOLD) < 0) {
      numbers.sorted
    } else {
      val midpoint = numbers.size / 2

      val mergeSortL = new SimpleMergeSort(SEQUENTIAL_THRESHOLD, numbers.take(midpoint)).mergeSort()
      val mergeSortR = new SimpleMergeSort(SEQUENTIAL_THRESHOLD, numbers.drop(midpoint)).mergeSort()

      MergeSort.MergeSort.merge(mergeSortL, mergeSortR)
    }
  }

}

object SimpleMergeSort extends App {
  private val mergeSort = new SimpleMergeSort(2, List(12, 45, 1, 67, 90, 55, 8, 43, 19))
  println(mergeSort.mergeSort())
}
