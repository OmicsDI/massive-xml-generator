package uk.ac.ebi.ddi.task.massivexmlgenerator.filters;

import uk.ac.ebi.ddi.api.readers.model.IFilter;
import uk.ac.ebi.ddi.task.massivexmlgenerator.model.MassiveDatasetSummaryMassive;

/**
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 10/11/15
 */
public class DatasetSummaryTrancheFilter<T> implements IFilter<T> {

    @Override
    public boolean valid(Object object) {
        MassiveDatasetSummaryMassive dataSetSummary = (MassiveDatasetSummaryMassive) object;
        return !dataSetSummary.getTask().toUpperCase().contains("TRANCHE") || (
                dataSetSummary.getTitle() != null
                        && !dataSetSummary.getTitle().toUpperCase().contains("TITLE HIDDEN")
                        && dataSetSummary.getInstrument() != null
                        && !dataSetSummary.getInstrument().isEmpty());
    }
}
