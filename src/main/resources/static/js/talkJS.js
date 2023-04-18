// Talk.ready.then(function () {
//     let me = new Talk.User({
//         id: '123456',
//         name: 'Alice',
//     });
//     window.talkSession = new Talk.Session({
//         appId: 'tU1uK9B8',
//         me: me,
//     });
//     let other = new Talk.User({
//         id: '654322',
//         name: 'Sebastian',
//     });
//
//     let conversation = talkSession.getOrCreateConversation(
//         Talk.oneOnOneId(me, other)
//     );
//     conversation.setParticipant(me);
//     conversation.setParticipant(other);
//
//     let inbox = talkSession.createInbox({ selected: conversation });
//     inbox.mount(document.getElementById('talkjs-container'));
// });
// Talk.ready.then(function () {
let username = document.getElementById("username").getAttribute("value")
let id = document.getElementById("id").getAttribute("value")
const getOtherUser = async () => {
    let otherUser = new Talk.User({
        id: id,
        name: username
    });
    return otherUser;
}
const getUser = async () => {
    const response = await fetch('/loggedInChatUser');
    const data = await response.json();
    console.log("dataset" + data)
    let user = new Talk.User({
        id: data.id,
        name: data.username
    });
    return user;
}

(
    async function() {
        console.log("in async");
        Talk.ready;
        console.log("talk ready");
        let user = await getUser();
        console.log("user")
        window.talkSession = new Talk.Session({
            appId: TEST_APPID,
            me: user
        });
        let otherUser = await getOtherUser();
        console.log("other user")

        const conversation = talkSession.getOrCreateConversation(Talk.oneOnOneId(user, otherUser));
        conversation.setParticipant(otherUser);
        conversation.setParticipant(user);

        let inbox = talkSession.createInbox(conversation);
        inbox.select(conversation);
        inbox.mount(document.getElementById("talkjs-container"));
    }());
// });
// map a user in your own database one-to-one to a TalkJS user.
//403 Forbidden response status