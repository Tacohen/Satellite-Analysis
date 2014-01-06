



<div class="box full">
<!-- wrapper element for the large image -->
<div id="image_wrap" style="opacity: 1;">
  <!-- Initially the image is a simple 1x1 pixel transparent GIF -->
  <img src="http://farm1.static.flickr.com/28/66523124_b468cf4978.jpg" width="500" height="375">
</div>

<!-- HTML structures -->
<div style="margin:0 auto; width: 634px; height:120px;">
<!-- "previous page" action -->
<a class="prev browse left disabled"></a>

<!-- root element for scrollable -->
<div class="scrollable" id="scrollable">

  <!-- root element for the items -->
  <div class="items" style="left: 0px;">

    <!-- 1-5 -->
    <div>
      <img src="http://farm1.static.flickr.com/143/321464099_a7cfcb95cf_t.jpg" class="">
      <img src="http://farm4.static.flickr.com/3089/2796719087_c3ee89a730_t.jpg">
      <img src="http://farm1.static.flickr.com/79/244441862_08ec9b6b49_t.jpg">
      <img src="http://farm1.static.flickr.com/28/66523124_b468cf4978_t.jpg" class="active">
    </div>

    <!-- 5-10 -->
    <div>
      <img src="http://farm1.static.flickr.com/163/399223609_db47d35b7c_t.jpg">
      <img src="http://farm1.static.flickr.com/135/321464104_c010dbf34c_t.jpg">
      <img src="http://farm1.static.flickr.com/40/117346184_9760f3aabc_t.jpg">
      <img src="http://farm1.static.flickr.com/153/399232237_6928a527c1_t.jpg">
    </div>

    <!-- 10-15 -->
    <div>
      <img src="http://farm4.static.flickr.com/3629/3323896446_3b87a8bf75_t.jpg">
      <img src="http://farm4.static.flickr.com/3023/3323897466_e61624f6de_t.jpg">
      <img src="http://farm4.static.flickr.com/3650/3323058611_d35c894fab_t.jpg">
      <img src="http://farm4.static.flickr.com/3635/3323893254_3183671257_t.jpg">
    </div>

  </div>

</div>

<!-- "next page" action -->
<a class="next browse right"></a>
</div>
<script type="text/javascript" async="" src="http://www.google-analytics.com/ga.js">
<script src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
<script>
var $j=jQuery.noConflict();

$j(document).ready(function(){

$j(".scrollable").scrollable();

$j(".items img").click(function() {
	// see if same thumb is being clicked
	if ($j(this).hasClass("active")) { return; }

	// calclulate large image's URL based on the thumbnail URL (flickr specific)
	var url = $j(this).attr("src").replace("_t", "");

	// get handle to element that wraps the image and make it semi-transparent
	var wrap = $j("#image_wrap").fadeTo("medium", 0.5);

	// the large image from www.flickr.com
	var img = new Image();


	// call this function after it's loaded
	img.onload = function() {

		// make wrapper fully visible
		wrap.fadeTo("fast", 1);

		// change the image
		wrap.find("img").attr("src", url);

	};

	// begin loading the image from www.flickr.com
	img.src = url;

	// activate item
	$j(".items img").removeClass("active");
	$j(this).addClass("active");

// when page loads simulate a "click" on the first image
}).filter(":first").click();
});

</script>

</div>