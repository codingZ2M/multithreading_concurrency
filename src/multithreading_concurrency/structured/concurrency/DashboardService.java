package multithreading_concurrency.structured.concurrency;

import java.util.concurrent.StructuredTaskScope;

/*
  ✅ Purpose:The core service that builds the final UserDashboard by concurrently:
  		Fetching user profile (UserClient)
		Fetching user activity (ActivityClient)
		Fetching recommendations (RecsServiceA and RecsServiceB)
		
		🧠 Key Benefits of Structured Concurrency Here:
				All 4 subtasks (2 recommendations, 1 profile, 1 activity) are launched in parallel.
				If any task fails, the scope shuts down the rest		
				It ensures all results are ready before constructing the dashboard.
 */
public class DashboardService {
	
    private final UserClient userClient;
    private final ActivityClient activityClient;
    private final RecsClientA recsA;
    private final RecsClientB recsB;

    public DashboardService(UserClient u, ActivityClient a, RecsClientA ra, RecsClientB rb) {
        this.userClient = u;
        this.activityClient = a;
        this.recsA = ra;
        this.recsB = rb;
    }

    public UserDashboard getDashboard(String userId) throws Exception {
    	
    	/*
    	  StructuredTaskScope allows you to run multiple related tasks in parallel (usually on virtual threads) 
    	  and manage them as a group with: automatic cancellation, exception handling, and guaranteed cleanup.
    	 */
    	//  'ShutdownOnFailure' flavor of StructuredTaskScope with a specific cancellation policy. Cancels all tasks if any one fails (throws, error, etc.)
    	// Perfect for use cases like fetching multiple things (e.g. user info, recommendations, etc.) — where if any one fails, the entire operation should fail.
    	// Scope is a structured concurrency block
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
        	
        	// Implicitly using virtual threads to run each task you fork()
        	// spins up a new virtual thread behind the scenes for fetchProfile(...), fetchActivity(...)
        	// Can run efficiently on virtual threads (doesn’t block platform threads).
            var userTask     = scope.fork(() -> userClient.fetchProfile(userId));
            var activityTask = scope.fork(() -> activityClient.fetchActivity(userId));

            // race: only fastest recommendation
            var recA = scope.fork(() -> recsA.fetch(userId));
            var recB = scope.fork(() -> recsB.fetch(userId));
            
            // first of recA or recB finishes →
            // Wait and retrieve results
            scope.join(); // wait for all tasks
            scope.throwIfFailed();

            var rec = recA.state() == StructuredTaskScope.Subtask.State.SUCCESS
                    ? recA.get()
                    : recB.get();

            return new UserDashboard(userTask.get(), activityTask.get(), rec);
        }
    }
}

