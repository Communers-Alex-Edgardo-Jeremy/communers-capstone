"use strict";
window.onload = function(){

// document.addEventListener("DOMContentLoaded", (event) => {


    let myInput = document.getElementById("psw");
    let letter = document.getElementById("letter");
    let capital = document.getElementById("capital");
    let number = document.getElementById("number");
    const length = document.getElementById("length");
    const button = document.getElementById("pic-button");
    const imageInput = document.getElementById("file-upload");
    const imageDisplay = document.getElementById("image-display");
    if (imageDisplay != null) {
        const client = filestack.init(FILESTACK_KEY);
        const options = {
            onFileUploadFinished(file) {
                console.log(file);
                imageInput.value = file.url;
                imageDisplay.setAttribute("src", file.url);
            }
        }
        button.addEventListener("click", function (event) {
            client.picker(options).open();
        })
    }

    document.getElementById("message").style.visibility = "hidden";

// When the user clicks on the password field, show the message box
    myInput.onfocus = function () {
        document.getElementById("message").style.visibility = "visible";
    }

// When the user clicks outside of the password field, hide the message box
    myInput.onblur = function () {
        document.getElementById("message").style.visibility = "hidden";
    }

// When the user starts to type something inside the password field
    myInput.onkeyup = function () {
        // Validate lowercase letters
        let lowerCaseLetters = /[a-z]/g;
        if (myInput.value.match(lowerCaseLetters)) {
            letter.classList.remove("invalid");
            letter.classList.add("valid");
        } else {
            letter.classList.remove("valid");
            letter.classList.add("invalid");
        }

        // Validate capital letters
        let upperCaseLetters = /[A-Z]/g;
        if (myInput.value.match(upperCaseLetters)) {
            capital.classList.remove("invalid");
            capital.classList.add("valid");
        } else {
            capital.classList.remove("valid");
            capital.classList.add("invalid");
        }

        // Validate numbers
        let numbers = /[0-9]/g;
        if (myInput.value.match(numbers)) {
            number.classList.remove("invalid");
            number.classList.add("valid");
        } else {
            number.classList.remove("valid");
            number.classList.add("invalid");
        }

        // Validate length
        if (myInput.value.length >= 8) {
            length.classList.remove("invalid");
            length.classList.add("valid");
        } else {
            length.classList.remove("valid");
            length.classList.add("invalid");
        }
    }

}
