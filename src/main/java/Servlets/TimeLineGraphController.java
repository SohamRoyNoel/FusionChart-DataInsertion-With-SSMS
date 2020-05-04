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

public class TimeLineGraphController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TimeLineGraphController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static String askFusion(String Pgname) {
    	String askFusion = "select * from PageTimeLine where PageName = '"+ Pgname +"'";
    	return askFusion;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		String askMaxTimetakenCharts = "select top 5 PageName as pg , Avg(TimeTaken) as avgCal from PageTimeLine group by PageName order by avgCal desc";
		String maxPlot = "select Top 1 pageName, Count(PageName) as pg , Avg(TimeTaken) as avgCal from PageTimeLine group by PageName order by pg desc";
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
			st1 = cn.createStatement();
			rs = st.executeQuery(askMaxTimetakenCharts);
			while(rs.next()) {
				String lokkhonRekha = "";
				String q = askFusion(rs.getString(1));
//				System.out.println("Q : " + q);
				rs1 = st1.executeQuery(q);
				while(rs1.next()) {
					lr = "{ \"value\":\""+rs1.getFloat(3)+"\"}";
					lokkhonRekha += ","+lr;
					//System.out.println("Lokkhon : " + lokkhonRekha);
				}
				modlok = lokkhonRekha.substring(1);
				seriesName = "{\"seriesname\": \""+rs.getString(1)+"\", \"data\": ["+modlok+"]},";
				multiSeries += seriesName;
			}
			String newMulti = multiSeries.substring(0, multiSeries.length()-1);
			finalDataset = "\"dataset\": ["+newMulti+"]"; // data
			
			rs2 = st.executeQuery(maxPlot);
			String maxElelemtCounter = "";
			while(rs2.next()) {
				maxElelemtCounter = rs2.getString(2);
				
			}
			
			for(int i=0; i<=Integer.parseInt(maxElelemtCounter); i++) {
				cat = "{ \"label\":\"Round-"+(i+1)+"\"}";
				category += ","+cat;
			}
			
			modCategory = category.substring(1);
			
			String categories = "\"categories\": [{ \"category\": ["+modCategory+"] }],"; // Labels
			
			String dataSource = "{" + 
					"\"chart\": {" + 
					"\"theme\": \"fusion\"," + 
					"\"caption\": \"\"," + 
					"\"subCaption\": \"\"," + 
					"\"xAxisName\": \"\"" + 
					"},";
			
			String modifiedChart = dataSource+categories+finalDataset+"}";

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

	}

}
