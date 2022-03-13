package processing.filter;

public interface IFilter {
    String apply(String matter);

    String takeFiltered();
}
