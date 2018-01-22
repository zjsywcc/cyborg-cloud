$(document).ready(function () {

    function setPercent(per){
        console.log("        ");
      //  i=i+1;
        $("#chart1").attr("data-percent",per);

        App.charts();
    }
    setPercent("20");
  //  status=getTiredStatus(id,name)
    //setPercent(status);
    setInterval(function()
        {
           // status=getTiredStatus(id,name)
            status="10";
            setPercent(status);
        }
        ,5000);
});








