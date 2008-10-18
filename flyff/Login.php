<?php session_start(); ?>



<html>

<head>
 <title>KF-Monkeys Shop</title>
</head>

	<body>
	
	
	
		<h1>Anmeldung</h1>
		
	

		
		
	<?php
	
	
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
			$sql = "Select * FROM account where loginname='$Benutzername' and  passwort='$passwort'";
			
			// Antwort der Datenbank in $sql_query speichern
			$sql_query = mysql_query($sql);
			
			$numrows = mysql_num_rows($sql_query);	
				
		
				if ($numrows == 0) {
					?>
					Nutzername oder Passwort nicht richtig eingegeben
					
					<?php	
				}
				
				if ($numrows == 1) {
					
					$_SESSION["Benutzername"]=$_POST['Benutzername'];
					
					?>
					Angemeldet als <?=$_POST['Benutzername']?> 
					
					<?php	
				}
				

							
		}
		?>
		
	
	<?php
	//menü
		include("navigation.php");
	?>
	
	
	 <!-- Loggin-Eingabe -->
	
	<form action="Login.php" method="post">
		<fieldset>
		<legend>Logindaten eingeben</legend>
		<label>Benutzername: <input type="text" name="Benutzername" /></label>
		<label>Password: <input type="text" name="passwort" /></label>
		<input type="submit" name="formaction" value="Einloggen" />
    	</fieldset>
	</form>
	
	
	
	
	
	</body>
	
</html>