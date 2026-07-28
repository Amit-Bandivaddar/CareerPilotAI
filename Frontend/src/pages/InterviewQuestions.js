import { useEffect, useState } from "react";
import { toast } from "react-toastify";

import api from "../services/api";
import Layout from "../components/Layout";
import Loading from "../components/Loading";

function InterviewQuestions() {

    const [questions, setQuestions] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        const loadQuestions = async () => {

            try {

                setLoading(true);

                const userId = localStorage.getItem("userId");

                const response = await api.get(`/interview-questions/${userId}`);

                let data = response.data.questions || response.data;

                if (Array.isArray(data)) {

                    setQuestions(data);

                } else {

                    const formatted = data
                        .split("\n")
                        .filter(line => line.trim() !== "");

                    setQuestions(formatted);

                }

            } catch (error) {

                toast.error("Failed to load Interview Questions.");

                setQuestions(["Failed to load interview questions."]);

            } finally {

                setLoading(false);

            }

        };

        loadQuestions();

    }, []);

    if (loading) {

        return (

            <Layout>

                <Loading />

            </Layout>

        );

    }

    return (

        <Layout>

            <div style={{ padding: "40px" }}>

                <h1>Interview Questions</h1>

                {questions.map((question, index) => (

                    <div
                        key={index}
                        style={{
                            background: "#ffffff",
                            marginBottom: "20px",
                            padding: "20px",
                            borderRadius: "12px",
                            boxShadow: "0 4px 12px rgba(0,0,0,0.1)"
                        }}
                    >

                        <h3 style={{ color: "#2563eb" }}>

                            Question {index + 1}

                        </h3>

                        <p
                            style={{
                                fontSize: "17px",
                                lineHeight: "1.7"
                            }}
                        >
                            {question}
                        </p>

                    </div>

                ))}

            </div>

        </Layout>

    );

}

export default InterviewQuestions;