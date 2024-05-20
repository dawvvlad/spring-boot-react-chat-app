import './group_chat.css'

export const GroupChat = (props) => {
    const { chat, onClick } = props

    return (
        <>
            <div key={chat.id} id={chat.id} onClick={onClick} className="group-chat">{chat.name}</div>
        </>
    )
}
