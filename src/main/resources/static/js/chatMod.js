"use strict";

document.addEventListener("DOMContentLoaded", function() {

    function addClickEvent(button){
        button.addEventListener('click', async function (e) {
            e.preventDefault()
            let title = this.closest('form').querySelector('input[name="title"]').value;
            let body = this.closest('form').querySelector('textarea[name="body"]').value;
            let form = this.closest('form')
            let msg = this.closest('form').querySelector('.mod-msg')
            let content = body;
            if (title !== null) {
                content = title + " " + body;
            }
            const data = new FormData();
            data.append('text', content);
            data.append('lang', 'en');
            data.append('opt_countries', 'us');
            data.append('mode', 'standard');
            data.append('api_user', '202054065');
            data.append('api_secret', SIGHT_ENGINE_KEY);
            let returnMsg = ""
            await fetch('https://api.sightengine.com/1.0/text/check.json', {
                method: 'POST',
                body: data
            })
                .then(response => response.json())
                .then(function (data) {
                    console.log(data)
                    if (data.link.matches.length > 0) {
                        returnMsg += "URL Link\n"
                    }
                    if (data.personal.matches.length > 0) {
                        returnMsg += "Personal Information\n"
                    }
                    if (data.profanity.matches.length > 0) {
                        returnMsg += "Profanity\n"
                    }
                    if(returnMsg === "") {
                        form.submit();
                    } else{
                        returnMsg = "Please remove:\n" + returnMsg
                        msg.innerText = returnMsg
                    }
                    console.log(returnMsg)
                })
                .catch(error => console.error(error));
        });
    }
    let checkButtons = document.getElementsByClassName("submit-btn");

    if(checkButtons.length > 1){
        checkButtons.forEach(function(button){
            addClickEvent(button)
        });
    } else {
        addClickEvent(checkButtons[0])
    }
});
