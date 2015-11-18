package org.sonarsource.escoem;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.BatchExtension;
import org.sonar.api.Properties;
import org.sonar.api.SonarPlugin;

@Properties({
		@org.sonar.api.Property(key = "sonar.escoem.oned.complexity.threshold", name = "ComplexityThreshold", description = "File Complexity Threshold", defaultValue = "20.0"),
		@org.sonar.api.Property(key = "sonar.escoem.oned.blocker.violation.threshold", name = "BlockerViolationThreshold", description = "Blocker Violations Threshold", defaultValue = "0.0"),
		@org.sonar.api.Property(key = "sonar.escoem.oned.critical.violation.threshold", name = "CriticalViolationThreshold", description = "Critical Violations Threshold", defaultValue = "1.0")})
public final class OnedPlugin extends SonarPlugin {
	
    public static final String COMPLEXITY_THRESHOLD = "sonar.escoem.oned.complexity.threshold";
    public static final String BLOCKER_THRESHOLD = "sonar.escoem.oned.blocker.violation.threshold";
    public static final String CRITICAL_THRESHOLD = "sonar.escoem.oned.critical.violation.threshold";

	public List<Class<? extends BatchExtension>> getExtensions() {
		
	    return Arrays.asList(OnedMetrics.class,
	            OnedSensor.class,
		        OnedFileDecorator.class); 
		        //OnedComplexDecorator.class);
		        //OnedDashboardWidget.class);
	}
}
