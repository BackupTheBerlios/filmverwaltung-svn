<?php session_start(); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="index.css" />
 <title>KF-Monkeys Shop</title>
</head>

	<body>
	<p id="kopfleiste" > 
<img  src="bilder/kopfleiste.jpg" alt="Logo der Item-Vermittlung Seite" />
</p>
	
	<?php
		include("navigation.php");
	
	?>
	
	
		<?php
	
		$mysqlhost="localhost";    // MySQL-Host angeben
		$mysqluser="root";    // MySQL-User angeben
		$mysqlpwd="";		// Passwort angeben
		$mysqldb="flyff_shop";    // Gewuenschte Datenbank angeben

		// Anmeldung am MySQL-Server
		$connection=mysql_connect($mysqlhost, $mysqluser, $mysqlpwd);
		
		// Auswählen, welche Datenbank verwendet werden soll
		mysql_select_db($mysqldb, $connection);
		
		// SQL: Item-name, Preis und Upgrade-Level von Tabelle Angebot abfragen
		$sql = "SELECT Item_name, Preis, upgrade_lvl, angebot.ID, server_id FROM angebot INNER JOIN avatar ON avatar.ID = angebot.avatar_id";
		
		// Antwort der Datenbank in $sql_query speichern
		$angebot_query = mysql_query($sql);

		?>
<div id="hauptinhalt">
<h1>Angebote</h1>

	<table>
		<tr>
			<td>Server</td>
			<td>Name des Items</td>
			<td>Preis</td>
			<td>Level des Upgrades</td>
		</tr>
		
	<?php
	 while ($angebot = mysql_fetch_array($angebot_query)) {
	?>
		<tr>
			<td><?=$angebot['server_id']?></td>
			<td><a href=Angebotsdetails.php?angebotsid=<?=$angebot['ID']?>><?=$angebot['Item_name']?></a></td>
			<td><?=$angebot['Preis']?></td>
			<td><?=$angebot['upgrade_lvl']?></td>
		
	
		</tr>
	<?php
	 }
	?>

	</table>
	
	
	
	</div>
	</body>
	
</html>