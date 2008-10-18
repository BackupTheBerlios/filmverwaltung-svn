<?php session_start(); ?>

<html>

<head>
 <title>KF-Monkeys Shop</title>
</head>

	<body>
	
		<h1>Regstirieren</h1>
		
		
		
	<?php
	//menü
		include("navigation.php");
	?>
	
	
	

	<?php
//Verarbeitung der Formular-Eingabe
		if (isset($_POST['Benutzername'], $_POST['passwort'])) {
		$Benutzername=$_POST['Benutzername'];
		$passwort=$_POST['passwort'];
		
		$mysqlhost="localhost";    // MySQL-Host angeben
		$mysqluser="root";    // MySQL-User angeben
		$mysqlpwd="";		// Passwort angeben
		$mysqldb="flyff_shop";    // Gewuenschte Datenbank angeben

		// Anmeldung am MySQL-Server
		$connection=mysql_connect($mysqlhost, $mysqluser, $mysqlpwd);
		
		// Auswählen, welche Datenbank verwendet werden soll
		mysql_select_db($mysqldb, $connection);
		
		// SQL: Item-name, Preis und Upgrade-Level von Tabelle Angebot abfragen
		$sql = "INSERT INTO account ( loginname , passwort) VALUES ('$Benutzername', '$passwort');";
		
		// Antwort der Datenbank in $sql_query speichern
		mysql_query($sql);		
			
			
			
			
	?>

		Registriert als <?=$_POST['Benutzername']?> 
	
	<?php
	 }
	?>
	
	
	
	
 <!-- Registrierungs-Eingabe -->
	
	<form action=Registration.php method="post">
		<fieldset>
		<legend>Logindaten eingeben</legend>
		<label>Benutzername: <input type="text" name="Benutzername" /></label>
		<label>Password: <input type="text" name="passwort" /></label>
		<input type="submit" name="formaction" value="Einloggen" />
    	</fieldset>
	</form>
	</body>
	
</html>