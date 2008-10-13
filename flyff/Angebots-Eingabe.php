<html>

<head>
 <title>KF-Monkeys Shop</title>
</head>

	<body>
	
		<h1>Eingabe eines Angebots</h1>
		
		
		
	<?php
	//menü
		include("navigation.php");
	?>
	
	<?php
	
	       $serverId = $_GET['serverId'];
	
			if (!isset($serverId)) {
	?>
	
	 Sie müssen schon einen Server auswählen.<br>
			
			<a href=Angebots-Eingabe.php?serverId=1>Server 1</a><br>
			<a href=Angebots-Eingabe.php?serverId=2>Server 2</a><br>
			<a href=Angebots-Eingabe.php?serverId=3>Server 3</a><br>	
				
		<?php		
				
			} else {
				
					?>
					
					Willkommen auf Server <?=$serverId?>
					
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
		if (isset($_POST['Item_name'], $_POST['Preis'], $_POST['Beschreibung'], $_POST['uprade_lvl'], $_POST['Item_typ_id'], $_POST['avatar_id'])) {
		$Item_name=$_POST['Item_name'];
		$Preis=$_POST['Preis'];
		$Beschreibungl=$_POST['Beschreibung'];
		$uprade_lvl=$_POST['uprade_lvl'];
		$item_typ_id=$_POST['Item_typ_id'];
		$avatar_id=$_POST['avatar_id'];
		
		
		$sql = "INSERT INTO angebot ( Item_name , Preis , Beschreibung, uprade_lvl, Item_typ_id, avatar_id) VALUES ( '$Item_name', '$Preis', '$Beschreibung', '$uprade_lvl', '$item_typ_id', '$avatar_id');";
		
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
	
	<form action=Angebots-Eingabe.php method="post">
		<fieldset>
		<legend>Item-Angaben eingeben</legend>
		Item-Name: <input type="text" name="Item_name" />
		Preis: <input type="text" name="Preis" />
		upgrade-Lvl: <input type="text" name="upgrade_lvl" />
		Beschreibung: <textarea cols=40 rows=10 name="Beschreibung" ></textarea>
    	<select name="avatar_id" size="4" >
    		
    			<?php
					 while ($char= mysql_fetch_array($char_query)) {
				?>
			<option value=<?=$char['ID']?>><?=$char['name']?></option>
				<?php
					 }
				?>
		</select>
		<select name="Item_typ_id" size="4" >
    		
    			<?php
					 while ($item_typ= mysql_fetch_array($item_typ_query)) {
				?>
			<option value=<?=$item_typ['ID']?>><?=$item_typ['name']?></option>
				<?php
					 }
				?>
		</select>
		<input type="submit" name="formaction" value="Angebot abgeben" />
    	
    	
    	
    	</fieldset>
	</form>
	
	
					<?php
					 }
				?>
	
	</body>
	
</html>