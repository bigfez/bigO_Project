package bigO_Project;

import java.io.*;
import java.util.*;

public class BigO {
	
	
	public static void main(String[] args){
		
		System.out.println("--- BIG-O ESTIMATION PROGRAM ---\r\n");
		System.out.println("Scanning folder for files:\n");
		ArrayList<String> files = filesInFolder();
		
		
		for(String file: files){
			System.out.println(file +  " found");
		}
		System.out.println("\nChecking files for proper formatting:\r\n");
		
		files = checkFormatting(files);

		Scanner inputFile = null;
		Scanner fileName = new Scanner(System.in);
		boolean opened = true;
		for(String file : files){
			try { 
			    inputFile = new Scanner(new File(file)); 
			}
			catch (FileNotFoundException e) { 
			    System.out.println("--- File Not Found! ---");
			    opened = false;
			}
			if(opened && inputFile.hasNext()){
				String nice = Estimator(inputFile, file);
		         
			}
			else if(opened && !inputFile.hasNext()){
				System.out.println("File is empty.");
			}
			
		}
		System.out.println("\r\n\r\nAll Big-O estimates were placed into their own individual output files.");
		
	}
	
	
	public static String Estimator(Scanner inFile, String fileName) {
			
		ArrayList<String> est, ctl, loop = null;
		boolean opened = true;
		String bigO = "O(";
		est = StoreV(inFile);
		ctl = StoreV(inFile);
		loop = StoreV(inFile);
		ArrayList<String> code = StoreAll(inFile);
		inFile.close();
		ArrayList<String> oNotations = new ArrayList<String>();
		try { 
		    inFile = new Scanner(new File(fileName)); 
		} 
		catch (FileNotFoundException e) { 
		    System.out.println("--- File Not Found! ---");
		    opened = false;
		}
		if(opened && hasForLoop(code)){
			System.out.println("\nFile Name: "+ fileName);
			int lineCounter = -4;
			String estimation = "", iteration = "";
			int forCounter = 0;
			while(inFile.hasNextLine()){
				lineCounter++;
				String shit = inFile.nextLine();
				if(shit.contains("for")){
					forCounter++;
					String [] temp = shit.split("\\(", 2);
					String [] temp1 = temp[1].split("\\)\\s*\\{*"); //safest way to look for ){ (in almost all cases)
					String [] chunks = temp1[0].split(";");
					for(int i = 0; i < chunks.length; i++){
						chunks[i]=chunks[i].trim();
					}
					for(int i = 0; i < ctl.size(); i++){
						if(ctl.get(i).equals(chunks[1].substring(0,1))){
							estimation = checkEstimator(chunks[1],est);
						}
						if(ctl.get(i).equals(chunks[2].substring(0, 1))){
							iteration = checkIterator(chunks[2],loop);
							if(iteration.equals("")){
								if(loop.contains(ctl.get(i))){
									for(int j=lineCounter; j<code.size();j++){
										if(code.get(j).contains(ctl.get(i)) && j!= lineCounter){
											
											if(code.get(j).matches(ctl.get(i)+"\\s*[/*]\\s*\\=\\s*.*;")){
												iteration = "log(";
											}
										}
									}
								}
							}
							if(iteration.equals("log(")){
								if(loop.contains(ctl.get(i))){
									for(int j=lineCounter; j<code.size();j++){
										if(code.get(j).contains(ctl.get(i)) && j!= lineCounter){
											if(code.get(j).matches(ctl.get(i)+"[/*]\\="+ctl.get(i)+";")){
												iteration = "sqrt(";
											}
										}
									}
								}
							}
							oNotations.add(!iteration.equals("") ? iteration+estimation+")" : "("+iteration+estimation+")");
						}
					}
					
				}
			}
			
			//if(forCounter>1){
			bigO = "O(" + (oNotations.size()>1 ? oNotations.get(0) : "");
			if(oNotations.size()>1)
				for(int i=1;i<oNotations.size();i++){
					bigO += "*"+oNotations.get(i);
				}
			else
				bigO += (oNotations.get(0).substring(1, 2));
			bigO +=(")\r\n");
		}
		else
			bigO = bigO + "1 )";
		
		String outputFile = fileName.replace(".txt", "");
		PrintStream outStream = null;
		try { 
			outStream = new PrintStream(new File(outputFile + "_BigO.txt")); 
		} 
		catch(FileNotFoundException e) { 
			System.out.println("Error opening the file."); 
			System.exit(0); 
		} 
		System.out.println(bigO);
		outStream.println(bigO);
		outStream.close( );
		
		return bigO;
	}


