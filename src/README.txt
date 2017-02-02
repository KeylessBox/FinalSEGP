As 01.02.2017

Tried to clean a bit how the files are arranged. After a lot of hours of work/research, I finally managed to implement the "Import csv files functionality"
But. There is a weird bug. It puts the data into the database, but the id (the primary key of the info TABLE) is incrementally jumping (let's say, you want to add n entries to the database, through the app. You got them nice there, everything perfect. Let's say you want to add other entries. The index jumps from the last index by n positions. Urgh, I'll look into it)

How it works? 
You press "Edit details" button (for now) and it gives you a new window, where you find your file. By opening it, you send the file pathname to the app, and from there it imports the information. 
The problem is. The query has to be tailored to the csv file. The csv file has to be tailored to the database. Yee, it sucks, but I'll find a way around this. 

Use HeidiSQL = UI for MySQL, cool stuff. Helped me a lot!

Let me help you out, on how to set up all these to your PC(or at least tell you how I did).

1. Download Xampp:
https://www.apachefriends.org/download.html

2. Download HeidiSQL (I went with the portable version, I guess whatever is fine)
http://www.heidisql.com/ 

3. Open Xampp -> Activate MySQL

4. From here I did it like this, maybe you can find a better way.

Open terminal

cd C:\xampp\mysql\bin

mysql -u root -p
(no password)

Now you are connected to the mysql.
You have to create a database now. To make it standard, please use this:

CREATE DATABASE investigationsdb;

Now you have to create a user (again, to make it standard, so that everyone has it the same)

CREATE USER 'user'@'localhost' IDENTIFIED BY '';
USE investigationsdb;

Now you have to grant permissions to that specific user:

GRANT ALL ON testdb.* TO 'testuser'@'localhost';

You can add some default data I made as quick as possible by writing:

source <fullPath>\load.sql

Load.sql is a file that will populate the database with simple data, we will elaborate it more in the future

5. Open HeidiSQL -> New -> (Add user as root and password left blank, the port should be 3306) -> Open -> Select investigationsdb and do w/e you want. It's easier to use this, than the terminal. Your choice.

By doing all these steps, you should have the program up and running. Later on, we'll think of a way to make these connections automatically (user friendly stuff). 

OH! case1.csv is the only file, unless you make something similar, with the functionality right now. As I said, I'll try to make it work properly tomorrow.



























