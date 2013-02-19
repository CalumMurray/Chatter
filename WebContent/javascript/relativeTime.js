function relativeTime() 
{
    //alert("in");
    var msPerMinute = 60 * 1000;
    var msPerHour = msPerMinute * 60;
    var msPerDay = msPerHour * 24;
    var msPerMonth = msPerDay * 30;
    var msPerYear = msPerDay * 365;

    //var timeStampLength = 21;

    var timestamps = document.getElementsByClassName("timestamp");
    for (var i = 0; i < timestamps.length; i++) 
    {
    	//alert(timestamps.length);
    	
    	//alert(timestamps[i]);
        // Split timestamp into [ Y, M, D, h, m, s ]
        var timeElements = timestamps[i].innerHTML.split(/[\- :]/);
        //for (var j = 0; j < timeElements.length; j++)
        //	alert(timeElements[j]);
        // Apply each element to the Date function
        var previous = new Date(timeElements[0], timeElements[1] - 1, timeElements[2], timeElements[3], timeElements[4], timeElements[5]);
        var current = new Date();

        var elapsed = current - previous;
       // alert(elapsed);
        
        var relativeTime;
        if (elapsed < msPerMinute) {
            relativeTime = Math.round(elapsed / 1000) + ' seconds ago';
        } else if (elapsed < msPerHour) {
            relativeTime = Math.round(elapsed / msPerMinute) + ' minutes ago';
        } else if (elapsed < msPerDay) {
            relativeTime = Math.round(elapsed / msPerHour) + ' hours ago';
        } else if (elapsed < msPerMonth) {
            relativeTime = 'about ' + Math.round(elapsed / msPerDay) + ' days ago';
        } else if (elapsed < msPerYear) {
            relativeTime = 'about ' + Math.round(elapsed / msPerMonth) + ' months ago';
        } else {
            relativeTime = 'about ' + Math.round(elapsed / msPerYear) + ' years ago';
        }

        timestamps[i].innerHTML = relativeTime;
    }



}