package processing.pl;

public class PL01 extends PL {

    public PL01(PL successor) {
        setSuccessor(successor);
    }

    public void process(String matter) {
        if (containsGold(matter)) {
            System.out.println(matter.replace("K", ""));
        } else super.process(matter);
    }
}
