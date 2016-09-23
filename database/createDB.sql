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
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Wie Melodien zieht es mir', 'Johannes Brahms', '/Users/sprinklej/Documents/musicXML_Files/BrahWiMeSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('West Point', 'Jonatha Brooke', '/Users/sprinklej/Documents/musicXML_Files/BrookeWestSample.xml');
INSERT INTO musicXMLFiles(songTitle,filePath) VALUES ('Quem queritis', '/Users/sprinklej/Documents/musicXML_Files/Chant.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Mandoline', 'Claude Debussy', '/Users/sprinklej/Documents/musicXML_Files/DebuMandSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Im wunderschönen Monat Mai', 'Robert Schumann', '/Users/sprinklej/Documents/musicXML_Files/Dichterliebe.xml');
INSERT INTO musicXMLFiles(songTitle, filePath) VALUES ('越後獅子', '/Users/sprinklej/Documents/musicXML_Files/Echigo-Jishi.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Après un rêve', 'Gabriel Fauré', '/Users/sprinklej/Documents/musicXML_Files/FaurReveSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('4. Die zwei blauen Augen', 'Gustav Mahler', '/Users/sprinklej/Documents/musicXML_Files/MahlFaGe4Sample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('An Chloe, K. 524', 'Wolfgang Amadeus Mozart', '/Users/sprinklej/Documents/musicXML_Files/MozaChloSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Excerpt from Piano Sonata in A Major, K. 331', 'Wolfgang Amadeus Mozart', '/Users/sprinklej/Documents/musicXML_Files/MozartPianoSonata.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Excerpt from Clarinet Quintet, K. 581', 'Wolfgang Amadeus Mozart', '/Users/sprinklej/Documents/musicXML_Files/MozartTrio.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Das Veilchen, K. 476', 'Wolfgang Amadeus Mozart', '/Users/sprinklej/Documents/musicXML_Files/MozaVeilSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Saltarello', 'Anonymous', '/Users/sprinklej/Documents/musicXML_Files/Saltarello.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Ave Maria (Ellen''s Gesang III), D. 839', 'Franz Schubert', '/Users/sprinklej/Documents/musicXML_Files/SchbAvMaSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Excerpt from "Liebe! Liebe! Was ist schöner als die Liebe?"', 'Georg Philipp Telemann', '/Users/sprinklej/Documents/musicXML_Files/Telemann.xml');

END TRANSACTION;


