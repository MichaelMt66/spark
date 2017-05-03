// Example: For each letter, calculate the average length of the words starting with that letter

var myfile = "file:/home/training/training_materials/sparkdev/data/frostroad.txt"

var avglens = sc.textFile(myfile).
  flatMap(line => line.split("\\W")).
  filter(line => line.length > 0).
  map(word => (word(0),word.length)).
  groupByKey().
  map(pair => (pair._1, pair._2.sum/pair._2.size.toDouble))

avglens.take(10)
