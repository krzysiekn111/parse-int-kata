package parseInt;

import java.util.Locale;

public class UtilMethods {
    protected static String[] tabelizer(String str) {
        str = str.replaceAll("-", " ").replaceAll(" and", "");
        String[] stringTable = str.split(" ");
        String[] stringTable2 = new String[stringTable.length + 1];
        stringTable2[stringTable2.length - 1] = "***"; // helps interpret input in method
        for (int i = 0; i< stringTable.length; i++) {
            stringTable2[i] = stringTable[i].concat("    ");
        }
        return stringTable2;
    }

    protected static String alternativeReadValue(String s) {

        switch (s.substring(0,6)) {
            case "twenty" : return "20";
            case "thirty" : return "30";
            case "forty " : return "40";
            case "fifty " : return "50";
            case "sixty " : return "60";
            case "sevent" : return "70";
            case "eighty" : return "80";
            case "ninety" : return "90";
        }

        return readValue(s);
    }

    protected static String readValue(String stringss) {
        if (stringss.contains("teen")) {
            switch (stringss.substring(0, 3)) {
                case "thi":
                    return "13";
                case "fou":
                    return "14";
                case "fif":
                    return "15";
                case "six":
                    return "16";
                case "sev":
                    return "17";
                case "eig":
                    return "18";
                case "nin":
                    return "19";
            }
        } else {
            switch (stringss.toLowerCase(Locale.ROOT).substring(0, 4)) {
                case "one ":
                    return "1";
                case "two ":
                    return "2";
                case "twen":
                    return "2";
                case "thre":
                    return "3";
                case "thir":
                    return "3";
                case "four":
                    return "4";
                case "fort":
                    return "4";
                case "five":
                    return "5";
                case "fift":
                    return "5";
                case "six ":
                    return "6";
                case "sixt":
                    return "6";
                case "seve":
                    return "7";
                case "eigh":
                    return "8";
                case "nine":
                    return "9";
                case "ten ":
                    return "10";
                case "elev":
                    return "11";
                case "twel":
                    return "12";
            }
        }
        return "";
    }

    protected static boolean checkIfAnotherIsNothing(String s) {
        return s.contains("***");
    }

    protected static boolean checkIfAnotherIsThousandOrHundred(String s) {
        return s.contains("hundred") || s.contains("thousand");
    }

    protected static boolean checkIfAnotherIsThousandOrNothing(String s) {
        return s.contains("thousand") || s.contains("***");
    }
}
