function tempo1(){
	let inf1 ="";
	   let inf2 ="";
	   let inf3 ="";
	 //  alert(inf1 + " "+inf2+" "+inf3);
	var cidade = document.getElementById("cidade1").value;
	 $.getJSON("http://api.weatherapi.com/v1/current.json?key=6847ed99fbc84d8098593123211908&q="+cidade+"&lang=PT", function(result){
      $.each(result, function(i, field){
	   if(i=="location"){
		  inf1 =field.name;
		 inf2 =field.country;
		   }
		   if(i=="current"){
			   inf3 =field.temp_c+"&ordm;C";  
			//  $("div").append(inf1 + " "+inf2+" "+inf3);
			   }
      
		 $("#info_tempo1").html("<span>"+inf1 + " "+inf2+" "+inf3+"</span>");
      });
    });
}
function tempo2(){
	 let inf_1_ = "";
	 let inf_2_ = "";
	 let inf_3_ = "";  
	 let inf_4_ = "";
	 let inf_5_ = "";
	var cp = document.getElementById("cidade3").value;
	//$("#info_tempo3").html("<h3>CIDADE: "+cp+"</h3><br>");	
	//alert(cp);
	$.getJSON("http://api.weatherapi.com/v1/current.json?key=6847ed99fbc84d8098593123211908&q="+cp+"&lang=PT", function(result){
		 $.each(result, function(i, field){
	 if(i=="location"){
	 inf_1_ = field.name;
	   inf_2_ = field.country;
	   inf_4_ =field.localtime;
		   }
		   if(i=="current"){
			    inf_3_ =field.temp_c+"&ordm;C";
				inf_5_ =field.condition.text;
			//  $("div").append(inf1 + " "+inf2+" "+inf3);
			   }
			 
		$("#info_tempo3").html("<span>"+inf_1_ + "  "+inf_2_+"  "+ inf_3_+"  "+inf_4_+"  "+inf_5_+"</span><br>");
      });
    });	
	/*<a href="http://api.weatherapi.com/v1/forecast.json?key=6847ed99fbc84d8098593123211908&q=07112&days=7">JSON</a>*/
}
function tempo3(){
		var cidade2 = document.getElementById("cidade2").value;
	 $.getJSON("http://api.weatherapi.com/v1/search.json?key=6847ed99fbc84d8098593123211908&q="+cidade2+"&lang=PT", function(result){
      $.each(result, function(i, field){
	  const inf_1 = field.name;
	  const inf_2 = field.country; 
		$("#info_tempo2").append("<span>"+inf_1 + " "+inf_2+"</span><br>");
      });
    });
	/*<a href="http://api.weatherapi.com/v1/search.json?key=6847ed99fbc84d8098593123211908&q=lond">JSON</a>*/
}

