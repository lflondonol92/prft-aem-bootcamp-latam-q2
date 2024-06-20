package com.aembootcamp.core.models;

import com.adobe.cq.dam.cfm.ContentElement;
import lombok.AccessLevel;
import lombok.Getter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.wcm.api.Page;

import java.util.Optional;

import javax.annotation.PostConstruct;

@Getter
@Model(adaptables = { SlingHttpServletRequest.class }, resourceType = {
        ArticleModel.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleModel {

    protected static final String RESOURCE_TYPE = "aem-bootcamp/components/structure/article";

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String description;

    @Getter(AccessLevel.NONE)
    @ValueMapValue
    private String authorContentFragment;

    @SlingObject
    private ResourceResolver resourceResolver;

    @ScriptVariable(injectionStrategy = InjectionStrategy.REQUIRED)
    private Page currentPage;

    private String authorName;

    private String authorDisplayPicture;

    private String authorBio;

    private String categoriesTagText;

    @PostConstruct
    private void init() {
        fetchTagText();
        fetchContentFragmentData();
    }

    private void fetchTagText() {
        Optional<String[]> articleCategories = Optional
                .ofNullable((String[]) currentPage.getProperties().get("articlecategories"));
        categoriesTagText = articleCategories.map(tags -> String.join(", ", tags)).orElse(null);
    }

    private void fetchContentFragmentData() {
        Optional.ofNullable(resourceResolver.getResource(authorContentFragment))
                .ifPresent(fragmentResource -> {
                    Optional<ContentFragment> cfAuthorOptional = Optional
                            .ofNullable(fragmentResource.adaptTo(ContentFragment.class));
                    cfAuthorOptional.ifPresent(cfAuthor -> {
                        authorName = Optional.ofNullable(cfAuthor.getElement("authorName"))
                                .map(ContentElement::getContent)
                                .orElse(null);
                        authorDisplayPicture = Optional.ofNullable(cfAuthor.getElement("authorDisplayPicture"))
                                .map(ContentElement::getContent)
                                .orElse(null);
                        authorBio = Optional.ofNullable(cfAuthor.getElement("authorBio"))
                                .map(ContentElement::getContent)
                                .orElse(null);
                    });
                });
    }
}
