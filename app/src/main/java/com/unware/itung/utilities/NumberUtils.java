package com.unware.itung.utilities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.NavigableMap;
import java.util.TreeMap;


public class NumberUtils implements Serializable {

    private static volatile NumberUtils numberUtilsInstance;

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000_000L, "#M");
        suffixes.put(1_000_000_000L, "#B");
    }

    private NumberFormat nf;
    private int fractionCount;
    private boolean alwaysShowFraction;

    private NumberUtils() {
        if (numberUtilsInstance != null) {
            throw new RuntimeException("Gunakan getInstance() untuk menggunakan NumberUtils.");
        }

        if (nf == null) {
            fractionCount = 2;
            alwaysShowFraction = false;


            nf = NumberFormat.getInstance(Locale.ITALIAN);
            nf.setMaximumFractionDigits(fractionCount);
            nf.setRoundingMode(RoundingMode.HALF_EVEN);

            if (nf instanceof DecimalFormat) {
                ((DecimalFormat) nf).setDecimalSeparatorAlwaysShown(alwaysShowFraction);
            }
        }
    }

    public static NumberUtils getInstance() {
        if (numberUtilsInstance == null) {  // Create new one
            synchronized (NumberUtils.class) {

                if (numberUtilsInstance == null) numberUtilsInstance = new NumberUtils();

            }
        }

        return numberUtilsInstance;
    }

    public static CharSequence getFormattedNumber(BigDecimal number) {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);

        return nf.format(number);
    }

    public static CharSequence getNonFractionedNumberWithRoundDown(double number) {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.DOWN);

        return nf.format(number);
    }


    public CharSequence getFormattedNumber(double number) {

        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);

        if (nf instanceof DecimalFormat) {
            ((DecimalFormat) nf).setDecimalSeparatorAlwaysShown(false);
        }

        return nf.format(number);
    }

    public CharSequence getFormattedPercentageNumber(double number) {

        fractionCount = 2;
        alwaysShowFraction = false;

        nf.setMinimumFractionDigits(fractionCount);
        nf.setMaximumFractionDigits(fractionCount);

        if (nf instanceof DecimalFormat) {
            ((DecimalFormat) nf).setDecimalSeparatorAlwaysShown(alwaysShowFraction);
        }

        try {
            return nf.format(number);
        } catch (ArithmeticException aex) {
            aex.printStackTrace();
            return "0";
        }
    }

    public CharSequence getFormattedPercentageNumber(double number, boolean alwaysShowFraction) {

        fractionCount = 2;

        nf.setMinimumFractionDigits(fractionCount);
        nf.setMaximumFractionDigits(fractionCount);

        if (nf instanceof DecimalFormat) {
            ((DecimalFormat) nf).setDecimalSeparatorAlwaysShown(alwaysShowFraction);
        }

        try {
            return nf.format(number);
        } catch (ArithmeticException aex) {
            aex.printStackTrace();
            return "0";
        }
    }

    public CharSequence getFormattedCurrencyWithoutFraction(double number) {
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);

        try {
            return "Rp ".concat(nf.format(number));
        } catch (ArithmeticException arex) {
            return "Rp -";
        }
    }

    public CharSequence getFormattedCurrencyWithoutSymbolAndFraction(double number) {
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);

        try {
            return nf.format(number);
        } catch (ArithmeticException arex) {
            return "0";
        }
    }

    private CharSequence numberFormatBandarDetector(double number) {
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);

        return nf.format(number);
    }

    public String formatTrilionFromString(String value, boolean needThousand) {

        boolean isNegative = false;
        String resultConvert;

        if (value.contains("-")) {
            isNegative = true;
            value = value.replaceAll("-", "");
        }

        double convertValueDouble = Double.parseDouble(value);
        long valueResult = Math.round(convertValueDouble);

        if (needThousand) {
            resultConvert = formatNumberSymbolWithThousand(valueResult);
        } else {
            resultConvert = formatNumberSymbol(valueResult);
        }

        if (isNegative) {
            resultConvert = "-".concat(resultConvert);
        }

        return resultConvert;
    }

    public CharSequence getFormattedWithoutFraction(double number) {
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);
        if (nf instanceof DecimalFormat) {
            ((DecimalFormat) nf).setDecimalSeparatorAlwaysShown(false);
        }

        return nf.format(number);
    }

    private String formatNumberSymbol(long count) {
        if (count < 1000000) return "" + numberFormatBandarDetector(count);
        int exp = (int) (Math.log(count) / Math.log(1000000));
        return String.format(Locale.US, "%.2f%c",
                count / Math.pow(1000000, exp),
                "MBTPE".charAt(exp - 1));
    }

    private String formatNumberSymbolWithThousand(long count) {
        if (count < 1000) return "" + numberFormatBandarDetector(count);
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format(Locale.US, "%.2f%c", count / Math.pow(1000, exp), "KMBTPE".charAt(exp - 1));
    }

}
