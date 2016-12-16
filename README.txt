##########################################################################
MusicXML Parser

Created By:
	Joel Sprinkle

Description:
	MusicXML file parser and exporter.

Build Platform:
	macOS 10.12.2
	IntelliJ IDEA Community 2016.2.5
	Sqlite3
##########################################################################
###################################
ChangeLog:

Version 1.0 - Git Release - Dec. 9, 2016
MusicXML files are now parsed when clicked on.
Some XML data of the parsed file is displayed in the textArea.
Users can also export MusicXML data back to a file now.


Version 0.1 - Git Release - Sept. 23, 2016
GUI displays database contents in a tableView.
The User can add/edit/delete database contents through the GUI interface.


###################################
Important files:

- Main - Launches the app and starts the primary stage for the GUI

- Database - Controls the database, additions/edits/filtering/deletions 

- Controller - Controls the main GUI scene of the application

- MXMLDetailsController - Controls the details GUI scene for a MusicXML file

- XMLParser - Starts the parsing process of a MusicXML file

- Score - Holds all of the parsed data of a MusicXML file

- ExportXML - Exports MusicXML data back to a file


###################################
To Run the application:

Import the project into IntelliJ, build it, run main.


###################################
Database Info:

To reset the database back to default:
(Assuming that SQLite is installed, if it is not installed
go to https://sqlite.org/download.html)
-In terminal/command prompt navigate to the folder where musicXML.db is located 
-Run sqlite3 musicXML.db 
-Run .read createDB-Mac.sql

NOTE:
The file paths for the sample XML files in the createDB-Mac.sql file are set
for mac file paths for windows the “/“ would have to changed to “\”