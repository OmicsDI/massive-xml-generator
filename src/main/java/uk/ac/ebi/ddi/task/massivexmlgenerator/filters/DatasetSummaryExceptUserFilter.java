package uk.ac.ebi.ddi.task.massivexmlgenerator.filters;

import uk.ac.ebi.ddi.api.readers.model.IFilter;
import uk.ac.ebi.ddi.task.massivexmlgenerator.model.MassiveDatasetSummaryMassive;

public class DatasetSummaryExceptUserFilter<T> implements IFilter<T> {

    private String user = null;

    public DatasetSummaryExceptUserFilter(String user) {
        this.user = user;
    }

    @Override
    public boolean valid(Object object) {
        MassiveDatasetSummaryMassive dataset = (MassiveDatasetSummaryMassive) object;
        return (dataset.getUser() != null && !dataset.getUser().toLowerCase().equalsIgnoreCase(user));
    }
}
