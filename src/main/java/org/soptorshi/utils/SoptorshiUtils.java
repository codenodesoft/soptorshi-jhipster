package org.soptorshi.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SoptorshiUtils {
    public static Font mLiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    public static Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);
    public static Font mLiteMediumFont = new Font(Font.FontFamily.TIMES_ROMAN, 8);
    public static Font mLiteSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 7);
    public static Font mLiteSmallBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD, BaseColor.BLACK);
    public static Font mBoldMediumFont = new Font(Font.FontFamily.TIMES_ROMAN, 8f, Font.BOLD, BaseColor.BLACK);
    public static Font mSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 3f);
    public static Font mBigBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.BOLD, BaseColor.BLACK);
    public static Font mBigLiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.NORMAL, BaseColor.BLACK);



    public static String getFormattedBalance(BigDecimal pBalance) {
        String formattedCurrency = NumberFormat.getCurrencyInstance().format(pBalance);
        if(formattedCurrency.contains("(") || formattedCurrency.contains(")")) {
            formattedCurrency = formattedCurrency.replace("(", "");
            formattedCurrency = formattedCurrency.replace(")", "");
            formattedCurrency = "-" + formattedCurrency;
        }
        return formattedCurrency.replace("$", "");
    }

    private static String numberToWords(int number) {
        // if this number is 75, then this function should return seventy five
        int intQuotient = (number / 10);
        StringBuilder word = new StringBuilder();

        if(intQuotient > 0) {

            if(intQuotient == 1 && (number % 10) > 0) {
                word.append(wordInTens(number % 10));
                return word.toString();
            }
            word.append(wordInTenMultiples(intQuotient));
        }
        int remainder = number % 10;
        if(remainder > 0) {
            if(word.length() > 0) {
                word.append(" ");
            }
            word.append(numberInWords(remainder));
        }

        return word.toString();

    }

    private static String wordInTenMultiples(int number) {
        String[] words = {"Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        return words[number - 1];
    }

    private static String wordInTens(int number) {
        String words[] =
            {"Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Ninteen"};
        return words[number - 1];

    }

    private static String numberInWords(int number) {
        String[] words = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        return words[number - 1];
    }

    public static String convertNumberToWords(BigDecimal number, boolean prefix, boolean suffix) {
        String numberInWords = convertNumberToWords(number);
        if(prefix) {
            if(number.compareTo(BigDecimal.ONE) == 0) {
                numberInWords = "Taka " + numberInWords;
            }
            else {
                numberInWords = "Taka " + numberInWords;
            }
        }

        if(suffix) {
            numberInWords += " Only";
        }
        return numberInWords;
    }

    public static String convertNumberToWords(BigDecimal parameter) {

        boolean negativeAmount = false;

        if(parameter.signum() == -1) {
            negativeAmount = true;
            parameter = parameter.abs();
        }

        StringBuilder word = new StringBuilder();

        String numberString = parameter.setScale(2, RoundingMode.HALF_UP).toPlainString();

        double number = Double.parseDouble(numberString);

        int quotient = (int) (number / 10000000);
        if(quotient > 0) {
            word.append(convertNumberToWords(new BigDecimal(quotient)) + " Crore ");
        }

        number = number % 10000000;

        quotient = (int) (number / 100000);
        if(quotient > 0) {
            word.append(numberToWords(quotient) + " Lakh ");
        }
        number = number % 100000;

        quotient = (int) (number / 1000);
        if(quotient > 0) {
            word.append(numberToWords(quotient) + " Thousand ");
        }

        number = number % 1000;

        quotient = (int) (number / 100);
        if(quotient > 0) {
            word.append(numberToWords(quotient) + " Hundred ");
        }

        number = number % 100;
        if(number != 0) {
            word.append(numberToWords((int) number) + " ");
        }

        int decimal = 0;
        String val;
        if(number % 1 != 0) {
            String decimalInWords = Double.toString(number);
            int index = decimalInWords.indexOf(".");
            decimalInWords = decimalInWords.substring(index + 1);
            if(decimalInWords.length() > 2) {
                val = decimalInWords.substring(0, 2);
                decimal = Integer.parseInt(val);
                if(Integer.parseInt(decimalInWords.substring(2, 3)) > 5) {
                    decimal++;
                }
            }
            else {
                decimal = Integer.parseInt(decimalInWords);
            }
            if(decimalInWords.length() == 1) {
                decimal *= 10;
            }
            if(word.toString().trim().length() > 0) {
                word.append(("& "));
            }

            word.append(numberToWords(decimal));
            if(decimal > 1) {
                word.append(" Paise");
            }
            else {
                word.append(" Paisa");
            }
        }

        if(word.toString().trim().length() == 0) {
            return "Zero";
        }

        String result = word.toString().trim();
        if(negativeAmount) {
            result = "Minus " + result;
        }

        return result;
    }

    public static String formatDate(LocalDate date, String outputFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(outputFormat);
        return date.format(formatter);
    }

   /* public static Date convertToDate(String dateStr, String dateFormat) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat)
        Date date = formatter.parse(dateStr);
        return date;
    }*/
}