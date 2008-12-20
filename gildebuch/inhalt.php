
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="LANGUAGE" content="deutsch,german,DE,AT,CH">
<link type="text/css" rel="stylesheet" href="buch.css" />
 <title>KungFuMonkeys Gildenbuch - Inhalt</title>
</head>
		

	<body>
	
	<?php
		
		if (!isset($_GET['id']))
			die ("Ungültiger Aufruf der Seite.");
		
		$id = $_GET['id'];
		
		$mysqlhost="localhost";    // MySQL-Host angeben
		$mysqluser="root";    // MySQL-User angeben
		$mysqlpwd="";		// Passwort angeben
		$mysqldb="gildebuch";    // Gewuenschte Datenbank angeben
		// Anmeldung am MySQL-Server
		$connection=mysql_connect($mysqlhost, $mysqluser, $mysqlpwd);
		
		// Auswählen, welche Datenbank verwendet werden soll
		mysql_select_db($mysqldb, $connection);

		// SQL: Alle Kategorien abfragen
		$sql="SELECT * FROM `buch` WHERE id = $id ";
				
		// Antwort der Datenbank in $sql_query speichern
		$query = mysql_query($sql);

		
	?>
	
		<div class="rechts">
				<p>

			<?php
			 while ($eintrag = mysql_fetch_array($query)) {
			?>
			
				<h1><?=$eintrag['titel']?></h1>
			
				<?=$eintrag['text']?><br>
			
			<?
			 }
			?>

				</p>
		</div>
			
		<div class="links">
				<p>
				links / bilder
				 <img  src="bilder/<?=$bild['bild']?>" alt="Bilder" />   
				</p>
		</div>
	</body>
	
</html>