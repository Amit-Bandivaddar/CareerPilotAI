import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import ResumeUpload from "./pages/UploadResume";
import ResumeAnalysis from "./pages/ResumeAnalysis"; 
import ATSScore from "./pages/ATSScore";
import CareerRoadmap from "./pages/CareerRoadmap";
import SkillGap from "./pages/SkillGap";
import InterviewQuestions from "./pages/InterviewQuestions";
import CoverLetter from "./pages/CoverLetter";
import LinkedInSummary from "./pages/LinkedInSummary";
import HREmail from "./pages/HREmail";
import ProtectedRoute from "./components/ProtectedRoute"; 
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Register from "./pages/Register";

function App() {
  return (
    <BrowserRouter>
       <Routes>

        <Route path="/register" element={<Register />} />

    <Route path="/" element={<Login />} />

    <Route
        path="/dashboard"
        element={
            <ProtectedRoute>
                <Dashboard />
            </ProtectedRoute>
        }
    />

    <Route
        path="/upload"
        element={
            <ProtectedRoute>
                <ResumeUpload />
            </ProtectedRoute>
        }
    />

    <Route
        path="/analysis"
        element={
            <ProtectedRoute>
                <ResumeAnalysis />
            </ProtectedRoute>
        }
    />

    <Route
        path="/ats-score"
        element={
            <ProtectedRoute>
                <ATSScore />
            </ProtectedRoute>
        }
    />

    <Route
        path="/skill-gap"
        element={
            <ProtectedRoute>
                <SkillGap />
            </ProtectedRoute>
        }
    />

    <Route
        path="/roadmap"
        element={
            <ProtectedRoute>
                <CareerRoadmap />
            </ProtectedRoute>
        }
    />

    <Route
        path="/interview"
        element={
            <ProtectedRoute>
                <InterviewQuestions />
            </ProtectedRoute>
        }
    />

    <Route
        path="/cover-letter"
        element={
            <ProtectedRoute>
                <CoverLetter />
            </ProtectedRoute>
        }
    />

    <Route
        path="/linkedin"
        element={
            <ProtectedRoute>
                <LinkedInSummary />
            </ProtectedRoute>
        }
    />

    <Route
        path="/hr-email"
        element={
            <ProtectedRoute>
                <HREmail />
            </ProtectedRoute>
        }
    />

</Routes>
 <ToastContainer
    position="top-right"
    autoClose={2500}
/>
    </BrowserRouter>
    
  );
}

export default App;