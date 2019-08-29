package uk.ac.ebi.ddi.task.massivexmlgenerator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.ddi.ddifileservice.services.IFileSystem;
import uk.ac.ebi.ddi.task.massivexmlgenerator.configuration.MassiveXmlTaskProperties;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MassiveXmlGeneratorApplication.class,
		initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(properties = {
		"massive.output_dir=/tmp/gnps/output",
		"massive.entries_per_file=10",
		"massive.prefix=GNPS-",
		"massive.database=GNPS",
		"file.provider=local"
})
public class ITMassiveXmlGeneratorApplication {

	@Autowired
	private MassiveXmlGeneratorApplication application;

	@Autowired
	private MassiveXmlTaskProperties taskProperties;

	@Autowired
	private IFileSystem fileSystem;

	@Before
	public void setUp() throws Exception {
		new File(taskProperties.getOutputDir()).mkdirs();
	}

	@Test
	public void contextLoads() throws Exception {
		application.run();
		List<String> files = fileSystem.listFilesFromFolder(taskProperties.getOutputDir());
		Assert.assertTrue(files.size() > 0);
	}

	@After
	public void tearDown() throws Exception {
		fileSystem.cleanDirectory(taskProperties.getOutputDir());
	}

}