	public static String checkIterator(String s, ArrayList<String> loop){
		String toReturn = "";
		if(s.matches(".*((\\-\\-)|(\\+\\+)|([+-]\\=)).*")){
			toReturn = s.endsWith(s.charAt(0) + "")? "log(" : "";
		}
		else if(s.matches(".*[/*]\\=.*")){
				toReturn = s.endsWith(s.charAt(0)+"") ? "sqrt(" : "log(";
		}
		return toReturn;

	}
	
	public static String checkEstimator(String s, ArrayList<String>est){
		int estVarCounter = 0;
		for(String estVar : est)
			if(s.contains(estVar)){
				estVarCounter++;
			}
		
		if(estVarCounter > 1){
			String est1 = s.replaceFirst(".*[<>=]=?", "");
			return est1;
		}
		else if(estVarCounter == 0){
			return "1"; //pray this is allowed
		}
		else {			
			return "n";
		}
	}
	
	
	public static ArrayList<String> StoreAll(Scanner in) {
        ArrayList<String> arr = new ArrayList<String>();
        String line = new String();
        while(in.hasNextLine()){
            line = in.nextLine();
            arr.add(line);
        }
        return arr;
        
}
	
	public static ArrayList<String> StoreV(Scanner in) {
		ArrayList<String> arr = new ArrayList<String>();
		String line = in.nextLine();
		Scanner sc = new Scanner(line); //Scans 
		while(sc.hasNext()){
			arr.add(sc.next());
		}
		sc.close();
		
		return arr;
	}
	
	public static boolean hasForLoop(ArrayList<String> lines ){
		
		for(String line : lines){
			if (line.matches(".*for\\s*\\(.*;.*;*\\)\\s*\\{*")){
				return true;
			}
		}
		return false;
	}
	
	public static int LogBase(int base, int num){
		return ((int)( Math.log(num)/Math.log(base)));
	}
	//
	public static void ForHandler(String i /* the variable to look for */, int i1 /*i's starting value*/, int n /*the comparison */, String a ){
		//;
	}	

	//Generates ArrayList of all files in folder that end with .txt
	public static ArrayList<String> filesInFolder(){
		ArrayList<String> results = new ArrayList<String>();
		File[] files = new File(System.getProperty("user.dir")).listFiles();

		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".txt")) {
				results.add(file.getName());
			}
		}
		return results;
}

	public static ArrayList<String> checkFormatting(ArrayList<String> files){
		ArrayList<String> toReturn = new ArrayList<String>();
		int forLoops = 0;
		
		for(int i=0; i<files.size(); i++){
			forLoops = 0;
			Scanner file = null;
			try {
				file = new Scanner(new File(files.get(i)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("file: " +files.get(i) + " not found");
			}
			if(file.hasNextLine()&& !file.nextLine().isEmpty()){//not empty.. has at least one line / estimate variable section not empty
					if(file.hasNextLine() && !file.nextLine().isEmpty()){ //Control variable line exists and is not empty
						if(file.hasNextLine() && !file.nextLine().isEmpty()){//Variables in loop body line exists and is not empty
							int braceCounter = 0;
							while(file.hasNextLine()){
								String line = file.nextLine();
								line = line.replaceAll("\\s","");
								if(line.matches(".*for\\s*\\(.*;.*;*\\)\\s*\\{*") && file.hasNextLine()){
									if(line.contains("{")){
										braceCounter += line.length()-line.replace("{","").length(); //amount of times { shows up
									}

									forLoops += 1;
								}
								else if(line.contains("{") || line.contains("}")){
									braceCounter += line.length()-line.replace("{","").length(); //amount of times { shows up
									braceCounter -= line.length()-line.replace("}","").length(); //amount of times { shows up
								}
							}
							if(forLoops >0 ){
								System.out.print(files.get(i) + " has " + forLoops + " for statement(s)...");
								if(braceCounter==0){
									System.out.print(" and has the proper amount of brackets.\r\nTherefore it is formatted properly.\r\n\r\n");
									toReturn.add(files.get(i));
								}
								else{
									System.out.print(" but it doesn't have the proper amount of brackets.\r\nTherefore it is not formatted properly.\r\n\r\n");
								}
							}
							else{
								System.out.println("no for loops found.");
							}
						}
					}
				}
				
		}
		
		return toReturn;
	}
}