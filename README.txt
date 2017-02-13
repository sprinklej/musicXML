##########################################################################
MusicXML Parser

Created By:
	Joel Sprinkle

Description:
	MusicXML file parser and exporter.
	For a more detailed look into how this project was created read
	the "MusicXML Parser - Report.pdf" file.

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

Import the project into IntelliJ:
-Select "Import Project"
-Select the folder where the project is located, click ok.
-Select "Create project from existing sources" click next
-Select a project name, click next. It is fine to override the existing .idea folder.
-Ensure the src folder is selected, click next.
-Ensure the libraries are selected, click next.
-Ensure the modules are selected, click next.
-Select a JDK (I am using 1.8)
-Click finish.

Once the project opens, if the existing .idea folder was overwritten, you must also:
-From the Run dropdown select Edit Configurations...
-Select the "+" to create a new configuration
-Select "Application"
-At the very top of the window Name the new configuration "Main"
-Select the "..." button beside the "Main Class:" field
-Choose the only option, should be Main (MusicXML)
-Click Ok, click OK again 

Build the project.

run the project.


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
