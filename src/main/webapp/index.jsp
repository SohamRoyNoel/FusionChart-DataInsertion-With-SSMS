<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%@page import="com.google.gson.*" %>

<html>
<head>
	<head>
<script type="text/javascript" src="resources/js/fusioncharts.js"></script>

<script type="text/javascript"
	src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.theme.fusion.js"></script>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
      <script>
    $( document ).ready(function() {
    	
    	 $('#btn').click(function(event) {
      	    $.get('FusionServlet', {

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
                      type: 'scrollbar2d',
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
</head>
<body>
<button type="submit" id="btn">Click Me</button>
<script>


</script>
<div id="charter-container">FusionCharts will render here</div>

</body>
</html>
