package multithreading_concurrency.structured.concurrency;

// How the virtual threads and structured concurrency come together.
public class DashboardApp {
	
    public static void main(String[] args) throws Exception {
    	
        DashboardService service = new DashboardService(
            new UserClient(), new ActivityClient(),
            new RecsClientA(), new RecsClientB());

        UserDashboard dashboard = service.getDashboard("user123");
        
        // Access and print all components
        System.out.println("👤 Profile: " + dashboard.profile());
        System.out.println("📊 Activity: " + dashboard.activity());
        System.out.println("🎯 Recommendation: " + dashboard.recs());
    }
}

