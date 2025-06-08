package b.attack.simulator;

public class BandwidthTracker {
    private long sentBytes = 0;
    private long receivedBytes = 0;

    public synchronized void addSent(long bytes) {
        sentBytes += bytes;
    }

    public synchronized void addReceived(long bytes) {
        receivedBytes += bytes;
    }

    public long getSentBytes() {
        return sentBytes;
    }

    public long getReceivedBytes() {
        return receivedBytes;
    }
}
