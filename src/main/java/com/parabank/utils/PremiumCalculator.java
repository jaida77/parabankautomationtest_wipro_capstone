package com.parabank.utils;

public class PremiumCalculator {
    /**
     * Simple deterministic premium model for demo:
     * base by type + ageFactor + coverageFactor
     */
    public static double calculate(String policyType, int age, double coverage){
        double base;
        switch (policyType.toLowerCase()){
            case "life": base = 500; break;
            case "health": base = 700; break;
            case "vehicle": base = 400; break;
            default: base = 300;
        }
        double ageFactor = (age < 30) ? 0.8 : (age <= 45 ? 1.0 : 1.4);
        double coverageFactor = Math.min(coverage / 100000.0, 10.0); // every 100k adds 1x up to 10x
        double premium = base * ageFactor + 100 * coverageFactor;
        return Math.round(premium * 100.0)/100.0;
    }
}
