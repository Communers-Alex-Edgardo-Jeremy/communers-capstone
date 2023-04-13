Talk.ready.then(function () {
    let me = new Talk.User({
        id: '123456',
        name: 'Alice',
        email: 'alice@example.com',
        photoUrl: 'https://talkjs.com/images/avatar-1.jpg',
        welcomeMessage: 'Hey there! How are you? :-)',
    });
    window.talkSession = new Talk.Session({
        appId: TEST_APP_ID,
        me: me,
    });
    let other = new Talk.User({
        id: '654321',
        name: 'Sebastian',
        email: 'Sebastian@example.com',
        photoUrl: 'https://talkjs.com/images/avatar-5.jpg',
        welcomeMessage: 'Hey, how can I help?',
    });

    let conversation = talkSession.getOrCreateConversation(
        Talk.oneOnOneId(me, other)
    );
    conversation.setParticipant(me);
    conversation.setParticipant(other);

    let inbox = talkSession.createInbox({ selected: conversation });
    inbox.mount(document.getElementById('talkjs-container'));
});

// const getOtherUser = async () => {
//     const response = await fetch('/loggedInChatUser');
//     const data = await response.json();
//     console.log(data)
//     let otherUser = new Talk.User({
//         id: data.id,
//         first_name: data.first_name,
//         last_name: data.last_name,
//         email: data.email,
//     });
//     return otherUser;
// }
// const getUser = async () => {
//     const response = await fetch('/loggedInChatUser');
//     const data = await response.json();
//     console.log(data)
//     let user = new Talk.User({
//         id: data.id,
//         first_name: data.first_name,
//         last_name: data.last_name,
//         email: data.email,
//     });
//     return user;
// }
//
// (
//     async function() {
//         await Talk.ready;
//         let otherUser = await getOtherUser();
//         let user = await getUser();
//
//         const session = new Talk.Session({
//             appId: 'tU1uK9B8',
//             me: user,
//         });
//         let conversation = session.getOrCreateConversation(Talk.oneOnOneId(user, otherUser));
//
//         conversation.setAttributes({
//             welcomeMessages: ["You can start typing your message here and one of our agents will be with you shortly.", "Please do not divulge any of your personal information."]
//         })
//         conversation.setParticipant(user);
//         conversation.setParticipant(otherUser);
//
//         let inbox = session.createInbox(conversation);
//         inbox.mount(document.getElementById("talkjs-container"));
//     }());

// map a user in your own database one-to-one to a TalkJS user.
//403 Forbidden response status