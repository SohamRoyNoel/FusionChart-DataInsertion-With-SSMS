package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import connectionFactory.Connections;

public class LineSeriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LineSeriesServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String askFusion(String Pgname) {
		String askFusion = "select * from PageTimeLine where PageName = '"+ Pgname +"'";
		return askFusion;
	}

	public static String askFusion(String startDate, String endDate, String custNm) {
		//String askFusion = "select name, payment from DependentTable where convert(date,Dates) between '"+startDate+"' and '"+endDate+"' and name='"+custNm+"' group by name, Payment";
		String askFusion = "select name, avg(payment) from DependentTable where convert(date,Dates) between '"+startDate+"' and '"+endDate+"' and name in ("+custNm+") group by name";

		return askFusion;
	}

	public static String askFusion1(String startDate, String endDate, String custNm) {
		//String askFusion = "select name, payment from DependentTable where convert(date,Dates) between '"+startDate+"' and '"+endDate+"' and name='"+custNm+"' group by name, Payment";
		String askFusion = "select name, avg(payment) from DependentTable where convert(date,Dates) between '"+startDate+"' and '"+endDate+"' and name in ("+custNm+") group by name";

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

		//String askMaxTimetakenCharts = "select distinct name from DependentTable";
		//		String askMaxTimetakenCharts = "select top 5 PageName as pg , Avg(TimeTaken) as avgCal from PageTimeLine group by PageName order by avgCal desc";
		//String maxPlot = "select distinct name from DependentTable";
		//String askFusion = "select * from PageTimeLine where PageName = ''";
		String fm = "";
		String appendMe = "";
		//String lokkhonRekha = "";

		String sm ="";
		String fc = "";
		String lr = "";


		String baseStartdate = "2020-04-27" ;
		String baseEndDate ="2020-04-28";

		String ActStartdate = "2020-04-29" ;
		String ActEndDate ="2020-04-30";

		String custName = "'Jack9', 'Jack13', 'Jack14'";

		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			st1 = cn.createStatement();
			//rs = st.executeQuery(askMaxTimetakenCharts);
			//while(rs.next()) {
			String lokkhonRekha = "";

			rs1 = st1.executeQuery(askFusion(baseStartdate, baseEndDate, custName));
			while(rs1.next()) {
				lr = "{ \"value\":\""+rs1.getFloat(2)+"\"}";
				lokkhonRekha += ","+lr;
				//System.out.println("Lokkhon : " + lokkhonRekha);
			}
			modlok = lokkhonRekha.substring(1);
			seriesName = "{\"seriesname\": \""+(baseStartdate+" to "+ baseEndDate)+"\", \"data\": ["+modlok+"]},";
			multiSeries += seriesName;
			
			lokkhonRekha = "";
			lr = "";
			modlok = "";
			
			rs = st.executeQuery(askFusion1(ActStartdate, ActEndDate, custName));
			while(rs.next()) {
				lr = "{ \"value\":\""+rs.getFloat(2)+"\"}";
				lokkhonRekha += ","+lr;
				//System.out.println("Lokkhon : " + lokkhonRekha);
			}
			modlok = lokkhonRekha.substring(1);
			String seriesName1 = "{\"seriesname\": \""+(ActStartdate+" to "+ ActEndDate)+"\", \"data\": ["+modlok+"]},";
			multiSeries += seriesName1;
			
			
			//}
			String newMulti = multiSeries.substring(0, multiSeries.length()-1);
			finalDataset = "\"dataset\": ["+newMulti+"]"; // data

			custName = custName.replace("'", "");
			String arr[] = custName.split(",");
			Arrays.sort(arr);
			for (String string : arr) {
				cat = "{ \"label\":\""+string+"\"}";
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

			System.out.println("XXXXXXXXXX : "+modifiedChart);
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
