package uk.ac.ebi.ddi.task.massivexmlgenerator.filters;

import uk.ac.ebi.ddi.api.readers.model.IFilter;

import java.util.ArrayList;
import java.util.List;

public class DatasetFilter<T> implements IFilter<T> {

    private List<IFilter<T>> filters = new ArrayList<>();

    public void addFilter(IFilter<T> iFilter) {
        filters.add(iFilter);
    }

    @Override
    public boolean valid(T t) {
        return filters.stream().anyMatch(x -> x.valid(t));
    }
}
