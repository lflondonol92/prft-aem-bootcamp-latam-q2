package com.aembootcamp.core.models;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;
import io.wcm.testing.mock.aem.junit5.AemContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class ArticleModelTest {

    private final AemContext context = new AemContext();

    private ArticleModel articleModel;

    private Resource resource;

    private Page page;

    private Resource fragmentResourceOptional;

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
        Field authordetail = ArticleModel.class.getDeclaredField("authorContentFragment");
        authordetail.setAccessible(true);
        String authordetailObj = (String) authordetail.get(articleModel);
        assertEquals("/content/authordetail-cf", authordetailObj);
    }

    @Test
    void getFetchTagText() {
        articleModel.fetchTagText();
        assertEquals("category1, category2", articleModel.getCategoriesTagText());
    }

}
