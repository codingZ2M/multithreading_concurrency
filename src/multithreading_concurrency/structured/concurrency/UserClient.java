package multithreading_concurrency.structured.concurrency;

import java.util.List;

// Simulated Remote User Service
// ✅ Purpose: Simulates fetching user profile data (e.g., from a REST API or DB).
public class UserClient {
	
    public UserProfile fetchProfile(String id) throws InterruptedException {
        Thread.sleep(300);
        return new UserProfile(id, "Alice", "alice@example.com");
    }
}

 class ActivityClient {
    public UserActivity fetchActivity(String id) throws InterruptedException {
        Thread.sleep(400);
        return new UserActivity(id, List.of("Login", "Post"));
    }
}

 class RecsClientA {
    public Recommendation fetch(String id) throws InterruptedException {
        Thread.sleep(500);
        return new Recommendation("A", List.of("Item1", "Item2"));
    }
}

 class RecsClientB {
    public Recommendation fetch(String id) throws InterruptedException {
        Thread.sleep(200);
        return new Recommendation("B", List.of("Item3", "Item4"));
    }
}

