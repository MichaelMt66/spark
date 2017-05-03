// Example code for mapPartitions

// given a partition containing log file data, find the number of JPG requests
def countJpgs(index: Int, partIter: Iterator[String]): Iterator[(Int,Int)] = {
    var jpgcount = 0
    for (line <- partIter) 
        if (line.contains("jpg")) jpgcount += 1
    Iterator((index,jpgcount))
}
 
// Map each file partition to the number of JPG requests in that file
sc.textFile("weblogs/*").mapPartitionsWithIndex(countJpgs).collect()

