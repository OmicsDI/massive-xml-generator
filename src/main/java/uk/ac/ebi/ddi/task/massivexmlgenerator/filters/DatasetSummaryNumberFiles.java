package uk.ac.ebi.ddi.task.massivexmlgenerator.filters;

import uk.ac.ebi.ddi.api.readers.model.IFilter;
import uk.ac.ebi.ddi.task.massivexmlgenerator.model.MassiveDatasetSummaryMassive;

/**
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 09/11/15
 */
public class DatasetSummaryNumberFiles<T> implements IFilter<T> {

    @Override
    public boolean valid(Object object) {
        int numberFile = 0;
        return ((MassiveDatasetSummaryMassive) object).getFileCount() > numberFile;
    }
}
