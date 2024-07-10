package parseInt;

import java.util.Arrays;

public class Parser {

    public static int parseInt(String numStr) {

        String[] s = UtilMethods.tabelizer(numStr); // przygotowujemy tabelę do wygodnego modyfikowania
        String result = ""; // deklarowanie zmiennej do ostatecznego zwrócenia

        long hundreds = Arrays.stream(s).filter(a -> a.contains("hundred")).count();
        long thousands = Arrays.stream(s).filter(a -> a.contains("thousand")).count();
        boolean zliczone = false;
        int whereIsThousand = 0;
        int count = 0;
        String copyOfRest = "";

        for (int i = 0; i < s.length; i++) {

            if (s[i].contains("zero")) {
                result+="0"; // border case
                continue;
            }

            /*********** HUNDREDSY THOUSENTÓW ****************/
            int hundredsPosition = 0;
            for (int y = 0; y < s.length; y++) {
                if (s[y].contains("hundred")) {
                    hundredsPosition = y;
                    break;
                }
            }
            if (hundreds >= 1 && thousands == 1 && !zliczone) {
                for (int t = hundredsPosition + 1; t < s.length; t++) {
                    if (s[t].contains("thousand")) {
                        whereIsThousand = t;
                        break;
                    }
                    count++;
                    copyOfRest += s[t];
                }
                System.out.print(copyOfRest.replaceAll("    ", " ") + "|t:"+ whereIsThousand + "|");
                zliczone = true;
            }

            if (s[i].contains("hundred") && thousands == 1) {
                if (s[i+1].contains("teen")) {
                    result+=UtilMethods.readValue(s[i+1]);
                    i++;
                    continue;
                }
                System.out.print("count=" + count + "|");
                if (count == 1) {
                    if (UtilMethods.readValue(s[i]).length() == 1 ) {
                        result+= "0" + UtilMethods.readValue(s[i+1]);
                        System.out.println(result);
                        i++;
                        continue;
                    } else {
                        result+=UtilMethods.alternativeReadValue(s[i]);
                        i++;
                        continue;
                    }
                }
            }


            /************* THOUSENTY ************/
            if (s[i].contains("thousand")) {
                int countTous = 0;
                String copyOfRestThous = "";
                for (int t = i+1; t < s.length - 1; t++) {
                    copyOfRest += s[t];
                    countTous++;
                }
                if (countTous == 0) {
                    result+="000";
                    i=s.length - 1;
                    continue;
                }

                if (!copyOfRest.contains("hundred")) {
                    if (countTous == 1) {
                        if (UtilMethods.alternativeReadValue(s[i + 1]).length() == 1) {
                            result += "0";
                        }
                        result += "0" + UtilMethods.alternativeReadValue(s[i + 1]);
                        i++;
                        continue;
                    } else {
                        result += "0";
                    }
                }

            }

            if (thousands == 0 && hundreds == 0) {
                if (s.length == 2 && s[i].contains("teen")) {
                    result += UtilMethods.readValue(s[i]);
                    continue;
                }
            }


            /************* Hundredsy ***************/
            if (s[i].contains("hundred")) {
                if (UtilMethods.checkIfAnotherIsThousandOrNothing(s[i+1])) {
                    result += "00";
                    // dopisuje pierwsze dwa zera przy "five hundred thousand"
                    // lub dwa ostatnie przy "one hundred"
                } else  {
                    // dlaczego obecność "thousand" zepsułaby program?
                    String rest = "";
                    int countHund = 0;
                    for (int t = i + 1; t<s.length - 1; t++) {
                        countHund++;
                    }
                    if (countHund == 2) {
                        continue;
                    }


                    if (countHund == 1) {
                        rest=UtilMethods.alternativeReadValue(s[i+1]);
                        if (s[i+1].contains("teen")) {
                            result+=UtilMethods.readValue(s[i+1]);
                            i+=1;
                            continue;
                        } else if (UtilMethods.alternativeReadValue(s[i+1]).length() == 2){
                            result+=rest;
                            i+=1;
                            continue;
                        } else {
                            result+= "0" + UtilMethods.readValue(s[i+1]);
                            i+=1;
                            continue;
                        }
                    }
                }
                continue;
            }



            if (s[i].equals("***")) { // na tej wartości program powinien się kończyć
                if (s[i-1].contains("thousand")) {
                    result+="000"; // jednak trzeba dopisać "000" jeśli wcześniej jest "thousand"
                }
                s[i] = "";
                continue;
            }
            if (UtilMethods.checkIfAnotherIsNothing(s[i+1])) {
                if (s[i+1].contains("teen")) {
                    result+= UtilMethods.readValue(s[i]);
                    continue;
                } else {
                    result += UtilMethods.alternativeReadValue(s[i]);
                    continue;
                }
            }


            if (UtilMethods.checkIfAnotherIsThousandOrHundred(s[i+1])) { // jeśli kolejny String to "thousand" lub "hundred"
                result += UtilMethods.alternativeReadValue(s[i]) + " "; // to używamy alternatywnego "readValue"
                // alternatywność polega na tym że szukamy dziesiątek. 30, 50, 70 itp
                // jeśli switch w powyższym programie niczego nie dopasuje to return
                // uruchamia standardowe readValue.
                continue;
            } else {
                result += UtilMethods.readValue(s[i])+ " "; // standardowa wartość, standardowe readValue
            }
        }



        result = result.replaceAll("    ", " ").replaceAll("  ", " ");
        result = result.replaceAll(" *", ""); /* na sam koniec */
//        return result.length() > 0 ? result : numStr; // zmień return type z powrotem na int
        return result.length() > 0 ? Integer.parseInt(result) : Integer.parseInt(numStr);
    }


