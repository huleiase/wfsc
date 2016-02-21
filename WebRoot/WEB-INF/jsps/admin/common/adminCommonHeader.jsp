<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<link rel="stylesheet" href="mm/css/bootstrap.min.css" />
<link rel="stylesheet" href="mm/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="mm/css/uniform.css" />
<link rel="stylesheet" href="mm/css/select2.css" />
<link rel="stylesheet" href="mm/css/matrix-style.css" />
<link rel="stylesheet" href="mm/css/matrix-media.css" />
<link rel="stylesheet" href="mm/css/bootstrap-wysihtml5.css" />
<link href="mm/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="js/artDialog4.1.7/skins/default.css" />


<script src="js/artDialog4.1.7/artDialog.js"></script> 
<script src="js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script src="mm/js/jquery.min.js"></script> 
<script src="mm/js/jquery.ui.custom.js"></script> 
<script src="mm/js/bootstrap.min.js"></script> 
<script src="mm/js/masked.js"></script> 
<script src="mm/js/jquery.uniform.js"></script> 
<!--<script src="mm/js/select2.min.js"></script> 

--><script src="mm/js/matrix.js"></script> 
<script src="mm/js/wysihtml5-0.3.0.js"></script> 
<script src="mm/js/jquery.peity.min.js"></script> 
<script src="mm/js/bootstrap-wysihtml5.js"></script> 
<script src="mm/js/matrix.form_common.js"></script> 
<script src="js/common/commonUse.js"></script> 
<style type="text/css">

body {
overflow: scroll;
overflow-x: hidden;
overflow-x: scroll !important;
}
#content {
    margin-left: 180px;
}
#sidebar > ul {
    width: 180px;
}

#header h1 {
    background: transparent url("../img/logo.png") no-repeat scroll 0 0;
    width: 156px;
}
.form-horizontal .controls {
    margin-left: 10px;
    padding: 10px 0;
}

.clear{
clear:both;
}
[class*="span"] {
    margin-left: 20px;
}
</style>
<script type="text/javascript">
 $(function() {
    $("#title-table-checkbox").click(function() {
    	var isChecked = this.checked;
           $('input[name="ids"]').each(function() {
				$(this).attr("checked", isChecked);
				if(isChecked){
					$(this).parent().addClass("checked");
				}else{
					$(this).parent().removeClass("checked");
				}
				
			}); 
            });
        });
</script>
