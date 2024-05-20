import './App.css'
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import {MainPage} from "./views/main/MainPage.jsx";
import {RegistrationPage} from "./views/register/RegistrationPage.jsx";
import {UserChatPage} from "./views/user-chat-page/UserChatPage.jsx";

function App() {
  return (
    <>
      <Router>
          <Routes>
            <Route path={"/home"} element={<MainPage/>}/>
              <Route path={"/register"} element={<RegistrationPage/>}/>
              <Route path={"/user/:id"} element={<UserChatPage/>}/>
          </Routes>
      </Router>
    </>
  )
}

export default App
