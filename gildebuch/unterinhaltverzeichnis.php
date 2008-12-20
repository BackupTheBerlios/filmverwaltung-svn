
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="LANGUAGE" content="deutsch,german,DE,AT,CH">
<link type="text/css" rel="stylesheet" href="buch.css" />
 <title>KungFuMonkeys Gildenbuch - Unterinhlatsverzeichnis</title>
</head>
		

	<body>
	
	<?php
		
		if (!isset($_GET['kategorie']))
			die ("Ungültiger Aufruf der Seite.");
		
		$kategorie = $_GET['kategorie'];
		
		$mysqlhost="localhost";    // MySQL-Host angeben
		$mysqluser="root";    // MySQL-User angeben
		$mysqlpwd="";		// Passwort angeben
		$mysqldb="gildebuch";    // Gewuenschte Datenbank angeben
		// Anmeldung am MySQL-Server
		$connection=mysql_connect($mysqlhost, $mysqluser, $mysqlpwd);
		
		// Auswählen, welche Datenbank verwendet werden soll
		mysql_select_db($mysqldb, $connection);

		// SQL: Alle Kategorien abfragen
		$sql="SELECT ID,titel FROM `buch` WHERE kategorie_1 = '$kategorie' ";
				
		// Antwort der Datenbank in $sql_query speichern
		$query = mysql_query($sql);
		
	?>
	
		<div class="rechts">
			<p>

			<?php
			 while ($eintrag = mysql_fetch_array($query)) {
			?>

				<a href="inhalt.php?id=<?=$eintrag['ID']?>"><?=$eintrag['titel']?></a><br>
	
			<?php
			 }
			?>

			</p>
		</div>
		
		<div class="links">
			<p>
			links / bilder
			</p>
		</div>

	</body>
	
</html>