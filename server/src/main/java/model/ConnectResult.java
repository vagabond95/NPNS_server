package model;

public class ConnectResult {
    private String pingResult;

    public String getPingResult() {
        return pingResult;
    }

    public void setPingResult(String pingResult) {
        this.pingResult = pingResult;
    }

    @Override
    public String toString() {
        return "ConnectResult{" +
                "pingResult='" + pingResult + '\'' +
                '}';
    }
}
