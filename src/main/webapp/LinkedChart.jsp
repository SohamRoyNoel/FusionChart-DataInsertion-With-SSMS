<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%@page import="com.google.gson.*" %>

<html>
<head>
	<head>
<script type="text/javascript" src="resources/js/fusioncharts.js"></script>
<script>
	var plotIndex = 0;
	var plotValue = '';
</script>
<script type="text/javascript"
	src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.theme.fusion.js"></script>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
      <script>
    $( document ).ready(function() {
    	
    	 $('#btn').click(function(event) {
      	    $.get('LinkedChartServlet', {

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
                      ,events: {
                          dataPlotClick: function(ev, props) {
                              var infoElem = document.getElementById("infolbl");
                              
                              //console.log(props.categoryLabel);
                              plotIndex = props.dataIndex;
                              plotValue = props.categoryLabel;
                              var dataStrings = plotIndex + "," + plotValue; 
                              console.log("Val : "+ dataStrings);
                              
                              $.ajax({
                            	    type: "POST",
                            	    url: "LinkedchartServlet2",
                            	    data: { dt : plotIndex, dt1 : plotValue },
                            	    success: function(data) {
                            	    	//console.log(JSON.parse(response));
                              	    	var x = data;
                              	    	console.log(x);
                              	    	var topStores='';
                              	    	FusionCharts.ready(function(x) {
                              	    		//var FusionCharts = require('fusioncharts');  
                              	    		var xx = x;
                              	    		console.log(xx);
                              	    		var revenueChart1 = new FusionCharts({
                                              type: 'scrollline2d',
                                              renderAt: 'charters-container',
                                              width: '1180',
                                              height: '330',
                                              dataFormat: 'json',
                                              dataSource: data                   
                                              
                              	    }).render();
                                  });
                            	    }
                            	});
                           }
                        }
                      
      	    }).render();
          });
    });
    	 });
    });
    	 
    //console.log("Plot Index : " + plotIndex);
    //console.log("Plot Value : " + plotValue);
    </script>
</head>
</head>
<body>
<button type="submit" id="btn">Click Me</button>
<script>


</script>
<div id="charter-container">
	<div class="controller">
      <label id="infolbl" class="p">Click on a column to know more.</label>
   </div>
</div>
<div id="charters-container">
	
</div>
</body>
</html>
