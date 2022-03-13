package processing.filter;

public abstract class Filter implements IFilter {
    private final StringBuilder container = new StringBuilder();
    private final String filterSetting;

    public Filter(String filterSetting) {
        this.filterSetting = filterSetting;
    }

    @Override
    public String apply(String matter) {
        StringBuilder unmatched = new StringBuilder();
        for (Character atom : matter.toCharArray()) {
            if (filterSetting.indexOf(atom) == -1) unmatched.append(atom);
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

    @Override
    public String getSetting() {
        return filterSetting;
    }
}
