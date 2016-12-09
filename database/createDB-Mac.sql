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
--Mac
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Prelude To Tragedy', 'Lee Actor', 'musicXMLFiles/Samples/ActorPreludeSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('An Die Ferne Geliebte', 'Ludwig van Beethoven', 'musicXMLFiles/Samples/BeetAnGeSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Magnificat Secundi Toni', 'Gilles Binchois', 'musicXMLFiles/Samples/Binchois.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Wie Melodien zieht es mir', 'Johannes Brahms', 'musicXMLFiles/Samples/BrahWiMeSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('West Point', 'Jonatha Brooke', 'musicXMLFiles/Samples/BrookeWestSample.xml');
INSERT INTO musicXMLFiles(songTitle,filePath) VALUES ('Quem queritis', 'musicXMLFiles/Samples/Chant.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Mandoline', 'Claude Debussy', 'musicXMLFiles/Samples/DebuMandSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Im wunderschönen Monat Mai', 'Robert Schumann', 'musicXMLFiles/Samples/Dichterliebe.xml');
INSERT INTO musicXMLFiles(songTitle, filePath) VALUES ('越後獅子', 'musicXMLFiles/Samples/Echigo-Jishi.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Après un rêve', 'Gabriel Fauré', 'musicXMLFiles/Samples/FaurReveSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('4. Die zwei blauen Augen', 'Gustav Mahler', 'musicXMLFiles/Samples/MahlFaGe4Sample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('An Chloe, K. 524', 'Wolfgang Amadeus Mozart', 'musicXMLFiles/Samples/MozaChloSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Excerpt from Piano Sonata in A Major, K. 331', 'Wolfgang Amadeus Mozart', 'musicXMLFiles/Samples/MozartPianoSonata.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Excerpt from Clarinet Quintet, K. 581', 'Wolfgang Amadeus Mozart', 'musicXMLFiles/Samples/MozartTrio.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Das Veilchen, K. 476', 'Wolfgang Amadeus Mozart', 'musicXMLFiles/Samples/MozaVeilSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Saltarello', 'Anonymous', 'musicXMLFiles/Samples/Saltarello.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Ave Maria (Ellen''s Gesang III), D. 839', 'Franz Schubert', 'musicXMLFiles/Samples/SchbAvMaSample.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Excerpt from "Liebe! Liebe! Was ist schöner als die Liebe?"', 'Georg Philipp Telemann', 'musicXMLFiles/Samples/Telemann.xml');

INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Como la Flor', 'A.B. Quintanilla, Pete Astudio', 'musicXMLFiles/From Nel/A.B. Quintanilla, Pete Astudio - Como la Flor.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('Runnin'' Wild', 'A.H.Gibbs,Joe Grey & Leo Wood', 'musicXMLFiles/From Nel/A.H.Gibbs, Joe Grey & Leo Wood - Runnin'' Wild.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('N. 4 ( TEMA.... 31.1.1996)', 'A.MARAFIOTI', 'musicXMLFiles/From Nel/A.MARAFIOTI - N. 4 ( TEMA.... 31.1.1996).xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('I''m Thinking Tonight Of My Blue Eyes', 'A.P.Carter, Don Marcotte', 'musicXMLFiles/From Nel/A.P.Carter, Don Marcotte - I''m Thinking Tonight Of My Blue Eyes.xml');
INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('A Nice Cup of Tea', 'A.P.Herbert, Henry Sullivan', 'musicXMLFiles/From Nel/A.P.Herbert, Henry Sullivan - A Nice Cup of Tea.xml');



END TRANSACTION;


