// document.addEventListener("DOMContentLoaded", function() {
//     const communities = Array.from(document.getElementsByClassName("card-title"));
//     const input = document.getElementById("community-search");
//     input.onkeyup = function() {
//         const myInput = new RegExp(input.value, "i");
//         communities.forEach(function(community) {
//             if (!community.textContent.match(myInput)) {
//                 community.parentElement.parentElement.parentElement.classList.add("d-none");
//             } else {
//                 community.parentElement.parentElement.parentElement.classList.remove("d-none");
//             }
//         });
//     };
// });
// document.addEventListener("DOMContentLoaded", function() {
//     const communities = Array.from(document.getElementsByClassName("card-title"));
//     const btn = document.getElementById("search-btn");
//     const input = document.getElementById("community-search");
//
// });
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
                return `<div class="suggestion bg-light border border-2">${match.textContent}</div>`;
            })
            .join("");
        suggestions.innerHTML = suggestionsHTML;
    }


    // Listen for input events
    input.addEventListener("input", function() {
        generateSuggestions();
        suggestions.classList.add("show");
    });

    // Listen for click events on suggestions
    suggestions.addEventListener("click", function(event) {
        if (event.target.classList.contains("suggestion")) {
            input.value = event.target.textContent;
            suggestions.classList.remove("show");
            btn.click();
        }
    });

    // Hide suggestions on document click
    document.addEventListener("click", function(event) {
        if (!event.target.matches("#search-input, #suggestions *")) {
            suggestions.classList.remove("show");
        }
    });

    btn.onclick = function() {
        const myInput = new RegExp(input.value, "i");
        elements.forEach(function(element) {
            if (!element.textContent.match(myInput)) {
                element.parentElement.parentElement.parentElement.classList.add("d-none");
            } else {
                element.parentElement.parentElement.parentElement.classList.remove("d-none");
            }
        });
    };
});



