"use strict";
// Get the notification div element

// Get the message from the model attribute
// let message = "${message}";

// Set the contents of the notification div
// notification.innerHTML = message;

// Show the notification div
let notification = document.getElementById("notification");
if(notification.textContent.trim().length !== 0){
    notification.classList.remove("hidden");
    notification.classList.add("show");

// Hide the notification div after 5 seconds
    setTimeout(function() {
        notification.classList.remove("show");
        notification.classList.add("hidden");
    }, 10000);
}

