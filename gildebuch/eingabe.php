
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="LANGUAGE" content="deutsch,german,DE,AT,CH">
<link type="text/css" rel="stylesheet" href="eingabe.css" />
 <title>KungFuMonkeys Gildenbuch - Eingabe</title>
</head>
<body>

	<?php
		
		$mysqlhost="localhost";    // MySQL-Host angeben
		$mysqluser="root";    // MySQL-User angeben
		$mysqlpwd="";		// Passwort angeben
		$mysqldb="gildebuch";    // Gewuenschte Datenbank angeben
		// Anmeldung am MySQL-Server
		$connection=mysql_connect($mysqlhost, $mysqluser, $mysqlpwd);
		
		// Ausw�hlen, welche Datenbank verwendet werden soll
		mysql_select_db($mysqldb, $connection);

		// Werte umschreiben
		if ($_POST['kategorie_1'] != "")
			$kategorie_1	= $_POST['kategorie_1'];
		else
			$kategorie_1	= $_POST['kategorie_1_select'];
			
			
			
			if ($_POST['bild'] != "")
			$bild			= $_POST['bild'];
		else
			$bild			= $_POST['bild_select'];
			
			
			
			if ($_POST['typ'] != "")
			$typ			= $_POST['typ'];
		else
			$typ			= $_POST['typ_select'];


		$titel			= $_POST['titel'];
		$text			= $_POST['text'];
		
		//Verarbeitung der Formular-Eingabe
		if (isset($kategorie_1, $titel, $bild, $text, $typ)) {
		
			if ($_POST['passwort'] != "hollululu")
				die("Falsches Passwort!");
		
			// SQL
			$sql = "INSERT INTO buch (kategorie_1,titel,bild,text,typ) VALUES ( '$kategorie_1', '$titel', '$bild', '$text', '$typ');";
				
			// Antwort der Datenbank in $sql_query speichern
			mysql_query($sql);		
		}
		
		
			 
		 $sql_kategorie_1 = "SELECT DISTINCT kategorie_1 FROM buch;";	
		 $kategorie_1_query=mysql_query($sql_kategorie_1);
		 $sql_bild = "SELECT DISTINCT bild FROM buch;";	
	     $bild_query=mysql_query($sql_bild);
	     $sql_typ = "SELECT DISTINCT typ FROM buch;";	
		 $typ_query=mysql_query($sql_typ);
		
	?>


	<form action="eingabe.php" method="post">
			
		<table>
				<tr>
					<th>Bezeichnung der Kategorie</th>
					<td> <input type="text" name="kategorie_1" />
						<select name="kategorie_1_select" size="4" >
					    		
					    			<?php
										 while ($_kategorie_1= mysql_fetch_array($kategorie_1_query)) {
									?>
								<option value=<?=$_kategorie_1['kategorie_1']?>><?=$_kategorie_1['kategorie_1']?></option>
									<?php
										 }
									?>
						</select>
					</td>
					<td>Bsp. Weihnachten 2008 oder Rezepte. Entweder ein alte Bezeichnung ausw�hlen oder eine neue festlegen. <br>
						Bitte nur maximal 30 Zeilen pro Seite</td>
				</tr>
				<tr>
					<th>Name des Titels</th>
					<td> <input type="text" name="titel" /> </td>
					<td>Den Namen des gedichts oder Rezoetes eintragen</td>
				</tr>
				<tr>
					<th>Inhalt der Seite</th>
					<td> <textarea cols=30 rows=10 name="text"> </textarea> </td> 
					<td>Den Inhalt des Gedichts,Rezeptes,etc eingeben. Bedenke das nur eine L�nge von ? eingegeben weden kann.</td>
				</tr>
				<tr>
					<th>Bild</th>
					<td> <input type="text" name="bild" /> 
						<select name="bild_select" size="4" >
					    		
					    			<?php
										 while ($_bild= mysql_fetch_array($bild_query)) {
									?>
								<option value=<?=$_bild['bild']?>><?=$_bild['bild']?></option>
									<?php
										 }
									?>
						</select>
					</td>
					<td>Den Dateinamnen des Bildes bitte ein geben. Dieses aber vorher in den Bilderordner verschieben. Gro� und Kleinschreibung beachten!</td>
				</tr>
				<tr>
					<th>Typ der Seite</th>
					<td> <input type="text" name="typ" />
						<select name="typ_select" size="4" >
					    		
					    			<?php
										 while ($typ= mysql_fetch_array($typ_query)) {
									?>
								<option value=<?=$typ['typ']?>><?=$typ['typ']?></option>
									<?php
										 }
									?>
						</select>
					</td>
					<td>Den Typ des Textes eintragen wie Lied,Gedicht,Rezept etc.</td>
				</tr>
				<tr>
					<th></th>
					<td>Passwort: <input type="password" name="passwort" /></td>
					<td><input type="submit" name="formaction" value="Eintrag einf�gen" /></td>
				</tr>	
					    	
			    </table>	
		    	
			</form>


</body>
</html>
		