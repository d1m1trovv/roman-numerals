package task.kodar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Enter number: ");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //Get user input and parse it into integer value
        int input = Integer.parseInt(bufferedReader.readLine());

        //Make sure the user input is valid
        if(input <= 3999 && 1 <= input){
            System.out.println("Result: " + convert(input));
        }
        else{
            System.out.println("Cannot convert. Allowed range is between 1 and 3999");
        }
    }

    public static String convert(int number){
        StringBuilder sb = new StringBuilder();

        /*Store roman numerals and their values in hashmap. This allows us to easily get the roman numeral
        we need by the value it represents*/
        LinkedHashMap<Integer, String> hashMap = new LinkedHashMap<>(){{
            put(1000, "M");
            put(500, "D");
            put(100, "C");
            put(50, "L");
            put(10, "X");
            put(5, "V");
            put(1, "I");
        }};

        //Integers List of the keys so we can get them by index
        List<Integer> keys = new ArrayList<>(hashMap.keySet());

        //Convert algorithm. The while loop stops when number is 0
        while(0 < number){
            //for loop to get through every element in the hashmap by index
            for(int i = 0; i < hashMap.size(); i++){
                //gets the current element value
                int currentValue = keys.get(i);
                int nextValue = 0;
                int previousValue = 0;

                //Make sure there is no next value after the last element
                if(i != hashMap.size() - 1){
                    nextValue = keys.get(i + 1);
                }
                //Make sure there is not previous value before the first element
                if(i != 0){
                    previousValue = keys.get(i - 1);
                }

                if(currentValue <= number){
                    //Get the first char of the number
                    String tempChar = String.valueOf(String.valueOf(number).charAt(0));

                    /*If the first char of the number is 4 (for example: 4, 40, 400), we need to subtract the
                    current value from the previous value, because we don't have roman numeral for 4, 40 and 400,
                    and we can't use the same roman numeral more than 3 times one after another. So to convert 40
                    to roman we do L-X or XL
                     */
                    if(tempChar.equals("4")){
                        //Get the roman numeral from the hashmap, which has the current value as key
                        sb.append(hashMap.get(currentValue));
                        sb.append(hashMap.get(previousValue));
                        //Subtract the subtraction of the previous value and the current value from the number
                        number -= (previousValue - currentValue);
                        //Break the loop and start over
                        break;
                    }
                    /*On the other hand, the numbers starting with 9 are made by
                    the subtraction of previous value and the next value
                     */
                    else if(tempChar.equals("9")){
                        sb.append(hashMap.get(nextValue));
                        sb.append(hashMap.get(previousValue));
                        number -= (previousValue - nextValue);
                        break;
                    }
                    //If the first char differs 4 or 9
                    else{
                        sb.append(hashMap.get(currentValue));
                        number -= currentValue;
                        break;
                    }
                }
            }
        }
        return sb.toString();
    }
}
