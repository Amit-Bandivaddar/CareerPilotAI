import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { toast } from "react-toastify";
import api from "../services/api";
import "../styles/Register.css";

function Register() {

    const navigate = useNavigate();

    const [fullName, setFullName] = useState("");
    const [careerGoal, setCareerGoal] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleRegister = async () => {

        if (!fullName || !careerGoal || !email || !password) {
            toast.warning("Please fill all fields");
            return;
        }

        try {

            await api.post("/register", {
                fullName,
                email,
                password,
                careerGoal
            });

            toast.success("Registration Successful!");

            setTimeout(() => {
                navigate("/");
            }, 1000);

        } catch (error) {

            console.log(error.response?.data);

            toast.error("Registration Failed");

        }

    };

    return (

        <div className="login-container">

            <div className="login-box">

                <h1>Create Account</h1>

                <p>CareerPilotAI</p>

                <input
                    type="text"
                    placeholder="Enter Full Name"
                    value={fullName}
                    onChange={(e) => setFullName(e.target.value)}
                />

                <input
                    type="text"
                    placeholder="Enter Career Goal"
                    value={careerGoal}
                    onChange={(e) => setCareerGoal(e.target.value)}
                />

                <input
                    type="email"
                    placeholder="Enter Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Enter Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button onClick={handleRegister}>
                    Register
                </button>

                <p style={{ marginTop: "20px" }}>
                    Already have an account?{" "}
                    <Link
                        to="/"
                        style={{
                            color: "#2563eb",
                            textDecoration: "none",
                            fontWeight: "bold"
                        }}
                    >
                        Login
                    </Link>
                </p>

            </div>

        </div>

    );

}

export default Register;