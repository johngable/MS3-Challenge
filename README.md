# MS3-Challenge

Coding challenge for MS3-Interview Application

## What does it do?

The code initializes an SQLite DB within the repo folder and fill its with data from a user
inputted CSV file.

## How to setup and run?

Clone the repo preferably onto your desktop for ease of use. Open the project from Eclipse, and 
build the project. SQLite executables are included within the repo (at the location your database will be made). 

Allow the project to build in Eclipse. 

Run the ApplicationRunner.java file, and input your CSV file relative pathing into the terminal  (./../input-file.csv).
I found putting your csv in the outermost folder "MS3-Challenge" to be easiest (The emailed csv is included with the repo at this location just run
and enter (./../ms3Interview.csv) as the file path).

The program will initialize and fill you an SQLite Database which can be found within the MS3-Challenge folder at (MS3-Challenge/db/'input-file'.db).
  
Upon completion, a log will be placed in the outermost folder MS3-Challenge named after your input csv file. The log will contain
the results of your CSV records (Total # read, Total # input to db, Total # bad records).

Along with the log, you will find an additional csv file at the same location named after your input csv file with an attached -bad.csv.
This is a CSV file of records that were incomplete and not able to be entered into the database.

**The same input can be inputted multiple times, but the DB will not be cleared.
