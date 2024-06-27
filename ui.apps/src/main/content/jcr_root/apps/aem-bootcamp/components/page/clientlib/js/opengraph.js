(function ($, window) {
  var pagePath = `${CQ.shared.HTTP.getPath().replace("/editor.html","")}/jcr:content.json`;
  var extPagePath = CQ.shared.HTTP.externalize(pagePath);
  var pageData = CQ.shared.HTTP.get(extPagePath);
  var pageObj = CQ.shared.Util.eval(pageData);

  $(document).ready(function () {
    pageTitle = pageObj["jcr:title"];
    if (!pageObj["ogtitle"]) {
      console.log(pageObj["jcr:title"]);
      pageObj["ogtitle"] = pageObj["jcr:title"];
      console.log(pageObj["ogtitle"]);
    }
  });
})($, window);
