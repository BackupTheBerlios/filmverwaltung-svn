<?php session_start(); ?>

<html>

<head>
 <title>KF-Monkeys Shop</title>
</head>

	<body>
	
	<h1>Eigenes Konto</h1>
	
	<?php
		include("navigation.php");
		
		if(!isset($_SESSION['Benutzername'])){      //Die in IF umwandeln, da besser!!!!!
			die("bitte einloggen");
		} 
			
		$benutzername = $_SESSION['Benutzername'];
		
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
		
		$sql_server = "SELECT * FROM Server;";	
	 	$server_query=mysql_query($sql_server);
	
	//Verarbeitung der Formular-Eingabe
		if (isset($_POST['name'], $_POST['server_id'])) {
		$avatarname=$_POST['name'];
		$server_id=$_POST['server_id'];
		$mysqlhost="localhost";    // MySQL-Host angeben
		 
		$account=mysql_fetch_array(mysql_query("SELECT account.ID From account WHERE loginname='$benutzername'"));
		$account_id=$account['ID'];
		
		
		// SQL: Item-name, Preis und Upgrade-Level von Tabelle Angebot abfragen
		$sql = "INSERT INTO avatar ( name , server_id , account_id ) VALUES ('$avatarname', '$server_id','$account_id');";
		
		// Antwort der Datenbank in $sql_query speichern
		mysql_query($sql);		
		?>
	<?php
	 }
	?>
	
	<h1>Eingabe der Charactere aus Flyff</h1>
	
	<!-- character-Eingabe -->
	
	<form action="Eigenes-Konto.php method="post">
		<fieldset>
		<legend>Logindaten eingeben</legend>
		<label>Charactername: <input type="text" name="name" /></label>
		
		<select name="server_id" size="2" >
    		
    			<?php
					 while ($server= mysql_fetch_array($server_query)) {
				?>
			<option value=<?=$server['ID']?>><?=$server['name']?></option>
				<?php
					 }
				?>
		</select>
	
		<input type="submit" name="formaction" value="speichern" />
    	</fieldset>
	</form>
	
	
	<?php
		$sql_avatar = "SELECT Server.name as ServerName, avatar.Name as AvatarName FROM avatar INNER JOIN account ON account.ID = avatar.account_id INNER JOIN Server ON Server.ID = avatar.server_id  WHERE loginname='$benutzername'";
		$avatar_query = mysql_query($sql_avatar);
		
	?>

	<table border=1>
		<tr>
			<td>Charactername</td>
			<td>Server</td>
		</tr>
		
	<?php
	 while ($avatar = mysql_fetch_array($avatar_query)) {
	?>
		<tr>
			<td><?=$avatar['AvatarName']?></td>
			<td><?=$avatar['ServerName']?></td>
		<?php
	 }
	?>
		</tr>
	
	</table>
	
	</body>
	
</html>