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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
     
      <script>
    $( document ).ready(function() {
    	
    	 $('#btn').click(function(event) {
    		 
    		 var invst = $("input#ivn").val();
             var grow = $("input#grth").val();
    		 
      	    $.get('MultiSeriesBar2DChart', {
					iv : invst, gt : grow, 
      	    }, function(response) {
      	    	//console.log(JSON.parse(response));
      	    	var x = response;
      	    	console.log(x);
      	    	var topStores='';
      	    	FusionCharts.ready(function(x) {
      	    		alert("How low");
      	    		//var FusionCharts = require('fusioncharts');  
      	    		var xx = x;
      	    		console.log(xx);
      	    		var revenueChart = new FusionCharts({
                      type: 'msbar2d',
                      renderAt: 'charter-container',
                      width: '600',
                      height: '500',
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

 
 
 
 
<!--  <div class="container-fluid">
  <div class="row">
    <div class="col-sm-4" style="background-color:lavender;">.
	<div class="form-group">
    <label for="exampleInputEmail1">Email address</label>
    <input type="text" class="form-control" id="ivn" aria-describedby="emailHelp" placeholder="Enter email">
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Password</label>
    <input type="text" class="form-control" id="grth" placeholder="Password">
  </div> -->
  <button type="submit" id="btn" class="btn btn-primary">Submit</button>
<!-- </div>
    <div class="col-sm-4" style="background-color:lavenderblush;"> -->
<div id="charter-container">FusionCharts will render here</div>
<!--</div>
  </div>
</div> -->



  
</body>
</html>