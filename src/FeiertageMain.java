import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Feiertag;

public class FeiertageMain extends Application {
	
	static int[] i= new int[5];
	static int years=2;

    public static void main(String[] args) throws SQLException  {
        
    	ArrayList<LocalDate> dates = read();
        
        determineDayOfWeek(dates,years);
        ausgabe();
        System.out.println(2020+years);
        
        
            
    }

    private static ArrayList<LocalDate> read()  {
    	final APIService apiService = new APIService();
        ArrayList<LocalDate> dates=new ArrayList<>();
		 for(int i = 0 ; i <= years ; i++)
		 {
			 apiService.addDays(dates,"Ostermontag",2020+i);
			 apiService.addDays(dates,"Christi Himmelfahrt",2020+i);
			 apiService.addDays(dates,"Pfingstmontag",2020+i);
			 apiService.addDays(dates,"Fronleichnam",2020+i); 
		 }    
        try {
        	
            File data = new File("C:\\Users\\Konrad\\Documents\\4. Klasse\\SwpDateien\\data");
            Scanner scanner = new Scanner(data);
            while(scanner.hasNextLine()){
                String s=scanner.nextLine();
                int d=Integer.parseInt(s.substring(0,s.indexOf(".")));
                s=s.substring(s.indexOf(".")+1);
                int m=Integer.parseInt(s.substring(0,s.indexOf(".")));
                s=s.substring(s.indexOf(".")+1);
                int year=Integer.parseInt(s);
                dates.add(LocalDate.of(year,m,d));
            }
            scanner.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return dates;
    }

    private static void ausgabe()
    {
        System.out.printf("MO:%2d DI:%2d MI:%2d DO:%2d FR:%2d",i[0],i[1],i[2],i[3],i[4]);
        System.out.println();
    }
    
    	 
    	   @Override
    	   public void start(Stage primaryStage) throws Exception {
    	 
    	       CategoryAxis xAxis = new CategoryAxis();
    	       xAxis.setLabel("Weekdays");
    	 
    	       NumberAxis yAxis = new NumberAxis();
    	       yAxis.setLabel("Amount");
    	 
    	       // Create a BarChart
    	       BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
    	 
    	       // Series 1 - Data of 2014
    	       XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
    	      
    	 
    	       dataSeries1.getData().add(new XYChart.Data<String, Number>("Monday", i[0]));
    	       dataSeries1.getData().add(new XYChart.Data<String, Number>("Tuesday", i[1]));
    	       dataSeries1.getData().add(new XYChart.Data<String, Number>("Wendsday", i[2]));
    	       dataSeries1.getData().add(new XYChart.Data<String, Number>("Thursdays", i[3]));
    	       dataSeries1.getData().add(new XYChart.Data<String, Number>("Friday", i[4]));
    	 
    	       // Series 2 - Data of 2015
    	      
    	       barChart.getData().add(dataSeries1);

    	 
    	       barChart.setTitle("Days");
    	 
    	       VBox vbox = new VBox(barChart);
    	 
    	       primaryStage.setTitle("JavaFX BarChart (o7planning.org)");
    	       Scene scene = new Scene(vbox, 400, 200);
    	 
    	       primaryStage.setScene(scene);
    	       primaryStage.setHeight(300);
    	       primaryStage.setWidth(400);
    	 
    	       primaryStage.show();
    	   }
    	   
    	   private static ArrayList<LocalDate> ferientage()
    	   {
    		   try {
    			   JSONObject json = new JSONObject(IOUtils.toString(new URL("https://ferien-api.de/api/v1/holidays/BY"), Charset.forName("UTF-8")));
    			   
    		   }
    		   catch(Exception e)
    		   {
    			   e.printStackTrace();
    		   }
    		  
    		   return null;
    	   }
    	   
    	   private static void determineDayOfWeek(ArrayList<LocalDate> dates, final int years) throws SQLException {
    		   final Connection connection = DatabaseManager.getInstance().getDatabaseConnection(3306, "Feiertage");
    	        for (LocalDate ld : dates) {
    	            for (int j = 0; j < years; j++) {
    	                LocalDate date = LocalDate.of(ld.getYear() + j, ld.getMonth(), ld.getDayOfMonth());
    	                DatabaseManager.getInstance().insertIntoDatabase(connection, new Feiertag(date));
    	                switch (date.getDayOfWeek()) {
    	                    case MONDAY:
    	                        i[0]++;
    	                        break;
    	                    case TUESDAY:
    	                        i[1]++;
    	                        break;
    	                    case WEDNESDAY:
    	                        i[2]++;
    	                        break;
    	                    case THURSDAY:
    	                        i[3]++;
    	                        break;
    	                    case FRIDAY:
    	                        i[4]++;
    	                        break;
    					default:
    						break;
    	                }
    	            }
    	        }   
    	    }
    
    }
