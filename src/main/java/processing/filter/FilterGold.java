package processing.filter;

public class FilterGold implements IFilter {
    private final StringBuilder container = new StringBuilder();

    @Override
    public String apply(String matter) {
        StringBuilder unmatched = new StringBuilder();
        String filter = "G";
        for (Character atom : matter.toCharArray()) {
            if (filter.indexOf(atom) == -1) unmatched.append(atom);
            else container.append(atom);
        }
        return unmatched.toString();
    }

    @Override
    public String takeFiltered() {
        String filtered = container.toString();
        container.setLength(0);
        return filtered;
    }
}
