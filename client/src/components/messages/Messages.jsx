import './messages.css'
import {Message} from "../message/Message.jsx";
import {useEffect, useState} from "react";

export const Messages = (props) => {
    const { chat, currentUser, stomp, messages, setMessages, currentUserId } = props;
    const [messageInput, setMessageInput] = useState('');

    useEffect(() => {
        setMessages(chat.messages || [])
    }, [chat.messages]);

    const sendMessage = (message) => {
        console.log("Отправлено: ", message)

        stomp.publish({
            destination: `/app/chat/${chat.id}`,
            body: JSON.stringify(message),
        })
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        if (messageInput.trim() !== '') {
            const newMessage = {
                sender: currentUser,
                content: messageInput,
                dateTime: new Date().toISOString(),
                status: true,
            };

            sendMessage(newMessage);
            setMessageInput('');
            setMessages((prevMessages) => [...prevMessages, newMessage]);
        }
    }

    return (
        <div className="messages">
            <p>Сообщения</p>
            <div className="message-wrapper">{!messages || messages.length === 0 ? <p>Сообщений пока нет</p> :
                messages.map(message => (<Message
                    curUserId={currentUserId}
                    key={message.id}
                    message={message}
                    sender={message.sender}
                    currentUser={currentUser}
                />))}
            </div>

            <div className="form-wrapper">
                <form onSubmit={handleSubmit}>
                    <input className="mes"
                           name="message"
                           type="text"
                           placeholder="Введите сообщение"
                           value={messageInput}
                           onChange={e => setMessageInput(e.target.value)}
                    />
                    <button className="button" type="submit">Отправить</button>
                </form>
            </div>
        </div>
    )
}

