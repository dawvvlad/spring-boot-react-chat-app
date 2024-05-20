import './online_user.css'

export const OnlineUser = (props) => {
    const {chat, onClick, currentUser } = props

    const name = chat.users.find(e => e.id !== currentUser).fullName

    return (
        <>
            <p id={chat.id} key={chat.id} onClick={onClick}>{name}</p>
        </>
    )
}