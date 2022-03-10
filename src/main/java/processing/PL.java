package processing;

public class PL {
    private PL successor;

    public PL getSuccessor() {
        return successor;
    }

    public void setSuccessor(PL successor) {
        this.successor = successor;
    }

    protected boolean containsGold(String matter) {
        return matter.contains("G");
    }

    public void process(String matter) {
        if (getSuccessor() != null) getSuccessor().process(matter);
        else System.err.printf("[Processing] Cannot process %s%n", matter);
    }
}
