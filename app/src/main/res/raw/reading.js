function(){
  var f=document.getElementsByTagName("header");
  var h3=document.getElementsByTagName("h3");
  var content=document.getElementsByClassName("content");
  for(var i=0;i<f.length;i++)
  {
    f[i].remove();
  }
  h3[0].remove();
  content[0].style.marginTop=0;
}