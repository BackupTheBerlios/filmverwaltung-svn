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
			<img src="bilder/kopfleiste.jpg" alt="Logo der Item-Vermittlung Seite" />
		</p>
		
			<?php
				//menü
				include("navigation.php");
			?>
		
		<div id="hauptinhalt">
			<h1>Eingabe eines Angebots</h1>
				
				
		
		
			<?php
		
			       $serverId = $_GET['serverId'];
			
					// if (!isset($serverId)) {
			?>
			
			<p>
			 	Sie müssen schon einen Server auswählen.<br>
					
				<a href=Angebots-Eingabe.php?serverId=1>Server 1</a><br>
				<a href=Angebots-Eingabe.php?serverId=2>Server 2</a><br>
				<a href=Angebots-Eingabe.php?serverId=3>Server 3</a><br>	
			</p>
			
			<?php		
						
				//	} else {
						
			?>
					<p>
						Willkommen auf Server<?=$serverId?>
					</p>
			<?php
			
				$mysqlhost="localhost";    // MySQL-Host angeben
				$mysqluser="root";    // MySQL-User angeben
				$mysqlpwd="";		// Passwort angeben
				$mysqldb="flyff_shop";    // Gewuenschte Datenbank angeben
			
				// Anmeldung am MySQL-Server
				$connection=mysql_connect($mysqlhost, $mysqluser, $mysqlpwd);
				
				// Auswählen, welche Datenbank verwendet werden soll
				mysql_select_db($mysqldb, $connection);
				
				
				
				//Verarbeitung der Formular-Eingabe
				if (isset($_POST['Item_name'], $_POST['Preis'], $_POST['upgrade_lvl'], $_POST['Item_typ_id'], $_POST['avatar_id'])) {
				$Item_name=$_POST['Item_name'];
				$Preis=$_POST['Preis'];
				$Beschreibung=$_POST['Beschreibung'];
				$uprade_lvl=$_POST['upgrade_lvl'];
				$item_typ_id=$_POST['Item_typ_id'];
				$avatar_id=$_POST['avatar_id'];
				
						
				$sql = "INSERT INTO angebot ( Item_name , Preis , Beschreibung, upgrade_lvl, Item_typ_id, avatar_id) VALUES ( '$Item_name', '$Preis', '$Beschreibung', '$uprade_lvl', '$item_typ_id', '$avatar_id');";
				
				echo $sql;
				
				// Antwort der Datenbank in $sql_query speichern
				mysql_query($sql);		
					
			?>
			<?php
				 }
			 
				 $sql_char = "SELECT * FROM avatar;";	
				 $char_query=mysql_query($sql_char);
				 $sql_item_typ = "SELECT * FROM Item_typ;";	
			 	$item_typ_query=mysql_query($sql_item_typ);
			?>
			
			
		 <!-- Eingabe des Angebots -->
		 
		 
		 	<h2> Item-Angaben eingeben </h2>
			<p> 
				Bitte füllen Sie das Formular aus um ein Angebot ab zu geben
			</p>
			<form action=Angebots-Eingabe.php method="post">
			
				<table id="angebotabgabe">
						<tr>
							<th>Item-Name:</th>
							<td> <input type="text" name="Item_name" /> </td>
						</tr>
						<tr>
							<th>Preis:</th>
							<td> <input type="text" name="Preis" /> </td>
						</tr>
						<tr>
							<th>upgrade-Lvl:</th>
							<td> <input type="text" name="upgrade_lvl" /> </td>
						</tr>
						<tr>
							<th>Beschreibung:</th>
							<td> <textarea cols=40 rows=10 name="Beschreibung"> </textarea> </td>
						</tr>
						<tr>
							<th>Character:</th>
					    	<td> <select name="avatar_id" size="4" >
					    		
					    			<?php
										 while ($char= mysql_fetch_array($char_query)) {
									?>
								<option value=<?=$char['ID']?>><?=$char['name']?></option>
									<?php
										 }
									?>
							</select>
							</td>
						</tr>
						<tr>
							<th>Itemtyp:</th>
					    	<td>
							<select name="Item_typ_id" size="4" >
					    		
					    			<?php
										 while ($item_typ= mysql_fetch_array($item_typ_query)) {
									?>
								<option value=<?=$item_typ['ID']?>><?=$item_typ['name']?></option>
									<?php
										 }
									?>
							</select>
							<td>
						</tr>
						<tr>
							<th></th>
					    	<td>
							<input type="submit" name="formaction" value="Angebot abgeben" />
							</td>
					    </tr>	
			    	
			    </table>	
		    	
			</form>
		
		
					
		</div>
	</body>
	
</html>