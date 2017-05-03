// Example code from the Working with RDDs chapter

// generate an array of random numbers and parallelize it to an RDD
var numlist: Array[Double] = new Array(10000)
for (i <- 0 until numlist.length-1) numlist(i) = Math.random() * 10 
var randomrdd = sc.parallelize(numlist)
// calculate the average
println( "Mean:"+randomrdd.mean())

// use flatMap to map a text file into individual words, and distinct to remove duplicates
var myfile = "file:/home/training/training_materials/sparkdev/data/purplecow.txt"
sc.textFile(myfile).
  flatMap(line => line.split("\\W")).
  distinct().
  collect()

// read a file with format userid[tab]firstname lastname
//var users = sc.textFile(file) \
//    .map(line => line.split('\t')) \
//    .map(fields => (fields[0],fields[1]))

// Read zip code, latitude, longitude from a file, map to (zip,(lat,lon))
var zipcoordfile = "file:/home/training/training_materials/sparkdev/data/latlon.tsv"
var zipcoords = sc.textFile(zipcoordfile).
    map(line => line.split('\t')).
    map(fields => (fields(0),(fields(1),fields(2)))
for ((zip,coords) <- zipcoords.take(5)) println( "Zip code: " + zip + " at " + coords)

// order file format: orderid:skuid,skuid,skuid...
// map to RDD of skuids keyed by orderid
var orderfile = "file:/home/training/training_materials/sparkdev/data/orderskus.txt"
var orderskus = sc.textFile(orderfile).
   map(line => line.split('\t')).
   map(fields => (fields(0),fields(1))).
   flatMapValues(skus => skus.split(':'))
for (pair <- orderskus.take(5)) {
    println(pair._1)
    pair._2.foreach(print)
}

// count words in a file
var wordfile = "file:///home/training/training_materials/sparkdev/data/purplecow.txt"
var counts = sc.textFile(wordfile).
    flatMap(_.split("\\W")).
    map((_,1)).
    reduceByKey(_+_)

var counts2 = sc.textFile(wordfile).
    flatMap(line => line.split("\\W")).
    map(word => (word,1)).
    reduceByKey((v1,v2) => v1+v2)

for (pair <- counts.take(5)) println (pair)



