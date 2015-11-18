package org.sonarsource.escoem;

import java.io.Serializable;

import org.sonar.api.batch.Sensor;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

/**
 * The sensor executes once per analysis. It will scans the project recursively, 
 * calculating metrics values and stores the information in SonarQube database. 
 * 
 * @author onuba
 *
 */
// Not used yet
public class OnedSensor implements Sensor{

    private org.sonar.api.batch.fs.FileSystem fileSystem;
    
    /**
     * Plexus will inject the filesystem value for us
     * 
     * @param fileSystem File system access.
     */
    
    public OnedSensor(org.sonar.api.batch.fs.FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }
    
    @Override
    public boolean shouldExecuteOnProject(Project project) {
        return true;
    }

    @Override
    public void analyse(Project module, org.sonar.api.batch.SensorContext context) {
        // Do the magic, we can scan resources, calculate metrics and so on.
        Measure<Serializable> measure = new Measure<>(OnedMetrics.DANGEROUS_COMPLEXITY, 20.0);
    }
}
