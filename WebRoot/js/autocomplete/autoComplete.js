;(function($){
/* Plugin */
$.fn.autoComplete=function(o){
    if(o.ajax) o.ajax=$.extend({ url:'', dataType:'json', async:true }, o.ajax||{});
    o.elemCSS=$.extend({ focus:{'color':'#f00'}, blur:{'color':'#000'} }, o.elemCSS||{});
    o=$.extend({
        source:null,/* privide an array for match */
        ajax:{},/* provide an ajax conditions, if source is exist this parameter is invalid */
        input:'input',/* provide the selector of input box */
        popup:'ul',/* provide the selector of popup box, it must be a ul element of html */
        elemCSS:{}/* provide the focus and blur css objects of items in the popup box */
    },o||{});
 
    var handler=(function(){
        var handler=function(e,o){ return new handler.prototype.init(e,o); };
        handler.prototype={
            e:null, o:null, timer:null, show:0, $input:null, $popup:null,
            init:function(e,o){
                this.e=e;
                this.o=o;
                this.$input=$(this.o.input,this.e);
                this.$popup=$(this.o.popup,this.e);
                this.initEvent();
            },
            match:function(quickExpr,value,source){
                for(var i in source){
                    if( value.length>0 && quickExpr.exec(source[i])!=null )
                        this.$popup.append('<li><a href="javascript:autoFill2();">'+source[i]+'</a></li>');
                }
                if($('li a',this.$popup[0]).length){ this.$popup.show(); }else{ this.$popup.hide(); }
            },
            fetch:function(ajax,search,quickExpr){
                var that=this;
                $.ajax({
                    url: ajax.url+search,
                    dataType: ajax.dataType,
                    async: ajax.async,
                    error: function(data,es,et){ alert('error'); },
                    success: function(data){ that.match(quickExpr,search,data); }
                });
            },
            initEvent:function(){
                var that=this;
                this.$input.focus(function(){
                    var value=this.value, quickExpr=RegExp('^'+value,'i'), self=this;
                    that.timer=setInterval(function(){
                        if(value!=self.value){
                            value=self.value;
                            that.$popup.html('');
                            if(value!=''){
                                quickExpr=RegExp('^'+value);
                                if(that.o.source) that.match(quickExpr,value,that.o.source);
                                else if(that.o.ajax) that.fetch(that.o.ajax,value,quickExpr);
                            }
                        }
                    },200);
                }).blur(function(){
                    clearInterval(that.timer);
                    var current=-1;
                    var len=that.$popup.find("li a").length-1;
                    $("li a",that.$popup[0]).click(function(){
                        that.$input[0].value=$(this).text();
                        that.$popup.html('').hide();
                    }).focus(function(){
                        current = $(this).parent().index();
                        $(this).css(that.o.elemCSS.focus);
                    }).blur(function(){
                        $(this).css(that.o.elemCSS.blur);
                    });
                    $("li a",that.$popup[0]).keydown(function(event){
                        if(event.keyCode==40){
                            current++;
                            if(current<0) current=len;
                            if(current>len) current=0;
                            that.$popup.find("li a").get(current).focus();
                        }else if(event.keyCode==38){
                            current--;
                            if(current>len) current=0;
                            if(current<0) current=len;
                            that.$popup.find("li a").get(current).focus();
                        }
                    });
                }).keydown(function(event){
                    if(event.keyCode==40){
                        that.$popup.blur().find("li a").get(0).focus();
                    }
                });
                $(this.e).hover(function(){ that.show=1; },function(){ that.show=0; });
                $(document).click(function(){ if(that.show==0){ that.$popup.hide(); } });
            }
        };
        handler.prototype.init.prototype=handler.prototype;/* JQuery style */
        return handler;
    })();
 
    return this.each(function(){ handler(this,o); });
};
/* Invoke */
$(document).ready(function(){
	var vcattn = $("#vcattncompany").val();
    $(".autoComplete").autoComplete({
        //source:["123","1234","43563","jj","你吗"],
        //ajax:{ url:'./php/fetch.php?search=', dataType:'json', async:false },
        source:JSON.parse(vcattn),
        elemCSS:{ focus:{'color':'#0f0'}, blur:{'color':'#f00'} }
    });
});
/* Conclude */
})(jQuery);