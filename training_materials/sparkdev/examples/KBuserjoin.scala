// Example: Join web server logs with a list of knowledge base articles to create
// a list of users and the articles they viewed

// parse a line from a web log file to find the ID of the knowledge base article requested
val matchre = "KBDOC-[0-9]*".r
def getRequestDoc(s: String): String = { matchre.findFirstIn(s).orNull }

// read in file of knowledge base articles 
// line format is docid:title
var kblistfile = "file:/home/training/training_materials/sparkdev/data/kblist.txt"
var kblist =  sc.textFile(kblistfile).
   map(line => line.split(":")).
   map(tokens => (tokens(0),tokens(1)))

// create a list of all KB articles requested with userid
var logs = "file:/home/training/training_materials/sparkdev/data/weblogs/*"      
var kbreqs = sc.textFile(logs).
    map(line => (getRequestDoc(line),line.split(" ")(2))).
    filter(pair => pair._1 != null).
    distinct()

// Join kb articles with kb article requests, key by user id and group by key
var titlereqs = kbreqs.join(kblist).
   map{case (docid,(userid,title)) => (userid,title)}
    groupByKey()

// Display the first 10 user IDs and their requested knowledge base articles
for ((userid,titles) <- titlereqs.take(10)) {
    println("userid: " + userid)
    for (title <- titles) println ("\t" + title)
}
