function(){
  var f=document.getElementsByTagName("header");
  var content=document.getElementsByClassName("content");
  for(var i=0;i<f.length;i++)
  {
    f[i].remove();
  }
  content.style.marginTop=0;
}