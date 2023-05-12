"use strict";
const myForm = document.getElementById('form');
const userName = document.getElementById('username');
const firstName = document.getElementById('first_name');
const lastName = document.getElementById('last_name');
const emailInput = document.getElementById('email');

myForm.addEventListener('submit', (event) => {
    // Prevent the form from submitting
    event.preventDefault();

    // Validate the name input
    const usernameValue = userName.value.trim();
    const nameRegex = /^[a-zA-Z]+$/;
    let returnValue = false;
    if (usernameValue.length > 50 || !nameRegex.test(usernameValue)) {
        userName.value = '';
        userName.placeholder = 'Please enter a valid username (letters only, up to 50 characters)';
        userName.focus();
        returnValue = true;
    }

    const firstNameValue = firstName.value.trim();
    if (firstNameValue.length > 50 || !nameRegex.test(firstNameValue)) {
        firstName.value = '';
        firstName.title = 'Please enter a valid first name (letters only, up to 50 characters)';
        firstName.focus();
        returnValue = true;
    }

    const lastNameValue = lastName.value.trim();
    if (lastNameValue.length > 50 || !nameRegex.test(lastNameValue)) {
        lastName.value = '';
        lastName.title = 'Please enter a valid last name (letters only, up to 50 characters)';
        lastName.focus();
        returnValue = true;
    }

    // Validate the email input
    const emailValue = emailInput.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (emailValue.length > 100 || !emailRegex.test(emailValue)) {
        emailInput.value = '';
        emailInput.title = 'Please enter a valid email address (up to 100 characters)';
        emailInput.focus();
        returnValue = true;
    }
    if(returnValue){
        return;
    }
    // If all input is valid, submit the form
    myForm.submit();
});
