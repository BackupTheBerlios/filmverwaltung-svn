
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="LANGUAGE" content="deutsch,german,DE,AT,CH">
 <title>KungFuMonkeys Gildenbuch - Eingabe</title>
</head>
<body>
	<form action=eingabe.php method="post">
			
		<table>
				<tr>
					<th>Bezeichnung der Kategorie</th>
					<td> <input type="text" name="kategorie_1" />
						<select name="kategorie_1" size="4" >
					    		
					    			<?php
										 while ($kategorie_1= mysql_fetch_array($kategorie_1_query)) {
									?>
								<option value=?>
								</option>
									<?php
										 }
									?>
						</select>
					</td>
					<td>Bsp. Weihnachten 2008 oder Rezepte. Entweder ein alte Bezeichnung auswählen oder eine neue fstlegen</td>
				</tr>
				<tr>
					<th>Name des Titels</th>
					<td> <input type="text" name="kategorie_2" /> </td>
					<td>Den Namen des gedichts oder Rezoetes eintragen</td>
				</tr>
				<tr>
					<th>Inhalt der Seite</th>
					<td> <textarea cols=30 rows=10 name="text"> </textarea> </td> 
					<td>Den Inhalt des Gedichts,Rezeptes,etc eingeben. Bedenke das nur eine Länge von ? eingegeben weden kann.</td>
				</tr>
				<tr>
					<th>Bild</th>
					<td> <input type="text" name="bild" /> 
						<select name="bild" size="4" >
					    		
					    			<?php
										 while ($bild= mysql_fetch_array($bild_query)) {
									?>
								<option value=?>
								</option>
									<?php
										 }
									?>
						</select>
					</td>
					<td>Den Dateinamnen des Bildes bitte ein geben. Dieses aber vorher in den Bilderordner verschieben. Groß und Kleinschreibung beachten!</td>
				</tr>
				<tr>
					<th>Typ der Seite</th>
					<td> <input type="text" name="typ" />
						<select name="typ" size="4" >
					    		
					    			<?php
										 while ($typ= mysql_fetch_array($typ_query)) {
									?>
								<option value=?>
								</option>
									<?php
										 }
									?>
						</select>
					</td>
					<td>Den Typ des Textes eintragen wie Lied,Gedicht,Rezept etc.</td>
				</tr>
				<tr>
					<th></th>
					<td><input type="submit" name="formaction" value="Eintrag einfügen" /></td>
				</tr>	
			<!--		
	
					    	<td> <select name="avatar_id" size="4" >
					    		
					    			<?php
										 while ($char= mysql_fetch_array($char_query)) {
									?>
								<option value=<?=$char['ID']?>><?=$char['name']?></option>
									<?php
										 }
									?>
							</select>
					
					    -->
			    	
			    </table>	
		    	
			</form>


</body>
</html>
		