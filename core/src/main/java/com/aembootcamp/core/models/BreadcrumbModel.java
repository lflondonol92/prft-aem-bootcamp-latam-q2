package com.aembootcamp.core.models;

import com.adobe.cq.wcm.core.components.models.Breadcrumb;
import com.day.cq.wcm.api.Page;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import lombok.experimental.Delegate;

import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class, adapters = Breadcrumb.class, resourceType = BreadcrumbModel.RESOURCE_TYPE)
public class BreadcrumbModel implements Breadcrumb {

    protected static final String RESOURCE_TYPE = "aem-bootcamp/components/structure/breadcrumb";

    @Inject
    private Page currentPage;

    @Delegate
    @Self
    @Via(type = ResourceSuperType.class)
    private Breadcrumb breadcrumb;

    public boolean isHomePage() {
        if (currentPage != null) {
            String templateName = currentPage.getTemplate().getName();
            return templateName != null && templateName.equals("homepage");
        }
        return false;
    }
}