--DROP THE DATABASE TABLES
--=======================
DROP TABLE IF EXISTS musicXMLFiles;

--CREATE DATABASE TABLES
--=======================

create table if not exists musicXMLFiles(
      id integer primary key not null, --auto increment key 
      songTitle text NOT NULL, --title of the songs
	  composer text, --composers of the songs
      filePath text NOT NULL  --file location of the songs
      );

--INSERT DATA
--=======================

BEGIN TRANSACTION; 

INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Prelude To Tragedy', 'Lee Actor', '/Users/sprinklej/Documents/musicXML_Files/ActorPreludeSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('An Die Ferne Geliebte', 'Ludwig van Beethoven', '/Users/sprinklej/Documents/musicXML_Files/BeetAnGeSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Magnificat Secundi Toni', 'Gilles Binchois', '/Users/sprinklej/Documents/musicXML_Files/Binchois.xml');

END TRANSACTION;


