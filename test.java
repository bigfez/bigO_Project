package bigO_Project;

import java.util.*; 
import java.io.*;
public class test {
  
  public static void main(String[] args)throws FileNotFoundException { 
    //variables
    String output="";
    String line;
    ArrayList<String> bigO = new ArrayList<String>();
    int bigOIndex = 0;
    File file = new File("test.txt");
    Scanner input=new Scanner(System.in);
    
    try {
      input = new Scanner(file);
    }catch(FileNotFoundException io){
      System.out.println("Can't find file");
      System.exit(1);
    }
    
    input.nextLine();
    String[] controlValues = collectVariables(input.nextLine());
    input.nextLine();
    int[] controlValueLocation = new int[controlValues.length];

        //Checks to see if controlValues appear besides in the for loop.
    for(int i = 0; i < controlValues.length; i++) {
      controlValueLocation[i] = 100;
    }
    while(input.hasNext()) {
      line = input.nextLine();
      for (int i = 0; i < controlValues.length; i++) {
                if (controlValues[i] != null && (line.contains(controlValues[i] + "++") || line.contains(controlValues[i] + " ++") || line.contains(controlValues[i] + "+") || line.contains(controlValues[i] + " +") || line.contains(controlValues[i] + "--") || line.contains(controlValues[i] + " --") || line.contains(controlValues[i] + "-") || line.contains(controlValues[i] + " -"))) {
                  
                	if(controlValueLocation[i] == 100)
                		controlValueLocation[i] = i+bigOIndex;

                	if (bigO.get(controlValueLocation[i]) == null || !bigO.get(controlValueLocation[i]).equals("logn"))
                		bigO.set(controlValueLocation[i],"n");

                } else if (controlValues[i] != null && (line.contains(controlValues[i] + "*" + controlValues[i]) ||
                        line.contains(controlValues[i] + "/" + controlValues[i]) ||
                        line.contains(controlValues[i] + "*=" + controlValues[i]) ||
                        line.contains(controlValues[i] + "/=" + controlValues[i]))) {

                    if(controlValueLocation[i] == 100)
                        controlValueLocation[i] = i+bigOIndex;

                    if (bigO.get(controlValueLocation[i]) == null || !bigO.get(controlValueLocation[i]).equals("logn") && !bigO.get(controlValueLocation[i]).equals("n"))
                        bigO.set(controlValueLocation[i], "√n");

                } else if (controlValues[i] != null && (line.contains(controlValues[i] + "*") ||
                        line.contains(controlValues[i] + " *") || line.contains(controlValues[i] + "/") ||
                        line.contains(controlValues[i] + " /") || line.contains(controlValues[i] + "+" + controlValues[i]) ||
                        line.contains(controlValues[i] + "+=" + controlValues[i]) ||
                        line.contains(controlValues[i] + "-" + controlValues[i]) ||
                        line.contains(controlValues[i] + "-=" + controlValues[i]))) {
                  
                  if(controlValueLocation[i] == 100)
                    controlValueLocation[i] = i+bigOIndex;
                  bigO.set(controlValueLocation[i],"logn");
                }
            }

            if (line.contains("for(") || line.contains("for (")) {
                while(bigO.get(bigOIndex) != null)
                    bigOIndex++;

                for(Integer number: controlValueLocation)
                    if(number == bigOIndex-1){
                        bigO.set(bigOIndex,"*(");
                        bigO.set(bigOIndex-1,bigO.get(bigOIndex-1).substring(0,bigO.get(bigOIndex-1).length()));
                    }
            }else if(line.contains("}")){
                while(bigO.get(bigOIndex) != null)
                    bigOIndex++;

                if(!bigO.get(bigOIndex-1).equals("*(") && !bigO.get(bigOIndex-1).equals(")+")){
                    bigO.set(bigOIndex,"+");
                }else {
                    bigO.set(bigOIndex - 1,"");
                    bigO.set(bigOIndex,")+");
                }
            }

        }

        for(int i = 0;i < bigO.size(); i++)
            if (bigO.get(i) != null)
                output += bigO.get(i);

        if(output.length() > 1)
            output = "O("+output.substring(0,output.length()-1)+")";
        else
            output = "O("+output+")";
        
        System.out.println("The Big O of the text file is :");
        System.out.println(output);
        input.close();
  }

    private static String[] collectVariables(String line){
        String[] result = new String[15];

        int lineIndex = 0;
        int valueIndex = 0;

        for (int i = 0; i < line.length(); i++) {

            char a = line.charAt(i);
            if(line.length() > 1) {
                if (!Character.isAlphabetic(a) && !Character.isDigit(a)) {
                    result[valueIndex] = line.substring(lineIndex, i);
                    lineIndex = i + 1;
                    valueIndex = (valueIndex + 1) % 15;
                }

                if (i < line.length() - 1) {
                    result[valueIndex] = line.substring(lineIndex, line.length());
                }
            }else{
                result[i] = line;
            }
        }

        return result;
    }
  }