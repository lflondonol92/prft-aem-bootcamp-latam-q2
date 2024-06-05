package com.aembootcamp.core.models;

import com.adobe.cq.wcm.core.components.models.Breadcrumb;
import com.day.cq.wcm.api.Page;
import lombok.experimental.Delegate;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;

@Model(adaptables = SlingHttpServletRequest.class, adapters = Breadcrumb.class, resourceType = {BreadcrumbModel.RESOURCE_TYPE}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BreadcrumbModel implements Breadcrumb {

  protected static final String RESOURCE_TYPE = "aem-bootcamp/components/structure/breadcrumb";

  @ScriptVariable
  private Page currentPage;

  @Delegate @Self @Via(type = ResourceSuperType.class)
  private Breadcrumb breadcrumb;

  public Boolean isHomePage() {
    if(currentPage != null) {
      String templateName = currentPage.getTemplate().getName();
      return templateName.equals("home-page");
    }
    return false;
  }

}
