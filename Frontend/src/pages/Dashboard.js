import Sidebar from "../components/Sidebar";
import "./../styles/Dashboard.css";
import { useNavigate } from "react-router-dom";

function Dashboard() {

    const name = localStorage.getItem("name");
    const email = localStorage.getItem("email");
    const navigate = useNavigate();
    return (
        
        <div className="dashboard">

            <Sidebar />

            <div className="dashboard-content">

                <h1>Welcome, {name} 👋</h1>

                <p>Your AI Career Assistant Dashboard</p>

                <div className="stats-grid">

                    <div className="stat-card">
                        <h2>👤 User</h2>
                        <p>{name}</p>
                    </div>

                    <div className="stat-card">
                        <h2>📧 Email</h2>
                        <p>{email}</p>
                    </div>

                    <div className="stat-card">
                        <h2>📄 Resume</h2>
                        <p>Ready to Upload</p>
                    </div>

                    <div className="stat-card">
                        <h2>🤖 AI Features</h2>
                        <p>9 Available</p>
                    </div>

                </div>

                 <h2 className="section-title">Quick Access</h2>

<div className="quick-grid">

    <div
        className="quick-card"
        onClick={() => navigate("/upload")}
    >
        📄
        <h3>Upload Resume</h3>
    </div>

    <div
        className="quick-card"
        onClick={() => navigate("/analysis")}
    >
        📊
        <h3>Resume Analysis</h3>
    </div>

    <div
        className="quick-card"
        onClick={() => navigate("/ats-score")}
    >
        ⭐
        <h3>ATS Score</h3>
    </div>

    <div
        className="quick-card"
        onClick={() => navigate("/skill-gap")}
    >
        🧠
        <h3>Skill Gap</h3>
    </div>

    <div
        className="quick-card"
        onClick={() => navigate("/roadmap")}
    >
        🛣
        <h3>Career Roadmap</h3>
    </div>

    <div
        className="quick-card"
        onClick={() => navigate("/interview")}
    >
        💼
        <h3>Interview Questions</h3>
    </div>

    <div
        className="quick-card"
        onClick={() => navigate("/cover-letter")}
    >
        📄
        <h3>Cover Letter</h3>
    </div>

    <div
        className="quick-card"
        onClick={() => navigate("/linkedin")}
    >
        🔗
        <h3>LinkedIn Summary</h3>
    </div>

    <div
        className="quick-card"
        onClick={() => navigate("/hr-email")}
    >
        📧
        <h3>HR Email</h3>
    </div>

</div>

            </div>

        </div>
  
    );

}

export default Dashboard;