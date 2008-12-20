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
	//menü
		include("navigation.php");
	?>
	
		<div id="hauptinhalt">
		<h1>Regstirieren</h1>
		
		
	
	

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
	<h3 class="login" > Registrier-Daten eingeben </h3>
	<form action=Registration.php method="post">
		<fieldset>
		<table class="tabelle_login" >
					<tr> 
						<th>Benutzername:</th>
						<td>
							 <input type="text" name="Benutzername" />
						</td>
					</tr> 
					<tr>
						<th>Password: </th>
						<td>
						 <input type="text" name="passwort" />
						 </td> 
					</tr>
					<tr>
						<th> </th>
						<td>
							<input type="submit" name="formaction" value="registrieren" />
					 	</td>
			</table>
			</fieldset>
	</form>
	
	</div>
	</body>
	
</html>