"use strict";

document.addEventListener("DOMContentLoaded", function() {
    const elements = Array.from(document.getElementsByClassName("card-title"));
    const input = document.getElementById("search-input");
    const suggestions = document.getElementById("suggestions");
    const btn = document.getElementById("search-btn");

    // Function to generate suggestions based on input
    function generateSuggestions() {
        const myInput = new RegExp(input.value, "i");
        const matches = elements.filter(function(element) {
            return element.textContent.match(myInput);
        }).slice(0, 5); // add this line to limit to first 5 elements
        const suggestionsHTML = matches
            .map(function(match) {
                return `<div class="px-2 suggestion">${match.textContent}</div>`;
            })
            .join("");
        suggestions.innerHTML = suggestionsHTML;
    }


    //jeremy d-none attempt

    if ($('#suggestions').length) {
        $('#suggestions').addClass('d-none');
    };

    input.onfocus = function () {
        // if ($('#profile-pw-validation').length) {
        generateSuggestions();
        $('#suggestions').removeClass('d-none');
        // }
    }

    // Listen for click events on suggestions
    suggestions.addEventListener("click", function(event) {
        if (event.target.classList.contains("suggestion")) {
            input.value = event.target.textContent;
            // suggestions.classList.remove("show");
            $('#suggestions').addClass('d-none');
            // btn.click();
        }
    });

    // Hide suggestions on document click
    document.addEventListener("click", function(event) {
        if (!event.target.matches("#search-input, #suggestions *")) {
            $('#suggestions').addClass('d-none');

            // suggestions.classList.remove("show");
        }
    });

    btn.onclick = function() {
        const myInput = new RegExp(input.value, "i");
        elements.forEach(function(element) {
            if (!element.textContent.match(myInput)) {
                element.parentElement.parentElement.parentElement.parentElement.classList.add("d-none");
            } else {
                element.parentElement.parentElement.parentElement.parentElement.classList.remove("d-none");
            }
        });
    };
});



