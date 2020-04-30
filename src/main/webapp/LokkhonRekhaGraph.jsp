<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="resources/js/fusioncharts.js"></script>

<script type="text/javascript"
	src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.theme.fusion.js"></script>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
      <script>
    $( document ).ready(function() {
    	
    	 $('#btn').click(function(event) {
      	    $.get('LokkhonRekhaController', {

      	    }, function(response) {
      	    	//console.log(JSON.parse(response));
      	    	var x = response;
      	    	console.log(x);
      	    	var topStores='';
      	    	FusionCharts.ready(function(x) {
      	    		//var FusionCharts = require('fusioncharts');  
      	    		var xx = x;
      	    		console.log(xx);
      	    		var revenueChart = new FusionCharts({
                      type: 'scrollcombi2d',
                      renderAt: 'charter-container',
                      width: '1180',
                      height: '330',
                      dataFormat: 'json',
                      dataSource: response                   
                      
      	    }).render();
          });
    });
    	 });
    });
    	 
   
    </script>
</head>
<body>
<button type="submit" id="btn">Click Me</button>
<script>


</script>
<div id="charter-container">FusionCharts will render here</div>
</body>
</html>