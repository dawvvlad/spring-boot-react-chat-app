import './message.css'

export const Message = (props) => {
    const { message, sender, curUserId } = props
    const isSender = sender.id === curUserId

    return (
        <>
            <div className={isSender ? "message " + "sender-message" : "message " + "receiver-message" }>
                <p className="sender-message">{sender.fullName}</p>
                <p className="message-content">{message.content}</p>
            </div>
        </>
    )
}