    public static void main(String[] args) {
        /*
        Źródło: https://www.codewars.com/kata/525c7c5ab6aecef16e0001a5/train/java
         */


//        System.out.println(parseInt("two hundred thousand") + " || thousand");
//        System.out.println(parseInt("two hundred thousand and one hundred") + " || thousand and one hundred");
        System.out.println(parseInt("one hundred and twenty thousand"));
        System.out.println(parseInt("one hundred and three thousand")); // nie
        System.out.println(parseInt("one hundred and three thousand and four hundred")); // nie
        System.out.println(parseInt("one hundred and three thousand and fifty six"));
        System.out.println(parseInt("one hundred and three thousand and four hundred and six"));
        System.out.println(parseInt("one hundred and twenty three thousand and four hundred and six"));
        System.out.println(parseInt("one hundred and twenty three thousand and four hundred and fifty six"));
//        System.out.println(parseInt("twenty three thousand"));
//        System.out.println(parseInt("twenty thousand"));
//        System.out.println(parseInt("two hundred thousand and three hundred"));
//        System.out.println(parseInt("two hundred thousand and three hundred thirty"));
//        System.out.println(parseInt("two hundred thousand and three hundred and three"));
//        System.out.println(parseInt("two hundred thousand and three hundred thirty three"));
//        System.out.println(parseInt("two hundred thousand and thirty"));
//        System.out.println(parseInt("two hundred thousand and thirty three"));
//        System.out.println(parseInt("five hundred thousands five hundred"));
//        System.out.println(parseInt("seven hundred eighty-three thousand nine hundred and nineteen"));
//        System.out.println(parseInt("six hundred twenty-three thousand four hundred and fifty")); // fifty jest czytane jako 5
//        System.out.println(parseInt("nine thousand nine hundred ninety-nine"));
//        System.out.println(parseInt("one hundred"));
//        System.out.println(parseInt("eighty-eight"));
//        System.out.println(parseInt("five hundred thousand"));


    }
}


/*
        for (int i = 0; i < s.length; i++) {
            if (s[i].contains("teen")) {
                switch (s[i].toLowerCase(Locale.ROOT).substring(0, 3)) {
                    case "thi":
                        result = result.concat("13");
                        break;
                    case "fou":
                        result = result.concat("14");
                        break;
                    case "fif":
                        result = result.concat("15");
                        break;
                    case "six":
                        result = result.concat("16");
                        break;
                    case "sev":
                        result = result.concat("17");
                        break;
                    case "eig":
                        result = result.concat("18");
                        break;
                    case "nin":
                        result = result.concat("19");
                        break;
                }

            } else {
                switch (s[i].toLowerCase(Locale.ROOT).substring(0, 4)) {
                    case "one ":
                        result = result.concat("1");
                        break;
                    case "two ":
                        result = result.concat("2");
                        break;
                    case "thre":
                        result = result.concat("3");
                        break;
                    case "four":
                        result = result.concat("4");
                        break;
                    case "fort":
                        result = result.concat("4");
                        break;
                    case "five":
                        result = result.concat("5");
                        break;
                    case "fift":
                        result = result.concat("5");
                        break;
                    case "six ":
                        result = result.concat("6");
                        break;
                    case "sixt":
                        result = result.concat("6");
                        break;
                    case "seve":
                        result = result.concat("7");
                        break;
                    case "eigh":
                        result = result.concat("8");
                        break;
                    case "nine":
                        result = result.concat("9");
                        break;
                    case "ten ":
                        result = result.concat("10");
                        break;
                    case "elev":
                        result = result.concat("11");
                        break;
                    case "twel":
                        result = result.concat("12");
                        break;
                    case "twen":
                        result = result.concat("20");
                        break;
                }

                //    private static int blankResultGenerator(String[] s) {
//        boolean thousands = false;
//        boolean hundred = false;
//        boolean hundredsOfThousands = false;
//        boolean hundreds = false;
//
//        for (int i = 0; i < s.length; i++) {
//            if (s[i].contains("thousand")) {
//                thousands = true;
//            }
//            if (s[i].contains("hundred") && hundred) {
//                hundreds = true;
//            }
//            if (s[i].contains("hundred")) {
//                hundred = true;
//            }
//        }
//        for (int i = 0; i < s.length; i++) {
//
//        }
//
//
//        int result = hundreds && thousands ? 6: thousands;
//
//        return result;
//    }
 */