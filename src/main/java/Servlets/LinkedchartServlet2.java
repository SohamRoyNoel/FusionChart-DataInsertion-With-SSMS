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

public class LinkedchartServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LinkedchartServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static String askFusion(String name) {
    	String askFusion = "select payment, convert(date,date) as date from DependentTable where name= '"+ name +"'"; // select page_load
    	return askFusion;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String index = request.getParameter("dt");
		String value = request.getParameter("dt1");
		System.out.println("Data : " + index);
		System.out.println("Data1 : " + value);
		
		
		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		Statement st1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Map<String, String> mps = new HashMap<String, String>();
		String modlok="";
		
		String cat = "";
		String category= "";
		String modCategory= "";
		
		String seriesName = "";
		String multiSeries = "";
		String finalDataset = "";

		//String askMaxTimetakenCharts = "select payment, date from DependentTable where name=";
//		String maxPlot = "select Top 1 pageName, Count(PageName) as pg , Avg(TimeTaken) as avgCal from PageTimeLine group by PageName order by pg desc";
		//String askFusion = "select * from PageTimeLine where PageName = ''";
		String fm = "";
		String appendMe = "";
		//String lokkhonRekha = "";
		
		String sm ="";
		String fc = "";
		String lr = "";

		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(askFusion(value));
			while(rs.next()) {
				//mps.put(rs.getString(1), rs.getString(2));
				fm = "{ \"value\":\""+rs.getFloat(1)+"\"}";
				//System.out.println(rs.getFloat(3));
				sm = "{ \"label\":\""+rs.getString(2)+"\"}";
				appendMe += ","+fm;
				fc += ","+sm;
			}
			
			String chart = "\"chart\": {" +  
					"        \"xAxisName\": \"Date\"," + 
					"        \"yAxisName\": \"Payment\"," + 
					"        \"numberSuffix\": \"$\"," + 
					"        \"lineThickness\": \"3\"," + 
					"        \"flatScrollBars\": \"1\"," + 
					"        \"scrollheight\": \"10\"," + 
					"        \"numVisiblePlot\": \"12\"," + 
					"        \"theme\": \"fusion\"" + 
					"    }";
			
			String modifiedDataset = "\"dataset\": [{\"data\":["+appendMe.substring(1)+"]}]";
			String modifiedCategories = "\"categories\": [{\"category\":["+fc.substring(1)+"]}]";
			//String modifiedChart = "{type: 'scrollbar2d',renderAt: 'containerss', width: '600',height: '500',dataFormat: 'json',dataSource: { \"chart\": {plottooltext: \"$dataValue Downloads\", theme: \"fusion\"},"+modifiedCategories+","+modifiedDataset+"}}";
			String modifiedChart = "{"+chart+","+modifiedCategories+","+modifiedDataset+"}";
//			String modifiedChart = "{"+modifiedDataset+"}";


			System.out.println("xxxxxxxxxxxxxxxxxxx" + modifiedChart);
			jsonx = new Gson().toJson(modifiedChart);
			response.setContentType("text/json");
			response.getWriter().write(jsonx);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);

		
		
		//String from = request.getParameter("from");
	}

}
