package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connectionFactory.Connections;

public class MultiSeriesBar2DChart extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MultiSeriesBar2DChart() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	/*String invst = request.getParameter("iv");
    	String grow = request.getParameter("gt");*/
    	String ss = "single";
		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Map<String, String> mps = new HashMap<String, String>();
		
		String askFusion = "select top 9 * from Investments";
		String avggroth = "select top 9 avg(CustInvestmentValue) from Investments";
		String avgFixed = "select top 9 avg(Fixed) from Investments";
		float invst = 0;
		float grow = 0;
		String fm = "";
		String appendMe = "";
		String lokkhonRekha = "";
		
		String sm ="";
		String fc = "";
		String lr = "";

		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			if(ss.contentEquals("single")) {
				System.out.println("IF");
				rs = st.executeQuery(askFusion);
				while(rs.next()) {
					fm = "{ \"value\":\""+rs.getFloat(3)+"\"}";
					sm = "{ \"label\":\""+rs.getString(2)+"\"}";
					lr = "{ \"value\":\""+rs.getString(5)+"\"}";
					appendMe += ","+fm;
					fc += ","+sm;
					lokkhonRekha += ","+lr;
				}
				rs = st.executeQuery(avggroth);
				while(rs.next()) {
					grow = rs.getFloat(1);
				}
				rs = st.executeQuery(avgFixed);
				while(rs.next()) {
					invst = rs.getFloat(1);
				}
				String modlok = lokkhonRekha.substring(1);
				//System.out.println("Lokkhon : "+ modlok);
				
				String chart = "\"chart\": {"+
	                "\"theme\": \"fusion\","+
	                "\"caption\": \"Fixed Vs Growth Ratio\","+
	                "\"subCaption\": \"\","+
	                "\"yAxisname\": \"Growth (In USD)\","+
	                "\"numberPrefix\": \"$\""+
	                
	              "}";
				
				String lines = " \"trendlines\": [{" + 
						"\"line\": [{" + 
						"\"startvalue\": \""+ invst +"\"," + 
						"\"color\": \"#5D62B5\"," + 
						"\"valueOnRight\": \"1\"," + 
						"\"displayvalue\": \"Avg. for{br}Food - "+invst+"\"" + 
						"}," + 
						"{" + 
						"\"startvalue\": \""+grow+"\"," + 
						"\"color\": \"#29C3BE\"," + 
						"\"valueOnRight\": \"1\"," + 
						"\"displayvalue\": \"Avg. for{br}Food - "+grow+"\"" + 
						"}" + 
						"]" + 
						"}]";
				
				String modifiedDataset = "\"dataset\": [{\"seriesname\": \"Growth\", \"data\":["+appendMe.substring(1)+"]}";
				String modifiedCategories = "\"categories\": [{\"category\":["+fc.substring(1)+"]}]";
				String modlok1 = "{ \"seriesName\": \"Fixed Deposite\", \"data\": [ " + modlok + "]}";
				String modifiedChart = "{"+chart+","+modifiedCategories+","+modifiedDataset+","+ modlok1 +"],"+lines+"}";
				
				//String modifiedChart = "{"+modifiedDataset+"}";


				System.out.println("modifiedChart : " + modifiedChart);
				jsonx = new Gson().toJson(modifiedChart);
//				response.setContentType("text/json");
//				response.getWriter().write(modifiedChart);
				response.setContentType("text/json");
				response.getWriter().write(jsonx);
			}else {
				System.out.println("ELSE");
				cn = Connections.getConnection();
				st = cn.createStatement();
				rs = st.executeQuery(askFusion);
				while(rs.next()) {
					//mps.put(rs.getString(1), rs.getString(2));
					fm = "{ \"value\":\""+rs.getFloat(5)+"\"}";
					sm = "{ \"label\":\""+rs.getString(2)+"\"}";
					appendMe += ","+fm;
					fc += ","+sm;
				}
				
				String chart = "\"chart\": {"+
	                "\"theme\": \"fusion\","+
	                "\"yaxisname\": \"Loading Times (MiliSeconds)\","+
	                "\"plottooltext\": \"$datavalue Downloads\","+
	                "\"showvalues\": \"1\","+
	                "\"placeValuesInside\": \"1\","+
	                "\"rotateValues\": \"1\","+
	                "\"valueFontColor\": \"#ffffff\","+
	                "\"numberprefix\": \"\","+
	                "\"numVisiblePlot\": \"15\","+
	                "\"showLabels\": \"0\","+
	                "\"labeldisplay\": \"WRAP\","+
	                "\"linethickness\": \"3\","+
	                "\"scrollheight\": \"10\","+
	                "\"flatScrollBars\": \"1\","+
	                "\"scrollShowButtons\": \"0\","+
	                "\"scrollColor\": \"#cccccc\","+
	                "\"showHoverEffect\": \"1\""+
	                
	              "}";
				
				String modifiedDataset = "\"dataset\": [{\"data\":["+appendMe.substring(1)+"]}]";
				String modifiedCategories = "\"categories\": [{\"category\":["+fc.substring(1)+"]}]";
				//String modifiedChart = "{type: 'scrollbar2d',renderAt: 'containerss', width: '600',height: '500',dataFormat: 'json',dataSource: { \"chart\": {plottooltext: \"$dataValue Downloads\", theme: \"fusion\"},"+modifiedCategories+","+modifiedDataset+"}}";
				String modifiedChart = "{"+chart+","+modifiedCategories+","+modifiedDataset+"}";
//				String modifiedChart = "{"+modifiedDataset+"}";


				//System.out.println(modifiedChart);
				jsonx = new Gson().toJson(modifiedChart);
//				response.setContentType("text/json");
//				response.getWriter().write(modifiedChart);
				response.setContentType("text/json");
				response.getWriter().write(jsonx);
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);

	}

}
