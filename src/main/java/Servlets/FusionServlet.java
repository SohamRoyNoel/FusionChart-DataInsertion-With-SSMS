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


public class FusionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FusionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Map<String, String> mps = new HashMap<String, String>();

		String askFusion = "select * from Customer";
		String fuckMe = "";
		String appendMe = "";
		
		String SuckMe ="";
		String fuckingChrist = "";

		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(askFusion);
			while(rs.next()) {
				//mps.put(rs.getString(1), rs.getString(2));
				fuckMe = "{ \"value\":\""+rs.getFloat(3)+"\"}";
				System.out.println(rs.getFloat(3));
				SuckMe = "{ \"label\":\""+rs.getString(2)+"\"}";
				appendMe += ","+fuckMe;
				fuckingChrist += ","+SuckMe;
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
			String modifiedCategories = "\"categories\": [{\"category\":["+fuckingChrist.substring(1)+"]}]";
			//String modifiedChart = "{type: 'scrollbar2d',renderAt: 'containerss', width: '600',height: '500',dataFormat: 'json',dataSource: { \"chart\": {plottooltext: \"$dataValue Downloads\", theme: \"fusion\"},"+modifiedCategories+","+modifiedDataset+"}}";
			String modifiedChart = "{"+chart+","+modifiedCategories+","+modifiedDataset+"}";
//			String modifiedChart = "{"+modifiedDataset+"}";


			System.out.println(modifiedChart);
			jsonx = new Gson().toJson(modifiedChart);
//			response.setContentType("text/json");
//			response.getWriter().write(modifiedChart);
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
