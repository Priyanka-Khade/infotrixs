package currencyconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class currencyconverter {

	private static final boolean JSONException = false;
	private static Set<String> favoriteCurrencies = new HashSet<>();
	private static void addfavoriteCurrencies() {
		BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
		System.out.println("enter the currency code to add to favorites:");
		try {
			String currencyCode = reader.readLine().toUpperCase();
			favoriteCurrencies.add(currencyCode);
			System.out.println(currencyCode + "Has been added to your favorites");
		}catch (IOException e) {
			System.out.println("error reding input:" + e.getMessage());
			
		}
	}
	private static void viewFavoriteCurrencies() {
		System.out.println("FavoriteCurrencies:");
		for (String currencyCode : favoriteCurrencies) {
		System.out.println(currencyCode);
			
		}
	
	}
	private static void convertCurrency() throws IOException {
		HashMap<Integer, String>currencyCode = new HashMap<Integer, String>();
		currencyCode.put(1, "USD");
		currencyCode.put(2, "CAD");
		currencyCode.put(3, "INR");
		String fromCode, toCode;
		double amount;
		
		Scanner sc = new Scanner (System.in);
		System.out.println("Welcome to the currency converter!");
		
		System.out.println("currency converting from?");
		System.out.println("1:USD(US Doller)\t2:CAD (canadian doller )\t3:INR(Indian Rupees)");
		int fromCurrencyChoice = sc.nextInt();
		fromCode = currencyCode.get (fromCurrencyChoice);
		
		System.out.println("currency converting to?");
		System.out.println("1:USD(US Doller)\t2:CAD (canadian doller )\t3:INR(Indian Rupees)");
		int toCurrencyChoice = sc.nextInt();
		toCode = currencyCode.get (toCurrencyChoice);
		
		System.out.println("amount you wish to convert?");
		amount=sc.nextDouble();
		
		sendHttpGetRequest(fromCode,toCode,amount);	
		
	}
	
	private static void sendHttpGetRequest(String fromCode, String toCode, double amount)
	
	throws IOException{
		
		String Get_URL="http://api.exchangerate.host/convert?access_key=e5c7048bbd38b0b2312052c4ec3dc2c8&from="  +fromCode + "&to=" +toCode + "&amount=" +amount;
		URL url = new URL (Get_URL);
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		httpURLConnection.setRequestMethod("GET");
		int responseCode = httpURLConnection.getResponseCode();
		
		if(responseCode == HttpURLConnection.HTTP_OK) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))){
			String inputLine;
			StringBuilder response = new StringBuilder();
			
			while ((inputLine = in.readLine()) !=null) {
				response.append(inputLine);
				
			}
			JSONObject obj = new JSONObject (response.toString());
			if(obj.has("result")) {
			double convertedAmount = obj.getDouble("result");
			System.out.println(amount + fromCode + "=" +convertedAmount + toCode);
				
			}else {
				
				System.out.println("Unable to find converted amount is response.");
			}
		}
	}else {
		
		System.out.println("Get request failed!");
	}
		
		
	}
		
		public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
       BufferedReader reader = new  BufferedReader(new InputStreamReader(System.in));
       
       while (true) {
    	   System.out.println("currency converter menu:");
    	   System.out.println("1.converter currency");
    	   System.out.println("2.Add Favorite Currency");
    	   System.out.println("3.View Favorite Currencies:");
    	   System.out.println("4.Exit");
    	   System.out.println("Enter Your Choice:");
    	   
    	   int choice = Integer.parseInt(reader.readLine());
    	   
    	   switch (choice) {
    	   case 1:
    		   convertCurrency();
    	   break;
    	   case 2:
    		   addfavoriteCurrencies();
    	   break;
    	   case 3:
    		   viewFavoriteCurrencies();
    	   break;
    	   case 4:
    		   System.out.println("Byee");
    		   System.exit(0);
    	   break;
    	   default:
    		   System.out.println("Invalid choice. Please enter valid option.");
    	   }
    	   
    	   
    	   
    	   
       }
	}
		
			
		}

			
		


