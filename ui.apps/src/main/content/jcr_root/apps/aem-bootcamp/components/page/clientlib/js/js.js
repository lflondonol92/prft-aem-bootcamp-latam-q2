(function ($, Granite, document, MSM) {
    "use strict";
  
    var pagePath = `${MSM.MSMCommons.getPageContentUrl()}.json`; //gets page path
    console.log(pagePath);
    //var pagePath = MSM.MSMCommons.getPageContentUrl();
    //var extPagePath = Granite.HTTP.externalize(pagePath);
    //var intPagePath = Granite.HTTP.internalize(pagePath);
    //var pageData = Granite.HTTP.getPath(extPagePath);
    //console.log("pageData: ", pageData);
    var pageObj = Granite.HTTP.eval(pagePath); //gets page properties
    console.log(pageObj);
  
    /*   $(document).ready(function (e) {
      getPageTitle($(this));
    });*/
  
    getPageTitle();
  
    function getPageTitle() {
      var pageTitle = pageObj["jcr:title"];
      console.log("title: ", pageTitle);
      /* if (!pageObj["ogtitle"]) {
        console.log(pageObj["jcr:title"]);
        pageObj["ogtitle"] = pageObj["jcr:title"];
        console.log(pageObj["ogtitle"]);
      } */
  
      //var title = $(document).attr("title");
      var titleFromDialog = document.getElementsByClassName("page-title-field");
      console.log(titleFromDialog, pageTitle);
      if (titleFromDialog !== null || titleFromDialog == "") {
        titleFromDialog.value = pageTitle;
      }
    }
  })(jQuery, Granite, document, MSM);