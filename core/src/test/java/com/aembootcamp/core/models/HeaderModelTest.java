package com.aembootcamp.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.spi.Injector;
import io.wcm.testing.mock.aem.junit5.AemContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class HaderModelTest {

    private final AemContext context = new AemContext();

    private HeaderModel headerModel;

    private Resource resource;

    @BeforeEach
    void setUp() throws Exception {
        context.addModelsForClasses(HeaderModel.class);
        
        resource = context.create().resource("/content/header",
            "fileReference", "/content/dam/logo.png",
            "altText", "AlternativeText",
            "logoLink", "/content/home");

        context.create().resource("/content/header/navigations/item1",
            "rootPath", "/content/page1");
        
        context.create().page("/content/page1");
        
        headerModel = resource.adaptTo(HeaderModel.class);
    }

    @Test
    void getFileReference() {
        assertEquals("/content/dam/logo.png", headerModel.getFileReference());
    }

    @Test
    void getAltText() {
        assertEquals("AlternativeText", headerModel.getAltText());
    }

    @Test
    void getLogoLink() {
        assertEquals("/content/home", headerModel.getLogoLink());
    }

    @Test
    void getHeaderNavigationItemsList() {
        List<Page> navigationItems = headerModel.getHeaderNavigationItemsList();
        assertNotNull(navigationItems);
        assertEquals(1, navigationItems.size());
        assertEquals("/content/page1", navigationItems.get(0).getPath());
    }

}
