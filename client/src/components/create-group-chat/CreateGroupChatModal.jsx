import './create_group_chat.css'
import {useEffect} from "react";

export const CreateGroupChatModal = (props) => {
    // eslint-disable-next-line react/prop-types
    const { onlines, setGrChats, curId }  = props
    const userIdArray= []

    useEffect(() => {
        console.log("Onlie in modal: ", onlines);
    }, []);

    const addUsersToChat = () => {
        if(userIdArray.length === 0) {
            closeModal();
        }
        else {
            userIdArray.push(curId)
            fetch("http://localhost:8080/api/createChat", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    userIds: userIdArray,
                    isGroup: true,
                })
            }).then(data => data.json()).then(e => {
                setGrChats(prevChats => [...prevChats, e])
            })
        }
    }

    function handlePushUserId(event, userId) {
        const isChecked = event.target.checked;
        if (isChecked) {
            userIdArray.push(userId)
        }
        else {
            const index = userIdArray.indexOf(userId);
            if (index !== -1) {
                userIdArray.splice(index, 1);
            }
        }

        console.log(userIdArray);
    }

    function closeModal() {
        const modal = document.querySelector(".cr-container")
        modal.classList.add("hide")
    }

    return (
        <>
            <div className="cr-container hide">
                <button className="button" onClick={closeModal}>Закрыть</button>
                <div className="modal-div">
                    {onlines ? onlines.map(user => (
                        <label key={user.id}>
                            <p>{user.fullName}</p>
                            <input key={user.id} type="checkbox" value={user.id} id={user.id} onChange={(e) => handlePushUserId(e, user.id)}/>
                        </label>
                    )) : ""}
                    <button className="button" onClick={addUsersToChat}>Создать чат</button>
                </div>
            </div>
        </>
    )
}