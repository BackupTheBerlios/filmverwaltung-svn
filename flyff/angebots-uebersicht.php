<?php session_start(); ?>

<html>

<head>
 <title>KF-Monkeys Shop</title>
</head>

	<body>
	
	<h1>Angebote</h1>
	
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
	
	
	
	
	</body>
	
</html>