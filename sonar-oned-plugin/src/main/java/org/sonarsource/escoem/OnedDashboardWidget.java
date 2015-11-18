package org.sonarsource.escoem;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.Widget;

// Not used yet
@UserRole(value = {""})
@Description(value = "This is the OneD plugin awesome widget!!")
public class OnedDashboardWidget extends AbstractRubyTemplate implements Widget {

    @Override
    public String getId() {
        return "onedwidget";
    }

    @Override
    public String getTitle() {
        return "OneD widget";
    }

    @Override
    protected String getTemplatePath() {
        
        // uncomment next line for change reloading during development
        //return "c:/projects/xxxxx/src/main/resources/xxxxx/sonar/idemetadata/idemetadata_widget.html.erb";
        return "/xxxxx/sonar/idemetadata/idemetadata_widget.html.erb";
    
    }

}
