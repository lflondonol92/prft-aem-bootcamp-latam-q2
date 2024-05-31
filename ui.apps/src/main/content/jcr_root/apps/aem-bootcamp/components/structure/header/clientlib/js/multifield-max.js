(function ($, window) {
$(window).adaptTo("foundation-registry").register("foundation.validation.validator", {     
  selector: "[data-foundation-validation^='multifield-max']",     
  validate: function(el) {       
    let validationName = el.getAttribute("data-validation");   
    let max = validationName.replace("multifield-max-", "");
    max = parseInt(max);       
    if (el.items.length > max){
       return "The Maximun number of path links allowed is "+ max
     }
   }
 });
})
($, window);