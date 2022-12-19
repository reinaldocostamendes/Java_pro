<?php
	$name=addslashes($_GET['name']);
	$email=addslashes($_GET['email']);
	$phone=addslashes($_GET['phone']);
	$message=addslashes($_GET['message']);
if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
echo "<div class='alert alert-danger'>Email invalido</div>";	
}else{
enviarEmail($name,$email,$phone,$message);
echo "<div class='alert alert-success'>Mensagem enviada com sucesso!</div>";	
}
function enviarEmail($name,$email,$phone,$message){
	
	$to = $email; 
    $from = "info@reinaldocostamendes.com"; 
    $subject = "Subscrição efectuada!"; 
		     $message = '
<html> 
<style>
body{
	text-align:center;
	padding:10px;
}
#conteudo_email{
	text-align:left;
	display:inline-table;
}
</style>
  <body bgcolor="#DCEEFC"> 
  <div id="conteudo_email">
		<a href="https://reinaldocostamendes.com/"> Ir para Página</a> 
		<p>Nome: '.$name.'</p>
		<p>Email: '.$email.'</p>
		<p>Telefone: '.$phone.'</p>
     <br><br>'.$message.'.<br><br> Com os melhores cumprimentos,<br>Reinaldo Mendes<br><br>
	 <a href="https://reinaldocostamendes.com/">www.reinaldocostamendes.com</a><br><br>
	 info@reinaldocostamendes.com
	 </div>
  </body> 
</html>';
   $headers  = "From: $from\r\n"; 
    $headers .= "Content-type: text/html\r\n";
		@mail($to, $subject, $message, $headers); 
		$to ="rcm_reinas@hotmail.com";
		@mail($to, $subject, $message, $headers);
}
?>