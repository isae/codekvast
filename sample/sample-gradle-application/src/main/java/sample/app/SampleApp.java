package sample.app;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import sample.lib.used.Bar1;
import sample.lib.used.Bar2;
import untracked.UntrackedClass;

/**
 * @author olle.hallin@crisp.se
 */
@SuppressWarnings("UseOfSystemOutOrSystemErr")
@Slf4j
public class SampleApp {

    private int sum = 0;

    public static void main(String[] args) {
        new SampleApp().run();
    }

    @SneakyThrows(InterruptedException.class)
    public void run() {
        System.out.printf("Hello, World! from %s%n%n", getClass().getName());
        tryToLoadClass("io.codekvast.javaagent.CodekvastAgent", true);
        tryToLoadClass("org.aspectj.weaver.loadtime.Agent", true);

        measureMethodCallTrackingOverhead();

        Thread.sleep(4000L);
        new Bar1().declaredOnBar();

        Thread.sleep(4000L);
        new Bar1().declaredOnBar();

        Thread.sleep(4000L);
        new Bar1().declaredOnBar();
        new Bar2().declaredOnBar();
        new Bar2().declaredOnBar2();
    }

    private void measureMethodCallTrackingOverhead() {
        System.out.println("\nMeasuring method call tracking overhead...");

        int count = 10000000;

        // warm-up
        invokeTracked(count);
        invokeUntracked(count);
        invokeUntrackedLogged(count);

        long untrackedElapsedMillis = invokeUntracked(count);
        long untrackedLoggedElapsedMillis = invokeUntrackedLogged(count);
        long trackedElapsedMillis = invokeTracked(count);
        int overheadNanos = (int) ((trackedElapsedMillis - untrackedElapsedMillis) * 1000000d / (double) count);

        System.out.printf("Invoked a trivial untracked        method %,d times in %5d ms%n", count, untrackedElapsedMillis);
        System.out.printf("Invoked a trivial untracked logged method %,d times in %5d ms%n", count, untrackedLoggedElapsedMillis);
        System.out.printf("Invoked a trivial   tracked        method %,d times in %5d ms%n", count, trackedElapsedMillis);
        System.out.printf("Codekvast adds roughly %d ns to a method call%n", overheadNanos);

        if (overheadNanos <= 5) {
            throw new IllegalStateException("Unreasonable overhead: " + overheadNanos + ". Is Codekvast Agent correctly wired?");
        }
    }

    private long invokeUntracked(int count) {
        long startedAt = System.currentTimeMillis();
        sum = 0;
        UntrackedClass untracked = new UntrackedClass();
        for (int i = 0; i < count; i++) {
            sum += untracked.foo();
        }
        log.debug("Make sure the entire loop is not bypassed by the JIT compiler... {}", sum);
        return System.currentTimeMillis() - startedAt;
    }

    private long invokeUntrackedLogged(int count) {
        long startedAt = System.currentTimeMillis();
        sum = 0;
        UntrackedClass untracked = new UntrackedClass();
        for (int i = 0; i < count; i++) {
            sum += untracked.fooLogged();
        }
        log.debug("Make sure the entire loop is not bypassed by the JIT compiler... {}", sum);
        return System.currentTimeMillis() - startedAt;
    }

    private long invokeTracked(int count) {
        long startedAt = System.currentTimeMillis();

        sum = 0;
        TrackedClass tracked = new TrackedClass();
        for (int i = 0; i < count; i++) {
            sum += tracked.publicMethod();
        }
        log.debug("Make sure the entire loop is not bypassed by the JIT compiler... {}", sum);
        return System.currentTimeMillis() - startedAt;
    }

    private void tryToLoadClass(String className, boolean shouldBeAvailable) {
        try {
            Class.forName(className);

            String verdict = shouldBeAvailable ? "GOOD:" : "BAD: ";
            String result = shouldBeAvailable
                    ? "is unavoidable."
                    : "has leaked into my class path from -javaagent:codekvast-agent.jar";
            System.out.printf("%s %s can load class %s, which %s%n", verdict, SampleApp.class.getName(), className, result);
        } catch (ClassNotFoundException e) {

            String verdict = shouldBeAvailable ? "BAD: " : "GOOD:";
            String result = shouldBeAvailable
                    ? ", which indicates that codekvast-agent is not enabled!"
                    : ". We don't want codekvast-agent internals to leak into the application.";
            System.out.printf("%s %s cannot load class %s%s%n", verdict, SampleApp.class.getName(), className, result);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public String meaningOfLife() {
        return "42";
    }

}