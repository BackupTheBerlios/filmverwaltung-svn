<?php session_start(); ?>

<html>

<head>
 <title>KF-Monkeys Shop</title>
</head>

	<body>
	
	<h1>Angebots-Details</h1>
	
	<?php
		include("navigation.php");
	?>
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
		
		
	
	</body>
	
</html>