package com.aembootcamp.core.models;

import com.day.cq.i18n.I18n;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import lombok.Getter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.*;

import com.day.cq.wcm.api.Page;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@Getter
@Model(adaptables = { SlingHttpServletRequest.class }, resourceType = {
        SubscribeModel.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SubscribeModel {

    protected static final String RESOURCE_TYPE = "aem-bootcamp/components/structure/subscribe";

    private final String ARTICLE_PATH = "/content/cq:tags/articles";

    @Self
    private SlingHttpServletRequest slingRequest;

    @SlingObject
    private ResourceResolver resourceResolver;

    @ScriptVariable(injectionStrategy = InjectionStrategy.REQUIRED)
    private Page currentPage;

    @ValueMapValue
    private String redirect;

    private String firstNameLabel;

    private String lastNameLabel;

    private String emailLabel;

    private String preferredArticleTypeLabel;

    private List<String> articleTypes;

    @PostConstruct
    private void init() {
        fetchLabels();
        fetchTagsArticles();
    }

    private void fetchLabels() {
        Optional.ofNullable(currentPage.getLanguage(false))
                .map(slingRequest::getResourceBundle)
                .ifPresent(resourceBundle -> {
                    I18n i18n = new I18n(resourceBundle);
                    firstNameLabel = i18n.get("First Name");
                    lastNameLabel = i18n.get("Last Name");
                    emailLabel = i18n.get("Email");
                    preferredArticleTypeLabel = i18n.get("Preferred Article Type");
                });
    }

    private void fetchTagsArticles() {
        articleTypes = new ArrayList<>();

        Optional.ofNullable(resourceResolver.adaptTo(TagManager.class))
                .flatMap(tagManager -> Optional.ofNullable(tagManager.resolve(this.ARTICLE_PATH)))
                .ifPresent(articleTags -> {
                    Iterator<Tag> tags = articleTags.listAllSubTags();
                    while (tags.hasNext()) {
                        Tag tag = tags.next();
                        articleTypes.add(tag.getTitle());
                    }
                });
    }
}