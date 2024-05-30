package com.aembootcamp.core.models;
 
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import javax.annotation.PostConstruct;
import com.day.cq.wcm.api.Page;
 
@Model(
        adaptables = {Resource.class},
        resourceType = {HeaderModel.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeaderModel {
 
    protected static final String RESOURCE_TYPE = "aem-bootcamp/components/structure/header";
 
    @ValueMapValue
    private String fileReference;
 
    @ValueMapValue
    private String altText;
 
    @ValueMapValue
    private String logoLink;
 
    @ChildResource(injectionStrategy = InjectionStrategy.DEFAULT)
    private Resource items;
 
    public List<Page> headerNavigationItemsList;
 
    @SlingObject
    private ResourceResolver resourceResolver;
 
 
    @PostConstruct
    private void init() {
        if(items!=null) {
          headerNavigationItemsList = new ArrayList<>();
   
          Iterator<Resource> children = items.listChildren();
   
          while (children.hasNext()){
              Resource childResource = children.next();
              ValueMap properties = childResource.adaptTo(ValueMap.class);
              String rootPath = properties.get("rootPath", String.class);
   
              Page navPage = resourceResolver.getResource(rootPath).adaptTo(Page.class);
              headerNavigationItemsList.add(navPage);
          }
        }
    }
 
    public String getFileReference() {
        return fileReference;
    }
 
    public String getAltText() {
        return altText;
    }
 
    public String getLogoLink() {
        return logoLink;
 
    }
 
    public List<Page> getHeaderNavigationItemsList() {
        return headerNavigationItemsList;        
    }    
 
}