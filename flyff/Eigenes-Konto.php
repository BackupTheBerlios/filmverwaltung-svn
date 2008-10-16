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
		
	?>
	
	
	
	</body>
	
</html>