document.addEventListener('touchstart', function (e) {
  e = e.changedTouches[0];
  onStart(e);
});

document.addEventListener('touchmove', function (e) {
  onMove(e.changedTouches[0], e);
});

document.addEventListener('touchend', function (e) {
  onEnd(e.changedTouches[0]);
});


// 翻转的绑定
window.onorientationchange = orientationChange;

function initPage(){
  pageWidth = $(document).width();
  pageHeight = $(".wrap").height();
  pages = $(".wrap section");

  //alert("宽："+ pageWidth +"；高："+ pageHeight);

  $(".wrap section").css({
    "width":pageWidth+"px",
    "height":pageHeight+"px"
  });
  $(".line").css({
    "width":pageWidth+"px",
    "height":pageHeight * 6 +"px"
  });

  secHeight = pageHeight * $(".wrap section").length;

  $(".sec, .line").addClass("drag");
  //animatePage(curPage);
  if(pageWidth > pageHeight){
    $('body').addClass('horizontal');
  }
  if(pageWidth == 360){
    $('body').addClass('w360');
    if(pageHeight == 575){
      //alert('三星@');
      $('body').addClass('h575');
    }
  }
  if(pageWidth == 540){
    $('body').addClass('w540');
  }
  if(pageHeight == 416){
    $('body').addClass('h416');
  }



}

function orientationChange(){
  initPage();
}

// 以下是拖动效果
var startX = 0,
    startY = 0;
margin = 0;
var pages = null;
var curPage = 0;
var pageWidth = 0,
    pageHeight = 0;
var lineHeight = 0, secHeight = 0;
var targetElement = null;
var scrollPrevent = false, movePrevent = false, touchDown = false;


$(document).ready(function(){
  initPage();
});


function onStart (e) {
  if(movePrevent == true){
    event.preventDefault();
    return false;
  }
  if($(e.target).parents("#container").length==1){
    scrollPrevent = true;
  }else{
    scrollPrevent = false;
  }

  touchDown = true;

  // 起始点，页面位置
  startX = e.pageX;
  startY = e.pageY;
  //margin = parseInt($(".sec").css("top"));
  //-webkit-transform:translateY(0px)

  //matrix(1, 0, 0, 1, 0, 8)

  $(".sec").addClass("drag");

  margin = $(".sec").css("-webkit-transform");
  //margin = "matrix(1, 0, 0, 1, 0, -50)";
  margin = margin.replace("matrix(", "");
  margin = margin.replace(")", "");
  margin = margin.split(",");
  margin = parseInt(margin[5]);
}

function onMove (e, oe) {
  if(movePrevent == true || touchDown != true){
    event.preventDefault();
    return false;
  }
  event.preventDefault();
  if( scrollPrevent==false && e.pageY!=startY){
    var temp = margin + e.pageY - startY;
    $(".sec").css("-webkit-transform", "matrix(1, 0, 0, 1, 0, "+temp+")");
  }
}

function onEnd (e) {
  if(movePrevent == true){
    event.preventDefault();
    return false;
  }

  touchDown = false;

  if( scrollPrevent==false ){
    // 抬起点，页面位置
    endX = e.pageX;
    endY = e.pageY;
    // swip 事件默认大于50px才会触发，小于这个就将页面归回
    if( Math.abs(endY-startY)<=50) {
      animatePage(curPage);
    }else{
      if(endY>startY){
        prevPage();
      }else{
        nextPage();
      }
    }
  }

  $(".sec, .line").removeClass("drag");
}

function prevPage(){
  var newPage = curPage - 1;
  animatePage(newPage);

}
function nextPage(){
  var newPage = curPage + 1;
  animatePage(newPage);
}

function animatePage( newPage ){
  if(newPage<0){
    newPage = 0;
  }
  if(newPage>$(".wrap section").length-1){
    newPage = $(".wrap section").length-1;
  }

  curPage = newPage;
  var newMarginTop = newPage * (-pageHeight);
  $(".sec").css({
    "-webkit-transform" : "matrix(1, 0, 0, 1, 0, "+newMarginTop+")"
  });

  var newTop = -parseInt(curPage*pageHeight*(lineHeight/secHeight));
  $(".line").css({
    "-webkit-transform" : "matrix(1, 0, 0, 1, 0, "+newTop+")"
  });

  movePrevent = true;
  setTimeout("movePrevent=false;", 300 );

  // 每页动画
  if( !$(pages[curPage]).hasClass("sec0" + (curPage+1) + "_show") ){
    $(pages[curPage]).addClass("sec0" + (curPage+1) + "_show");
  }
  $(pages[curPage-1]).removeClass("sec0" + (curPage) + "_show");
  $(pages[curPage+1]).removeClass("sec0" + (curPage+2) + "_show");

}


