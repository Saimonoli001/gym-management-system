import java.io.Serializable;

public class RegularMember extends GymMember implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final int attendanceLimit;
    private boolean isEligibleForUpgrade;
    private String removalReason;
    private String referralSource;
    private String plan;
    private double price;
    
    public RegularMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String referralSource) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.attendanceLimit = 25;
        this.isEligibleForUpgrade = false;
        this.removalReason = "";
        this.referralSource = referralSource;
        this.plan = "basic";
        this.price = 6500.0;
    }
    
    // Accessor methods
    public int getAttendanceLimit() { return attendanceLimit; }
    public boolean isEligibleForUpgrade() { return isEligibleForUpgrade; }
    public String getRemovalReason() { return removalReason; }
    public String getReferralSource() { return referralSource; }
    public String getPlan() { return plan; }
    public double getPrice() { return price; }
    
    @Override
    public void markAttendance() {
        if (activeStatus) {
            attendance++;
            loyaltyPoints += 5;
            if (attendance >= attendanceLimit) {
                isEligibleForUpgrade = true;
            }
        }
    }
    
    public double getPlanPrice(String plan) {
        switch (plan.toLowerCase()) {
            case "basic": return 6500.0;
            case "standard": return 12500.0;
            case "deluxe": return 18500.0;
            default: return -1.0;
        }
    }
    
    public String upgradePlan(String newPlan) {
        if (!isEligibleForUpgrade) {
            return "Member is not eligible for upgrade.";
        }
        if (newPlan.equalsIgnoreCase(plan)) {
            return "Member is already on the " + newPlan + " plan.";
        }
        double newPrice = getPlanPrice(newPlan);
        if (newPrice == -1) {
            return "Invalid plan selected.";
        }
        plan = newPlan;
        price = newPrice;
        return "Plan upgraded to " + newPlan + " with price " + newPrice;
    }
    
    public void revertRegularMember(String removalReason) {
        super.resetMember();
        isEligibleForUpgrade = false;
        plan = "basic";
        price = 6500.0;
        this.removalReason = removalReason;
    }
    
    @Override
    public void display() {
        super.display();
        System.out.println("Plan: " + plan);
        System.out.println("Price: " + price);
        if (!removalReason.isEmpty()) {
            System.out.println("Removal Reason: " + removalReason);
        }
    }
    
    public static void main(String[] args) {
        RegularMember member = new RegularMember(1, "Saimon Oli", "Itahari", "9707565286", "SaimonOli781@gmail.com", "male", "2005-05-18", "2025-04-01", "Friend");
        member.display();
    }
}