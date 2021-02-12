# MS3-Challenge

Coding challenge for MS3-Interview Application

## What does it do?

The code initializes an SQLite DB within the repo folder and fills it with VALID data from a user
inputted CSV file.

## How to setup and run

Clone the repo preferably onto your desktop for ease of use. Open the project from Eclipse, and 
build the project. SQLite executables are included within the repo (at the location your database will be made). 


### **IMPORTANT**
* Right click the project from you project explorer and navigate to "Build Path -> Configure Build Path"
* In the window that pops up, under "Libraries" click the "JRE System Library"
  * An "Edit Library" window should appear and ensure that in the Alternate JRE field "JAVA SE 13" is selected.



Run the ApplicationRunner.java file, and input your CSV file relative pathing into the terminal  (./../input-file.csv).
I found putting your csv in the outermost folder "MS3-Challenge" to be easiest (The emailed csv is included with the repo at this location just run
and enter (./../ms3Interview.csv) as the file path).

The program will initialize and fill you an SQLite Database which can be found within the MS3-Challenge folder at (MS3-Challenge/db/'input-file'.db).
  
Upon completion, a log will be placed in the outermost folder MS3-Challenge named after your input csv file. The log will contain
the results of your CSV records (Total # read, Total # input to db, Total # bad records).

Along with the log, you will find an additional csv file at the same location named after your input csv file with an attached -bad.csv.
This is a CSV file of records that were incomplete and not able to be entered into the database.

**The same input can be inputted multiple times, but the DB will not be cleared.

## Design and Assumptions

### Design : 

The design is pretty simple. I took the common 3 layered architecture for an application and just trimmed off the fat. There are 3 classes, one to facilitate all interactions with the user (presentation layer), one to facilitate businiess logic for handling CSVs (application layer), and one to facilitate all database interactions (data layer).

In all honesty, I wasn't sure how rounded to design the application. If it was to be a full fledged application, I would most likely add a java swing front end to replace the CLI, command interface with commands to bridge the gap between presentation and applpication layer, as well as table/row gateways for the table. This is commonly what I do when creating java based applications, but again I wasn't exactly sure if thats what was being desired from the documents I was given.

To keep database commits to a minimum (for times sake), I added each entry to a batch of prepared statements. I noted in javadocs that the 
speed was optimal with one large batch, but if data entry grew larger the batch should be broken down as well. 

### Assumptions : 

* The column names and entry lengths will remain the same. Nothing was specified in the requirements so I left the columns static instead of dynamic per 
csv.

* CSV file sizes from user are not drastic enough to slow the program, if so batch sizes must be specified. 

* I am also assuming that end users are somewhat familiar with pathing (specifically realtive pathing) in order to allow the files to be found.
