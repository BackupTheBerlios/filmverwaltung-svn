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
<img id="kopfleiste-bild" src="bilder/kopfleiste.jpg" alt="Logo der Item-Vermittlung Seite" />
</p>
	
	<?php
		include("navigation.php");
	?>

<div id="hauptinhalt">	
	<h1>Angebots-Details</h1>
	<?php
		$angebotsid=$_GET['angebotsid'];
		$mysqlhost="localhost";    // MySQL-Host angeben
		$mysqluser="root";    // MySQL-User angeben
		$mysqlpwd="";		// Passwort angeben
		$mysqldb="flyff_shop";    // Gewuenschte Datenbank angeben
		// Anmeldung am MySQL-Server
		$connection=mysql_connect($mysqlhost, $mysqluser, $mysqlpwd);
		
		// Auswählen, welche Datenbank verwendet werden soll
		mysql_select_db($mysqldb, $connection);
		
		// SQL: Item-name, Preis und Upgrade-Level von Tabelle Angebot abfragen
		$sql = "SELECT Item_name, Preis, upgrade_lvl, Beschreibung, ID  FROM angebot WHERE ID='$angebotsid'";
		
		// Antwort der Datenbank in $sql_query speichern
		$angebot_query = mysql_query($sql);
	
		$angebot = mysql_fetch_array($angebot_query)
	?>
	<?=$angebotsid?>
	<table>
		<tr>
			<td>Name des Items</td>
			<td><?=$angebot['Item_name']?></td>
		</tr>
		<tr>
			<td>Preis</td>
			<td><?=$angebot['Preis']?></td>
		</tr>
		<tr>
			<td>Level des Upgrades</td>
			<td><?=$angebot['upgrade_lvl']?></td>
		</tr>
		<tr>
			<td> Beschreibung</td>
			<td><?=$angebot['Beschreibung']?></td>
		</tr>
		</table>
		
	</div>
	</body>
	
</html>