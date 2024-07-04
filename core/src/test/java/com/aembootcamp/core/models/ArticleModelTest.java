package com.aembootcamp.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.wcm.api.Page;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.factory.ModelFactory;
import io.wcm.testing.mock.aem.junit5.AemContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.mockito.Mockito;

@ExtendWith(AemContextExtension.class)
class ArticleModelTest {
    private static final String AUTHOR_NAME = "authorName";
    private static final String AUTHOR_DISPLAY_PICTURE = "authorDisplayPicture";
    private static final String AUTHOR_BIO = "authorBio";
    public static final String AUTHOR_CONTENT_FRAGMENT_ATR = "authorContentFragment";
    public static final String RESOURCE_RESOLVER_OBJ = "resourceResolver";

    private final AemContext context = new AemContext();

    private ArticleModel articleModel;

    private Resource resource;

    private Page page;

    @BeforeEach
    void setUp() throws Exception {
        context.addModelsForClasses(ArticleModel.class);

        page = context.create().page("/content/aem-bootcamp/article",
                "testTemplate",
                "jcr:title", "Test Article Page",
                "sling:resourceType", "aem-bootcamp/components/structure/article",
                "articlecategories", new String[]{"category1", "category2"});

        resource = context.create().resource("/content/aem-bootcamp/article/articleComponent",
            "jcr:primaryType", "aem-bootcamp/components/structure/article",
            "title", "test title",
            "fileReference", "/content/dam/logo.png",
            "description", "test description",
            "authorContentFragment", "/content/authordetail-cf");

        context.currentPage(page);
        context.currentResource(resource);
        articleModel = context.getService(ModelFactory.class).createModel(context.request(), ArticleModel.class);

    }

    @Test
    void getTitle() {
        assertEquals("test title", articleModel.getTitle());
    }

    @Test
    void getFileReference() {
        assertEquals("/content/dam/logo.png", articleModel.getFileReference());
    }

    @Test
    void getDescription() {
        assertEquals("test description", articleModel.getDescription());
    }

    @Test
    void getAuthorContentFragment() throws NoSuchFieldException, IllegalAccessException {
        Field authordetail = ArticleModel.class.getDeclaredField(AUTHOR_CONTENT_FRAGMENT_ATR);
        authordetail.setAccessible(true);
        String authordetailObj = (String) authordetail.get(articleModel);
        assertEquals("/content/authordetail-cf", authordetailObj);
    }

    @Test
    void getFetchTagText() {
        articleModel.fetchTagText();
        assertEquals("category1, category2", articleModel.getCategoriesTagText());
    }

    @Test
    void fetchContentFragmentData() throws NoSuchFieldException, IllegalAccessException {
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        Resource fragmentResourceOptional = mock(Resource.class);
        ContentFragment cfAuthor = mock(ContentFragment.class);

        ContentElement cfAuthorNameElement = Mockito.mock(ContentElement.class);
        ContentElement cfAuthorDisplayPictureElement = Mockito.mock(ContentElement.class);
        ContentElement cfAuthorBioElement = Mockito.mock(ContentElement.class);

        when(resourceResolver.getResource("/content/authordetail-cf")).thenReturn(fragmentResourceOptional);
        when(fragmentResourceOptional.adaptTo(ContentFragment.class)).thenReturn(cfAuthor);

        when(cfAuthor.getElement(AUTHOR_NAME)).thenReturn(cfAuthorNameElement);
        when(cfAuthorNameElement.getContent()).thenReturn("testAuthorName");
        when(cfAuthor.getElement(AUTHOR_DISPLAY_PICTURE)).thenReturn(cfAuthorDisplayPictureElement);
        when(cfAuthorDisplayPictureElement.getContent()).thenReturn("testAuthorDisplayPicture");
        when(cfAuthor.getElement(AUTHOR_BIO)).thenReturn(cfAuthorBioElement);
        when(cfAuthorBioElement.getContent()).thenReturn("testAuthorBio");

        Field resourceResolverField = ArticleModel.class.getDeclaredField(RESOURCE_RESOLVER_OBJ);
        resourceResolverField.setAccessible(true);
        resourceResolverField.set(articleModel, resourceResolver);

        articleModel.fetchContentFragmentData();
        
        assertEquals("testAuthorName", articleModel.getAuthorName());
        assertEquals("testAuthorDisplayPicture", articleModel.getAuthorDisplayPicture());
        assertEquals("testAuthorBio", articleModel.getAuthorBio());
    }

}