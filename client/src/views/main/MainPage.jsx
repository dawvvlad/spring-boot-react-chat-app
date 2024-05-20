import {Header} from "../../components/header/Header.jsx";
import {Link} from "react-router-dom";

export const MainPage = () => {

    return (
        <>
            <Header/>

            <div className="container">
                <h1>Курсовой проект Чат</h1>
                <div className="buttons">
                    <button className="button">
                        <Link to="/register">Регистрация</Link>
                    </button>
                    <button className="button">
                        <a href="/login">Войти</a>
                    </button>
                </div>
            </div>


        </>

    )
}