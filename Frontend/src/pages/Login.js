import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import "./../styles/Login.css";
import { toast } from "react-toastify";
import { Link } from "react-router-dom";

function Login() {
const [email, setEmail] = useState("");

const [password, setPassword] = useState("");
const navigate = useNavigate();
    const handleLogin = async () => {

    try {

        const response = await api.post("/login", {

            email,
            password

        });
        console.log(response.data);

        localStorage.setItem("token", response.data.token);
localStorage.setItem("userId", response.data.userId);
localStorage.setItem("name", response.data.name);
localStorage.setItem("email", response.data.email);

console.log("Token:", localStorage.getItem("token"));
console.log("User ID:", localStorage.getItem("userId"));
console.log("Name:", localStorage.getItem("name"));
console.log("Email:", localStorage.getItem("email"));
        toast.success("Login Successful!");

setTimeout(() => {

    navigate("/dashboard");

}, 1000);

    } catch (error) {

        toast.error("Invalid Email or Password");

    }

};
return (

        <div className="login-container">

            <div className="login-box">

                <h1>CareerPilotAI</h1>

                <p>AI Personal Career Mentor</p>

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

                 <button onClick={handleLogin}>

Login

</button>

                <p style={{ marginTop: "25px" }}>
    Don't have an account?{" "}
    <Link
        to="/register"
        style={{
            color: "#2563eb",
            textDecoration: "none",
            fontWeight: "bold"
        }}
    >
        Register
    </Link>
</p>

            </div>

        </div>

    );

}

export default Login;