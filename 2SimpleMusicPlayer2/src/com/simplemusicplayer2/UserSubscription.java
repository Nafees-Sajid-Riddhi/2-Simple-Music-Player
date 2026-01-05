package com.simplemusicplayer2;

public class UserSubscription {
    private String subscriptionId;
    private String planType;
    private double price;
    private String startDate;
    private String endDate;

    public UserSubscription(String subscriptionId, double price) {
        this.subscriptionId = subscriptionId;
        this.price = price;
        this.planType = "Basic"; // Default plan type
        this.startDate = "2025-03-01"; // Sample start date
        this.endDate = "2025-09-01"; // Sample end date
    }

    public void beginSubscription() {
        System.out.println("Subscription started for: " + planType + 
                           " (ID: " + subscriptionId + 
                           ", Price: $" + price + 
                           ", Start Date: " + startDate + 
                           ", End Date: " + endDate + ")");
    }

    public void renewSubscription() {
        System.out.println("Subscription renewed for: " + planType + 
                           " (ID: " + subscriptionId + 
                           ", Price: $" + price + 
                           ", Start Date: " + startDate + 
                           ", End Date: " + endDate + ")");
    }

    public void cancelSubscription() {
        System.out.println("Subscription canceled for: " + planType + 
                           " (ID: " + subscriptionId + 
                           ", Price: $" + price + 
                           ", Start Date: " + startDate + 
                           ", End Date: " + endDate + ")");
    }
}