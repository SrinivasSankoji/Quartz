package com.lilium.quartz.cronexpression;

import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;

public class CronExpressionGenerator {

    public static void main(String[] args) {
        try {
            // Define your schedule parameters
            String minutes = "0";
            String hours = "1";
            String dayOfMonth = "?";
            String month = "?";
            String dayOfWeek = "?"; // Use '?' when specifying day of month

            // Create the cron expression
            String cronExpression = generateCronExpression(minutes, hours, dayOfMonth, month, dayOfWeek);
            System.out.println("Generated Cron Expression: " + cronExpression);

            // Validate the cron expression
            validateCronExpression(cronExpression);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static String generateCronExpression(String minutes, String hours, String dayOfMonth, String month, String dayOfWeek) {
        return String.format("%s %s %s %s %s", minutes, hours, dayOfMonth, month, dayOfWeek);
    }

    private static void validateCronExpression(String cronExpression) throws ParseException {
        // Validate the cron expression syntax
        CronExpression.validateExpression(cronExpression);

        // Optionally, you can check the next execution time
        CronExpression cron = new CronExpression(cronExpression);
        Date nextExecutionTime = cron.getNextValidTimeAfter(new Date());
        System.out.println("Next Execution Time: " + nextExecutionTime);
    }
}
