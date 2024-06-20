package com.aembootcamp.core.models;

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
        if (currentPage.getProperties().get("articlecategories") != null) {
            String[] tags = (String[]) currentPage.getProperties().get("articlecategories");
            categoriesTagText = String.join(", ", tags);
        }
    }

    private void fetchContentFragmentData() {
        Optional<Resource> fragmentResourceOptional = Optional
                .ofNullable(resourceResolver.getResource(authorContentFragment));

        fragmentResourceOptional.ifPresent(fragmentResourceAdaptToCF -> {
            Optional<ContentFragment> cfAuthorOptional = Optional
                    .ofNullable(fragmentResourceOptional.get().adaptTo(ContentFragment.class));

            cfAuthorOptional.ifPresent(cfAuthorGetData -> {
                Optional<String> authorNameOptional = Optional
                        .ofNullable(cfAuthorOptional.get().getElement("authorName").getContent());
                authorNameOptional.ifPresent(authorNameData -> {
                    authorName = authorNameOptional.get();
                });

                Optional<String> authorDisplayPictureOptional = Optional
                        .ofNullable(cfAuthorOptional.get().getElement("authorDisplayPicture").getContent());
                authorDisplayPictureOptional.ifPresent(authorDisplayPictureData -> {
                    authorDisplayPicture = authorDisplayPictureOptional.get();
                });

                Optional<String> authorBioOptional = Optional
                        .ofNullable(cfAuthorOptional.get().getElement("authorBio").getContent());
                authorBioOptional.ifPresent(authorBioData -> {
                    authorBio = authorBioOptional.get();
                });
            });
        });
    }
}
