#!/usr/bin/env bash

# list the databases
sqoop list-databases \
--connect jdbc:mysql://localhost \
--username training --password training

# list the tables in the movielens database
sqoop list-tables \
  --connect jdbc:mysql://localhost/movielens \
  --username training --password training

# import the movie table to HDFS (tab delimited)
sqoop import \
  --connect jdbc:mysql://localhost/movielens \
  --username training --password training \
  --fields-terminated-by '\t' --table movie 

# import the movie rating table to HDFS (tab delimited)
sqoop import \
  --connect jdbc:mysql://localhost/movielens \
  --username training --password training \
  --fields-terminated-by '\t' --table movierating 

# confirm the movie import
hadoop fs -ls movie
hadoop fs -tail movie/part-m-00000

# confirm the movie ratings import
hadoop fs -ls movierating
hadoop fs -tail movierating/part-m-00000
