package interview;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

/**
 * Implement an application-level circuit breaker
 *
 * interface CircuitBreaker {
 *
 * <T> T execute(Supplier<T> supplier)
 *
 * }
 *
 * Requirements:
 *
 * Count errors within a given time period, if the number of errors in the period exceeds or reaches a configurable level then the circuit breaker should open.
 *
 * When the circuit breaker is open, this means that all executions should fail fast.
 *
 * After a configurable period of time, the circuit breaker should close. This means that executions will no longer fail fast.
 */
public class CircuitBreaker {

    private final int windowLag;
    private final int noOfErrrorsAllowed;
    private final int secondsToStayOpen;
    private int noOfErrors = 0;

    private Instant timeStarted;
    private boolean isClosed = true;

    public CircuitBreaker(int windowLag, int noOfErrrors, int secondsToStayOpen) {
        this.windowLag = windowLag;
        this.noOfErrrorsAllowed = noOfErrrors;
        this.secondsToStayOpen = secondsToStayOpen;
    }

    <T> T execute(Supplier<T> supplier) {

        if (Instant.now().isAfter(timeStarted.plus(secondsToStayOpen, ChronoUnit.SECONDS))) {
            isClosed = true;
            noOfErrors = 0;
        }

        // check state of breaker
        if (!isClosed) {
            return null;
        }

        T t = supplier.get();

        if (t == null) {
            noOfErrors += 1;
        }
        if (noOfErrors >= noOfErrrorsAllowed) {
            isClosed = false;
            timeStarted = Instant.now();
        }

        return t;
    }
}
