To reset database back to default:
-Assuming that SQLite is installed, if it is not installed
go to https://sqlite.org/download.html
-In terminal/command prompt navigate to the musicXML.db folder
-Run sqlite3 musicXML.db 
-Run .read createDB-Mac.sql


NOTE:
The file paths in the createDB-Mac.sql file are set for mac file
paths for windows the “/“ would have to changed to “\”