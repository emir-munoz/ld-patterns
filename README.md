Learning Content Patterns from Linked Data
==========================================

by Emir Munoz (emir@emunoz.org)

* Abstract *
Linked Data (LD) datasets (e.g., DBpedia, Freebase) are used in many knowledge extraction tasks due to the high variety of domains they cover. 
Unfortunately, many of these datasets do not provide description for their properties and classes. It discourages users who want to enrich the data 
or reuse their ontologies. This work describes the first analysis over all DBpedia's properties with textual data. Using an unsupervised pattern 
mining approach we generate a database of lexical patterns for properties values, that can be exploited by any information extraction task. This 
enables (i) the discovery and reuse of existing property, (ii) the human-understanding of  lexical patterns for properties in a generic LD dataset, 
(iii) the detection of data inconsistencies, and (iv) the validation and suggestion of new property values.

* How to deploy the database into MySQL *

The tsv file patterns.tsv.tar.bz2 can be loaded into any SQL or no-SQL database. Here a small example of how to load the content patterns into a MySQL instance.
First, uncompress the provided file to extract the tsv file. Second, execute the following script, modifying the file location.

	# create a table in the database
	CREATE TABLE patterns(predicate VARCHAR(255) NOT NULL, pattern VARCHAR(255) NOT NULL, coverage FLOAT, PRIMARY KEY(predicate, pattern)) CHARACTER SET utf8 COLLATE utf8_bin;
	
	# load the data into the table
	LOAD DATA LOCAL INFILE 'D:/patterns.tsv' INTO TABLE patterns FIELDS TERMINATED BY '\t';
	
	
* How to get the patterns for a property *

The patterns for a given property can be retrieved using a select query as follows:

	mysql> SELECT * FROM patterns where predicate="http://dbpedia.org/ontology/isbn";
	
	+----------------------------------+--------------------------------------------------------+-----------+
	| predicate      				   | pattern												| coverage 	|
	+----------------------------------+--------------------------------------------------------+-----------+
	| http://dbpedia.org/ontology/isbn | ALPHANUMERIC - NUMBER - NUMBER - NUMBER				| 0.56	   	|
	| http://dbpedia.org/ontology/isbn | NUMBER - NUMBER - NUMBER - NUMBER						| 0.56		|
	| http://dbpedia.org/ontology/isbn | ALPHANUMERIC - NUMBER - NUMBER - SMALL_NUMBER			| 0.412		|
	| http://dbpedia.org/ontology/isbn | NUMBER - NUMBER - NUMBER - SMALL_NUMBER				| 0.412		|
	| http://dbpedia.org/ontology/isbn | SMALL_NUMBER - NUMBER - NUMBER - SMALL_NUMBER			| 0.39		|
	| http://dbpedia.org/ontology/isbn | ALPHANUMERIC - MEDIUM_NUMBER - NUMBER - SMALL_NUMBER	| 0.272		|
	| http://dbpedia.org/ontology/isbn | NUMBER - MEDIUM_NUMBER - NUMBER - SMALL_NUMBER			| 0.272		|
	| http://dbpedia.org/ontology/isbn | SMALL_NUMBER - MEDIUM_NUMBER - NUMBER - SMALL_NUMBER	| 0.268		|
	| ...							   | ...													| ...		|
	+----------------------------------+--------------------------------------------------------+-----------+


