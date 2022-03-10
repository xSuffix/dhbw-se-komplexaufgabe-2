package processing.filter;

public class Filter01 implements IFilter {
    private final StringBuilder container = new StringBuilder();

    @Override
    public String apply(String matter) {
        StringBuilder unmatched = new StringBuilder();
        String filter = "AB";
        for (Character atom : matter.toCharArray()) {
            if (filter.indexOf(atom) == -1) unmatched.append(atom);
            else container.append(atom);
        }
        return unmatched.toString();
    }

    @Override
    public String getFiltered() {
        return container.toString();
    }
}
