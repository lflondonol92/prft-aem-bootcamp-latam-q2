package com.aembootcamp.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, resourceType = BreadcrumbModel.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BreadcrumbModel {

    protected static final String RESOURCE_TYPE = "aem-bootcamp/components/structure/breadcrumb";

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resourceResolver;

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