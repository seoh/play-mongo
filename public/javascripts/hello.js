function flash(result, key) {
  if(result && key.length > 0) {
    [].slice.call(document.querySelectorAll("ol li")).forEach(function(li){
      if(li.textContent.indexOf(key + " ->") == 0){
        li.style.backgroundColor = "red";
      }
    });
  } else if(!result) {
    document.body.insertBefore(
      (function(){
        var span = document.createElement("span");
        span.innerHTML = "<i>" + key + "</i>";
        return span;
      })(),
      document.querySelector("ol"));
  }
}