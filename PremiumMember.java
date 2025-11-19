import java.io.Serializable;

public class PremiumMember extends GymMember implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final double premiumCharge;
    private String personalTrainer;
    private boolean isFullPayment;
    private double paidAmount;
    private double discountAmount;
    
    public PremiumMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.premiumCharge = 50000.0;
        this.personalTrainer = personalTrainer;
        this.isFullPayment = false;
        this.paidAmount = 0.0;
        this.discountAmount = 0.0;
    }
    
    public double getPremiumCharge() { return premiumCharge; }
    public String getPersonalTrainer() { return personalTrainer; }
    public boolean isFullPayment() { return isFullPayment; }
    public double getPaidAmount() { return paidAmount; }
    public double getDiscountAmount() { return discountAmount; }
    
    @Override
    public void markAttendance() {
        if (activeStatus) {
            attendance++;
            loyaltyPoints += 10;
        }
    }
    
    public String payDueAmount(double amount) {
        if (isFullPayment) {
            return "Payment already completed.";
        }
        if (paidAmount + amount > premiumCharge) {
            return "Amount exceeds premium charge. Maximum allowed: " + (premiumCharge - paidAmount);
        }
        paidAmount += amount;
        if (paidAmount == premiumCharge) {
            isFullPayment = true;
        }
        double remaining = premiumCharge - paidAmount;
        return "Payment successful. Remaining amount: " + remaining;
    }
    
    public void calculateDiscount() {
        if (isFullPayment) {
            discountAmount = 0.10 * premiumCharge;
            System.out.println("Discount calculated: " + discountAmount);
        } else {
            System.out.println("No discount available. Full payment not made.");
        }
    }
    
    public void revertPremiumMember() {
        super.resetMember();
        personalTrainer = "";
        isFullPayment = false;
        paidAmount = 0.0;
        discountAmount = 0.0;
    }
    
    @Override
    public void display() {
        super.display();
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: " + paidAmount);
        System.out.println("Full Payment: " + isFullPayment);
        System.out.println("Remaining Amount: " + (premiumCharge - paidAmount));
        if (isFullPayment) {
            System.out.println("Discount Amount: " + discountAmount);
        }
    }
}