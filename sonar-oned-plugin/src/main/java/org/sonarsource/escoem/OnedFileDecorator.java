package org.sonarsource.escoem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorBarriers;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;

/**
 * Executes once per resource, after all sensor are executed. Can query for existing values o calculate
 * new derived values and store them in sonarqube database. 
 * 
 * @author escoem
 *
 */
// Run the decorator after the issues are tracked
@DependsUpon(value=DecoratorBarriers.ISSUES_TRACKED)
public class OnedFileDecorator implements Decorator {
	private final double complexityThreshold;
	private final double blockerThreshold;
	private final double criticalThreshold;
	private static final Logger LOG = LoggerFactory.getLogger(OnedFileDecorator.class);
	
	public OnedFileDecorator(Settings settings) {
		this.complexityThreshold = settings.getFloat(OnedPlugin.COMPLEXITY_THRESHOLD).floatValue();
		this.blockerThreshold = settings.getFloat(OnedPlugin.BLOCKER_THRESHOLD).floatValue();
		this.criticalThreshold = settings.getFloat(OnedPlugin.CRITICAL_THRESHOLD).floatValue();
	}

	public boolean shouldExecuteOnProject(Project project) {
		return true;
	}

	public void decorate(Resource resource, DecoratorContext context) {
		
	    // Avoid to decorate unit test entities.
	    if (ResourceUtils.isEntity(resource) && !ResourceUtils.isUnitTestClass(resource)) {
	        LOG.debug("Oned resource: " + resource.getLongName());
		    
	        final Measure<?> complexity = context.getMeasure(CoreMetrics.COMPLEXITY);
	        // TODO change to Blocker
	        final Measure<?> blockerViolations = context.getMeasure(CoreMetrics.MAJOR_VIOLATIONS);
	        // TODO change to Critical
	        final Measure<?> criticalViolations = context.getMeasure(CoreMetrics.MINOR_VIOLATIONS);
	        
	        LOG.debug("Oned complexity " + complexity.getValue());
	        LOG.debug("Oned blocker violations " + blockerViolations.getValue());
	        LOG.debug("Oned critical violations " + criticalViolations.getValue());
	        
	        final double value = complexity.getValue().doubleValue();
	        // Cannot add twice the same measure, so keep in isDangerous if we have to mark the resource as dangerous
	        boolean isDangerous = false;
	        
	        if (value > this.complexityThreshold) {
	            isDangerous = true;
	            
	            context.saveMeasure(OnedMetrics.DANGEROUS_COMPLEXITY, Double.valueOf(value));
	            context.saveMeasure(OnedMetrics.DANGEROUS_FILE_COMPLEXITY, Double.valueOf(value));
	        }
	        
	        if (blockerViolations.getValue() > blockerThreshold) {
	            isDangerous = true;
	        }
	        
	        if (criticalViolations.getValue() > criticalThreshold) {
	            isDangerous = true;
	        }
	        
	        if (isDangerous) {
	            context.saveMeasure(OnedMetrics.DANGEROUS_FILES, Double.valueOf(1.0D));
	        }
		}
	}
}
