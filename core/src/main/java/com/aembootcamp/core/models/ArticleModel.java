package com.aembootcamp.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.wcm.api.Page;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Getter
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, resourceType = {
        ArticleModel.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleModel {

    protected static final String RESOURCE_TYPE = "aem-bootcamp/components/structure/article";

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String authorContentFragment;

    @ValueMapValue
    private String[] categories;

    @SlingObject
    private ResourceResolver resourceResolver;

    @SlingObject
    private Resource resource;

    @Inject
    private Page currentPage;

    private String authorName;

    private String authorDisplayPicture;

    private String authorBio;

    private String categoriesTagText;

    @PostConstruct
    private void init() {
        fetchTagText();
        fetchTitle();
        fetchContentFragmentData();
    }

    private void fetchTagText() {
        if (currentPage != null) {
            if (currentPage.getProperties().get("articlecategories") != null) {
                String[] tags = (String[]) currentPage.getProperties().get("articlecategories");
                categoriesTagText = String.join(", ", tags);
            }
        }
    }

    private void fetchTitle() {
        if (title == null) {
            title = currentPage.getTitle();
        }
    }

    private void fetchContentFragmentData() {
        Resource fragmentResource = resourceResolver.getResource(authorContentFragment);
        if (fragmentResource != null) {
            ContentFragment cfAuthor = fragmentResource.adaptTo(ContentFragment.class);
            if (cfAuthor != null) {
                authorName = cfAuthor.getElement("authorName").getContent();
                authorDisplayPicture = cfAuthor.getElement("authorDisplayPicture").getContent();
                authorBio = cfAuthor.getElement("authorBio").getContent();
            }
        }
    }
}
