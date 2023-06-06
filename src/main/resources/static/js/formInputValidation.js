"use strict";
const myForm = document.getElementById('form');
const userName = document.getElementById('username');
const firstName = document.getElementById('first_name');
const lastName = document.getElementById('last_name');
const emailInput = document.getElementById('email');
const feedback = document.getElementById('feedback')

myForm.addEventListener('submit', (event) => {
    // Prevent the form from submitting
    event.preventDefault();

    // Validate the name input
    const usernameValue = userName.value.trim();
    const nameRegex = /^[a-zA-Z]{2,}$/;
    if (usernameValue.length > 50 || !nameRegex.test(usernameValue)) {
        userName.value = '';
        feedback.innerText = 'Username must contain only characters between 2 and 50 characters in length';
        userName.focus();
        return;
    }

    const firstNameValue = firstName.value.trim();
    if (firstNameValue.length > 50 || !nameRegex.test(firstNameValue)) {
        firstName.value = '';
        feedback.innerText = 'First name must contain only characters and be between 2 and 50 characters in length';
        firstName.focus();
        return;
    }

    const lastNameValue = lastName.value.trim();
    if (lastNameValue.length > 50 || !nameRegex.test(lastNameValue)) {
        lastName.value = '';
        feedback.innerText = 'Last name must contain only characters and be between 2 and 50 characters in length';
        lastName.focus();
        return;
    }

    // Validate the email input
    const emailValue = emailInput.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (emailValue.length > 100 || !emailRegex.test(emailValue)) {
        emailInput.value = '';
        feedback.innerText = 'Please enter a valid email address';
        emailInput.focus();
        return;
    }
    // If all input is valid, submit the form
    myForm.submit();
});
