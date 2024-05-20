import {useEffect, useState} from "react";
import {Header} from "../../components/header/Header.jsx";
import {useParams} from "react-router-dom";

import './user-chat-page.css'
import {Messages} from "../../components/messages/Messages.jsx";
import {OnlineUser} from "../../components/online-user/OnlineUser.jsx";
import {CreateGroupChatModal} from "../../components/create-group-chat/CreateGroupChatModal.jsx";
import {GroupChat} from "../../components/group-chats/GroupChat.jsx";

export const UserChatPage = () => {

    const params = useParams();

    const [currentUser, setCurrentUser] = useState({});
    const [onlineUsers, setOnlineUsers] = useState([]);
    const [fullName, setFullName] = useState("");
    const [selectedChat, setSelectedChat] = useState({});
    const [allChats, setAllChats] = useState([]);
    const [privateChats, setPrivateChats] = useState([]);
    const [groups, setGroups] = useState([]);
    const [stomp, setStomp] = useState({});
    const [messages, setMessages] = useState([]);

    // получение id авторизованного пользователя
    useEffect(() => {
        try {
            fetch(`http://localhost:8080/api/users/${params.id}`)
                .then(res => res.json())
                .then(data => {
                    setCurrentUser(data);
                    setFullName(data.fullName)
                })
        } catch (error) {
            console.log(error);
        }
    }, []);


    // получение всех пользователей онлайн
    useEffect(() => {
        try {
            fetch(`http://localhost:8080/api/allOnline`)
                .then(res => res.json())
                .then(data => {
                    let onlineUs = data.filter(e => e.id !== Number(params.id));
                    setOnlineUsers(onlineUs);
                    console.log("onlineUsers: ", onlineUs);
                    console.log("id: ", params.id);
                })
        } catch (e) {
            console.log(e);
        }
    }, []);

    // получение всех чатов и сортировка на приватные и групповые
    useEffect(() => {
        try {
            fetch(`http://localhost:8080/api/users/${params.id}/chats`)
                .then(res => res.json())
                .then(data => {
                    setAllChats(data)

                    let privs = data.filter(e => !e.isGroup)
                    let groups = data.filter(e => e.isGroup)

                    setPrivateChats(privs);
                    setGroups(groups)

                    console.log(privs, groups);

                    const stompClient = new StompJs.Client({
                        brokerURL: "ws://localhost:8080/gs-chat",
                    });

                    stompClient.onConnect = (frame) => {
                        console.log("all chats: ", allChats);

                        console.log("Connected to StompJS", frame);
                        data.forEach((chat) => {
                            stompClient.subscribe(`/topic/chat/${chat.id}`, (message) => {
                                const senderMan = JSON.parse(message.body).sender
                                if(senderMan.id !== Number(params.id)) {
                                    console.log("Получено:", JSON.parse(message.body));
                                    setMessages((prevMessages) => [...prevMessages, JSON.parse(message.body)]);
                                }
                            })
                        })
                    }

                    stompClient.onStompError = (frame) => {
                        console.log("Stomp Error", frame)
                    }

                    stompClient.activate()

                    setStomp(stompClient)
                })
        } catch (e) {
            console.log(e);
        }
    }, [])


    //websocket


    useEffect(() => {
        setFullName(currentUser.fullName)
    }, [fullName])


    const openOrCloseModal = () => {
        const modal = document.querySelector(".cr-container")
        if(modal.classList.contains("hide")) {
            modal.classList.remove("hide")
        }
        else modal.classList.add("hide");
    }

    const handleGroupChatClick = (event) => {
        const chatId = event.target.id;
        let ch = groups.find((e) => e.id === (Number(chatId)));
        setSelectedChat(ch);
        console.log("selectedChat: ", ch);
    }

    const handlePrivateChatClick = (event) => {
        const chatId = event.target.id;
        let ch = privateChats.find((e) => e.id === (Number(chatId)));

        setSelectedChat(ch);
        console.log("selectedChat: ", ch);

    }

    return (
        <>
            <Header/>

            <div className="main">
                <div className="user-chat">

                    <div className="current-user">
                        <span className="avatar">{fullName !== undefined ? fullName.charAt(0) : ""}</span>
                        <span className="username">{currentUser.fullName}</span>
                    </div>

                    <div className="online-users">
                        <h2>Приватные чаты:</h2>
                        { currentUser ? privateChats.map(chat => (
                            <OnlineUser
                                onClick={handlePrivateChatClick}
                                key={chat.id}
                                id={chat.id}
                                chat={chat}
                                currentUser={Number(params.id)}
                                />
                        )) : "" }
                    </div>

                    <div className="groups">
                        <h2>Групповые чаты:</h2>

                        {groups && groups.length !== 0 ?
                            groups.map((chat) => (
                            <GroupChat  key={chat.id}
                                        chat={chat}
                                        id={chat.id}
                                        onClick={handleGroupChatClick}
                            /> )) : ""}
                    </div>

                    <div className="group-chats">
                        <button className="button" onClick={openOrCloseModal}>Создать групповой чат</button>
                    </div>
                </div>

                {onlineUsers ? <CreateGroupChatModal curId={currentUser.id} setGrChats={setGroups} onlines={onlineUsers}/> : ""}

                <Messages
                    chat={selectedChat}
                    stomp={stomp}
                    currentUser={currentUser}
                    currentUserId={Number(params.id)}
                    messages={messages}
                    setMessages={setMessages}/>
            </div>
        </>
    )
}