import './header.css';
import {Link} from "react-router-dom";

export const Header = () => {
    return (
        <header className="header">
            <Link to="/"><h2 className="logo">Logo</h2></Link>
        </header>
    )
}