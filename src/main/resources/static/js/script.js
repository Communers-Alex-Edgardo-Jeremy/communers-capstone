"use strict";
// Get the notification div element

// Get the message from the model attribute
// let message = "${message}";

// Set the contents of the notification div
// notification.innerHTML = message;

// Show the notification div
let notification = document.getElementById("notification");
if(notification != null){
    if(notification.textContent.trim().length !== 0){
        notification.classList.remove("opacity-0");
        // notification.classList.add("show");

// Hide the notification div after 5 seconds
        setTimeout(function() {
            // notification.classList.remove("show");
            notification.classList.add("opacity-0");
        }, 15000);
    }
}
let fetchUrl;
if (document.body.dataset.page === "communities") {
    fetchUrl = '/community'
} else {
    fetchUrl = '/follow'
}


const followButtons = document.querySelectorAll('.followButton');
followButtons.forEach(function(button) {
    button.addEventListener('click', function() {
        // const followerId = document.querySelector('#currentUser').dataset.id;
        const followeeId = this.parentNode.getAttribute("dataset-id");

        console.log(fetchUrl);

        fetch(fetchUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: followeeId
            })
        }).then(response => {
            return response.json()
        }).then(json => {
            // check if the current user is following the followee
            console.log(json);

            const isFollowing = json.following;
            console.log(isFollowing)

// landing page scroll grab

new ScrollBooster({
    viewport: document.querySelector('.section2'),
    content: document.querySelector('.scrolling-wrapper-container'),
    scrollMode: 'native',
    direction: 'horizontal'
});
