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

public class LokkhonRekhaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LokkhonRekhaController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonx = null;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		Map<String, String> mps = new HashMap<String, String>();

		String askFusion = "select * from Investments";
		String fm = "";
		String appendMe = "";
		String lokkhonRekha = "";
		
		String sm ="";
		String fc = "";
		String lr = "";

		try {
			cn = Connections.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(askFusion);
			while(rs.next()) {
				fm = "{ \"value\":\""+rs.getFloat(5)+"\"}";
				sm = "{ \"label\":\""+rs.getString(2)+"\"}";
				lr = "{ \"value\":\""+rs.getString(3)+"\"}";
				appendMe += ","+fm;
				fc += ","+sm;
				lokkhonRekha += ","+lr;
			}
			String modlok = lokkhonRekha.substring(1);
			//System.out.println("Lokkhon : "+ modlok);
			
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
                "\"showLabels\": \"1\","+
                "\"labeldisplay\": \"WRAP\","+
                "\"linethickness\": \"3\","+
                "\"scrollheight\": \"10\","+
                "\"flatScrollBars\": \"1\","+
                "\"scrollShowButtons\": \"0\","+
                "\"scrollColor\": \"#cccccc\","+
                "\"showHoverEffect\": \"1\""+
                
              "}";
			
			String modifiedDataset = "\"dataset\": [{\"data\":["+appendMe.substring(1)+"]}";
			String modifiedCategories = "\"categories\": [{\"category\":["+fc.substring(1)+"]}]";
			String modlok1 = "{ \"seriesName\": \"Projected Revenue\", \"renderAs\": \"line\", \"showValues\": \"0\", \"data\": [ " + modlok + "]}";
			String modifiedChart = "{"+chart+","+modifiedCategories+","+modifiedDataset+","+ modlok1 +"]}";
			
			//String modifiedChart = "{"+modifiedDataset+"}";


			System.out.println("modifiedChart : " + modifiedChart);
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
