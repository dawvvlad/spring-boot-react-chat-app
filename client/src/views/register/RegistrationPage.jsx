import {Header} from "../../components/header/Header.jsx";
import {useState} from "react";
import { useNavigate } from 'react-router-dom'
import './registration-page.css'

export const RegistrationPage = () => {
    const navigate = useNavigate();

    let [user, setUser] = useState({
        name: "",
        surname: "",
        login: "",
        password: "",
    });
    let pVal = "";

    const { name, surname, login, password } = user;

    function handleChange(e) {
        const { name, value } = e.target;
        setUser({...user, [name]: value});
    }

    async function registerHandler() {
        let userData = {}
        try {
            await fetch("http://localhost:8080/api/registration", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    name,
                    surname,
                    login,
                    password
                })
            }).then(res => res.json())
                .then(e => userData = e);
        } catch (error) {
            pVal = "Пользователь с таким логином уже существует!"
            console.log(error);
        }
            navigate("/home")
    }

    return (
        <>
            <Header/>
            <div className="container">
                <h2>Регистрация</h2>
                <form action="/api/registration" method="POST" className="form">

                    <input type="text" name="name" placeholder="Ваше имя" value={name} onChange={handleChange}/>
                    <input type="text" name="surname" placeholder="Ваша фамилия" value={surname}
                           onChange={handleChange}/>
                    <input type="text" name="login" placeholder="Логин" value={login} onChange={handleChange}/>
                    <input type="password" name="password" placeholder="Пароль" value={password}
                           onChange={handleChange}/>
                    <button type="button" className="button" onClick={registerHandler}>Зарегистрироваться</button>
                    <p className="attempt">{pVal}</p>
                </form>
            </div>
        </>
    )
}