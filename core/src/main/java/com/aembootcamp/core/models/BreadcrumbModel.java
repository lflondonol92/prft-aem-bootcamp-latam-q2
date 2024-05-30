package com.aembootcamp.core.models;

import com.adobe.cq.wcm.core.components.models.Breadcrumb;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import lombok.experimental.Delegate;

@Model(adaptables = SlingHttpServletRequest.class, adapters = Breadcrumb.class, resourceType = BreadcrumbModel.RESOURCE_TYPE)
public class BreadcrumbModel implements Breadcrumb {

  protected static final String RESOURCE_TYPE = "aem-bootcamp/components/structure/breadcrumb";

  @SlingObject
  private Resource currentResource;

  @SlingObject
  private ResourceResolver resourceResolver;

  @Delegate
  @Self
  @Via(type = ResourceSuperType.class)
  private Breadcrumb breadcrumb;

  public boolean isHomePath() {
    PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
    if (pageManager != null) {
      Page currentPage = pageManager.getContainingPage(currentResource);
      if (currentPage != null) {
        String pagePath = currentPage.getPath();
        return pagePath != null && pagePath.endsWith("/home");
      }
    }
    return false;
  }
}