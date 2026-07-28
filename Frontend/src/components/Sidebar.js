import { NavLink, useNavigate } from "react-router-dom";
import "./../styles/Sidebar.css";

function Sidebar() {

    const navigate = useNavigate();

    const logout = () => {

        localStorage.clear();
        navigate("/");

    };

    return (

        <div className="sidebar">

            <h2>CareerPilotAI</h2>

            <NavLink to="/dashboard">🏠 Dashboard</NavLink>

            <NavLink to="/upload">📄 Upload Resume</NavLink>

            <NavLink to="/analysis">📊 Resume Analysis</NavLink>

            <NavLink to="/ats-score">⭐ ATS Score</NavLink>

            <NavLink to="/skill-gap">🧠 Skill Gap</NavLink>

            <NavLink to="/roadmap">🛣 Career Roadmap</NavLink>

            <NavLink to="/interview">💼 Interview Questions</NavLink>

            <NavLink to="/cover-letter">📃 Cover Letter</NavLink>

            <NavLink to="/linkedin">🔗 LinkedIn Summary</NavLink>

            <NavLink to="/hr-email">📧 HR Email</NavLink>

            <button onClick={logout}>
                Logout
            </button>

        </div>

    );

}

export default Sidebar;